import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./historico-estado-page.css"; // Importamos los estilos

const FormularioHistoricoLocal = () => {
  const [formData, setFormData] = useState({
    fechaCambio: new Date().toISOString().split("T")[0],
    estadoAnterior: "",
    estadoNuevo: "",
    motivo: "",
  });

  const navigate = useNavigate();

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Datos enviados:", formData);
    alert(`Registro guardado:\n${JSON.stringify(formData, null, 2)}`);
  };

  const handleGoBack = () => {
    navigate("/");
  };

  return (
    <div className="historico-container">
      <button onClick={handleGoBack} className="back-button">
        <span></span> Regresar
      </button>

      <h2>Registro de Cambio</h2>

      <form onSubmit={handleSubmit} className="historico-form">
        <label>
          Fecha del Cambio:
          <input
            type="date"
            name="fechaCambio"
            value={formData.fechaCambio}
            onChange={handleChange}
          />
        </label>

        <label>
          Estado Anterior:
          <select
            name="estadoAnterior"
            value={formData.estadoAnterior}
            onChange={handleChange}
          >
            <option value="">Seleccionar...</option>
            <option value="Operativa">Operativa</option>
            <option value="Inactiva">Inactiva</option>
            <option value="Mantenimiento">Mantenimiento</option>
          </select>
        </label>

        <label>
          Estado Nuevo:
          <select
            name="estadoNuevo"
            value={formData.estadoNuevo}
            onChange={handleChange}
          >
            <option value="">Seleccionar...</option>
            <option value="Operativa">Operativa</option>
            <option value="Inactiva">Inactiva</option>
            <option value="Mantenimiento">Mantenimiento</option>
            <option value="Retirada">Retirada</option>
          </select>
        </label>

        <label>
          Motivo:
          <textarea
            name="motivo"
            value={formData.motivo}
            onChange={handleChange}
            placeholder="Describa qué sucedió..."
            rows={4}
          />
        </label>

        <button type="submit" className="submit-button">
          Guardar
        </button>
      </form>
    </div>
  );
};

export default FormularioHistoricoLocal;
