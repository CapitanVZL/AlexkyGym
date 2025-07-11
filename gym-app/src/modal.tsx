import React, { useState, useEffect, useRef } from "react";
import { Maquina } from "./hooks/types";
import "./modal.css";

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  maquina: Maquina | null;
  onSave: (maquina: Maquina) => void;
  isLoading: boolean;
}

const Modal: React.FC<ModalProps> = ({
  isOpen,
  onClose,
  maquina,
  onSave,
  isLoading,
}) => {
  const [formData, setFormData] = useState<Maquina>({
    id: 0,
    modelo: "",
    marca: "",
    grupMuscular: "",
    imagenURL: "",
    estado: "operativa",
  });

  const [internalIsOpen, setInternalIsOpen] = useState(false);
  const modalRef = useRef<HTMLDivElement>(null);

  // Sincronizar formData con la máquina recibida
  useEffect(() => {
    if (maquina) {
      setFormData(maquina);
    }
  }, [maquina]);

  // Manejar apertura/cierre con delay para animaciones
  useEffect(() => {
    let timer: NodeJS.Timeout;

    if (isOpen && maquina) {
      setInternalIsOpen(true);
    } else {
      timer = setTimeout(() => setInternalIsOpen(false), 300);
    }

    return () => clearTimeout(timer);
  }, [isOpen, maquina]);

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >
  ) => {
    const { id, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [id]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSave(formData);
  };

  const handleBackdropClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  if (!isOpen || !internalIsOpen || !maquina) return null;

  return (
    <div
      className={`modal-parent ${isOpen ? "active" : ""}`}
      ref={modalRef}
      onClick={handleBackdropClick}
    >
      <div className="modal-content">
        <form onSubmit={handleSubmit}>
          <div className="modal-div1">Editar Máquina</div>

          <div className="modal-div3">
            <div className="form-group">
              <label htmlFor="modelo">Modelo</label>
              <input
                type="text"
                id="modelo"
                value={formData.modelo}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="marca">Marca</label>
              <input
                type="text"
                id="marca"
                value={formData.marca}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="imagenURL">Imagen URL</label>
              <input
                type="text"
                id="imagenURL"
                value={formData.imagenURL}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="grupMuscular">Grupo Muscular</label>
              <select
                id="grupMuscular"
                value={formData.grupMuscular}
                onChange={handleChange}
                required
              >
                <option value="Pierna">Pierna</option>
                <option value="Brazo">Brazo</option>
                <option value="Espalda">Espalda</option>
                <option value="Pecho">Pecho</option>
                <option value="Abdomen">Abdomen</option>
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="estado">Estado</label>
              <select
                id="estado"
                value={formData.estado}
                onChange={handleChange}
                required
              >
                <option value="Operativa">Operativa</option>
                <option value="Mantenimiento">Mantenimiento</option>
                <option value="Inactiva">Inactiva</option>
              </select>
            </div>
          </div>

          <div className="modal-div4">
            <button
              type="button"
              className="action-button secondary-button"
              onClick={onClose}
              disabled={isLoading}
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="action-button primary-button"
              disabled={isLoading}
            >
              {isLoading ? "Guardando..." : "Guardar"}
            </button>
          </div>
        </form>

        <button
          className="close-button"
          onClick={onClose}
          disabled={isLoading}
          aria-label="Cerrar modal"
        >
          ×
        </button>
      </div>
    </div>
  );
};

export default Modal;
