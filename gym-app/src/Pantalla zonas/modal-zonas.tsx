import React, { useState, useEffect } from "react";
import "./modal-zonas.css";

interface TableRow {
  id: number;
  model: string;
  muscleGroup: string;
}

// Asegúrate de que esta línea esté al final del archivo:
export interface Zona {
  // <-- Exportación nombrada
  id: string;
  nombre: string;
  descripcion: string;
  maquinasSeleccionadas: number[];
}

interface ModalZonaProps {
  isOpen: boolean;
  onClose: () => void;
  zona: Zona | null;
  onSave: (zona: Zona) => void;
  onDelete?: (id: string) => void;
  isLoading: boolean;
}

const tablaMaquinas: TableRow[] = [
  { id: 1, model: "Model A", muscleGroup: "Pecho" },
  { id: 2, model: "Model B", muscleGroup: "Espalda" },
  { id: 3, model: "Model C", muscleGroup: "Piernas" },
  { id: 4, model: "Model D", muscleGroup: "Brazos" },
  { id: 5, model: "Model E", muscleGroup: "Abdominales" },
  { id: 6, model: "Model F", muscleGroup: "Glúteos" },
  { id: 7, model: "Model G", muscleGroup: "Hombros" },
];

const ModalZona: React.FC<ModalZonaProps> = ({
  isOpen,
  onClose,
  zona,
  onSave,
  onDelete,
  isLoading,
}) => {
  const [formData, setFormData] = useState<Zona>({
    id: "",
    nombre: "",
    descripcion: "",
    maquinasSeleccionadas: [],
  });

  // Inicializar datos al abrir
  useEffect(() => {
    if (zona) setFormData(zona);
    else
      setFormData({
        id: "",
        nombre: "",
        descripcion: "",
        maquinasSeleccionadas: [],
      });
  }, [zona, isOpen]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { id, value } = e.target;
    setFormData((prev) => ({ ...prev, [id]: value }));
  };

  const handleCheckboxChange = (id: number) => {
    setFormData((prev) => ({
      ...prev,
      maquinasSeleccionadas: prev.maquinasSeleccionadas.includes(id)
        ? prev.maquinasSeleccionadas.filter((mid) => mid !== id)
        : [...prev.maquinasSeleccionadas, id],
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSave(formData);
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div
        className="modal-container-zonas"
        onClick={(e) => e.stopPropagation()}
      >
        <h2 className="form-title">{zona ? "Editar Zona" : "Nueva Zona"}</h2>

        <div className="form-content">
          {/* Panel izquierdo: Formulario */}
          <form className="form-section" onSubmit={handleSubmit}>
            <div className="form-field">
              <label htmlFor="nombre">Nombre *</label>
              <input
                id="nombre"
                type="text"
                value={formData.nombre}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-field">
              <label htmlFor="descripcion">Descripción</label>
              <textarea
                id="descripcion"
                value={formData.descripcion}
                onChange={handleChange}
                rows={4}
              />
            </div>

            <div className="form-actions-zonas">
              {zona && onDelete && (
                <button
                  type="button"
                  className="action-button danger-button"
                  onClick={() => onDelete(formData.id)}
                  disabled={isLoading}
                >
                  Eliminar
                </button>
              )}
              <button
                type="button"
                className="action-button cancelar-button"
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
                {isLoading ? "Guardando.." : "Guardar"}
              </button>
            </div>
          </form>

          {/* Panel derecho: Tabla de máquinas */}
          <div className="table-section">
            <div className="table-container">
              <table className="data-table">
                <thead className="thead-zonas">
                  <tr>
                    <th>Sel.</th>
                    <th>Modelo</th>
                    <th>Grupo Muscular</th>
                  </tr>
                </thead>
                <tbody>
                  {tablaMaquinas.map((row) => (
                    <tr key={row.id}>
                      <td>
                        <input
                          type="checkbox"
                          checked={formData.maquinasSeleccionadas.includes(
                            row.id
                          )}
                          onChange={() => handleCheckboxChange(row.id)}
                        />
                      </td>
                      <td>{row.model}</td>
                      <td>{row.muscleGroup}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ModalZona;
