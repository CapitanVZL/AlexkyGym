import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../navbarra";
import "./registro-zonas.css";

type TableRow = {
  id: number;
  model: string;
  muscleGroup: string;
};

const ZonasForm: React.FC = () => {
  const navigate = useNavigate();
  const [zoneCode, setZoneCode] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [selectedRows, setSelectedRows] = useState<number[]>([]);

  // Datos constantes para la tabla
  const tableData: TableRow[] = [
    { id: 1, model: "Model A", muscleGroup: "Pecho" },
    { id: 2, model: "Model B", muscleGroup: "Espalda" },
    { id: 3, model: "Model C", muscleGroup: "Piernas" },
    { id: 4, model: "Model D", muscleGroup: "Brazos" },
    { id: 5, model: "Model E", muscleGroup: "Abdominales" },
    { id: 6, model: "Model E", muscleGroup: "Abdominales" },
    { id: 7, model: "Model E", muscleGroup: "Abdominales" },
  ];

  const handleCheckboxChange = (id: number): void => {
    setSelectedRows((prev) =>
      prev.includes(id) ? prev.filter((rowId) => rowId !== id) : [...prev, id]
    );
  };

  const handleSubmit = (e: React.FormEvent): void => {
    e.preventDefault();

    if (!zoneCode || !location) {
      alert("Por favor complete los campos requeridos");
      return;
    }

    console.log({
      zoneCode,
      description,
      selectedItems: selectedRows,
    });

    handleCancel();
  };

  const handleCancel = (): void => {
    navigate("/page-zonas"); // Redirige a /page-zonas
  };

  return (
    <div className="parent-zonas">
      <div className="div1-zonas">
        <Navbar />
      </div>

      <div className="div3-zonas">
        <div className="form-container-zonas">
          <h2 className="form-title">Gestión de Zonas</h2>

          <div className="form-content">
            {/* Panel izquierdo - Formulario */}
            <form className="form-section" onSubmit={handleSubmit}>
              <div className="form-field">
                <label htmlFor="zoneCode">Código de Zona *</label>
                <input
                  id="zoneCode"
                  type="text"
                  value={zoneCode}
                  onChange={(e) => setZoneCode(e.target.value)}
                  placeholder="Ingrese el código"
                  required
                />
              </div>

              <div className="form-field">
                <label htmlFor="description">Descripción</label>
                <textarea
                  id="description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                  placeholder="Descripción de la zona"
                  rows={4}
                />
              </div>

              <div className="form-actions">
                <button
                  type="button"
                  className="action-button secundary-button"
                  onClick={handleCancel}
                >
                  Cancelar
                </button>
                <button type="submit" className="action-button primary-button">
                  Registrar
                </button>
              </div>
            </form>

            {/* Panel derecho - Tabla de datos */}
            <div className="table-section">
              <table className="data-table">
                <thead>
                  <tr>
                    <th>Sel.</th>
                    <th>Modelo</th>
                    <th>Grupo Muscular</th>
                  </tr>
                </thead>
                <tbody>
                  {tableData.map((row) => (
                    <tr key={row.id}>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedRows.includes(row.id)}
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

export default ZonasForm;
