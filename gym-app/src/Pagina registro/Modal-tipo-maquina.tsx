import React, { useState } from "react";
import "./formulario-maquina.css"; // Asegúrate de que la ruta sea correcta

interface NuevoTipoMaquinaModalProps {
  isOpen: boolean;
  onClose: () => void;
  onGuardar: (nuevoTipo: string) => void;
}

const NuevoTipoMaquinaModal: React.FC<NuevoTipoMaquinaModalProps> = ({
  isOpen,
  onClose,
  onGuardar,
}) => {
  const [nuevoTipo, setNuevoTipo] = useState("");

  const handleGuardar = () => {
    if (nuevoTipo.trim()) {
      onGuardar(nuevoTipo.trim());
      setNuevoTipo("");
      onClose();
    } else {
      alert("Ingrese un nombre válido");
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay-tipo-maquina">
      <div className="modal-contenttipo-maquina">
        <h2>Agregar nuevo tipo de máquina</h2>
        <input
          type="text"
          value={nuevoTipo}
          onChange={(e) => setNuevoTipo(e.target.value)}
          placeholder="Nombre del nuevo tipo"
          autoFocus
        />
        <div className="modal-buttons">
          <button className="guardar" onClick={handleGuardar}>
            Guardar
          </button>
          <button className="cancelar" onClick={onClose}>
            Cancelar
          </button>
        </div>
      </div>
    </div>
  );
};

export default NuevoTipoMaquinaModal;
