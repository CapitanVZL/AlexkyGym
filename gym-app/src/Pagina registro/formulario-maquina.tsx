import React, { useState } from "react";
import { useFormulario } from "../hooks/useFormulario";
import { FormData, GrupoMuscular, TipoEquipo, Zona } from "../hooks/types";
import "./formulario-maquina.css";

interface FormularioMaquinaProps {
  onSubmit: (e: React.FormEvent) => void; // Cambiado para aceptar el evento
  isSubmitting?: boolean;
  submitStatus?: { success: boolean; message: string } | null;
  resetForm?: boolean;
  zonas: Zona[];
  tiposEquipo: TipoEquipo[];
  gruposMusculares: GrupoMuscular[];
  initialFormData?: FormData;
}

const FormularioMaquina: React.FC<FormularioMaquinaProps> = ({
  onSubmit,
  isSubmitting = false,
  submitStatus = null,
  resetForm = false,
  zonas,
  tiposEquipo,
  gruposMusculares,
  initialFormData = {
    serial: "",
    nombre: "",
    modelo: "",
    marca: "",
    estado: "",
    imagen: "",
    peso: 0,
    tipoEquipoId: 0,
    zonaId: 0,
    gruposMuscularesIds: [],
    fechaAdquisicion: "",
  },
}) => {
  const {
    formData,
    errors,
    handleChange,
    handleBlur,
    isFormValid,
    setFormData,
  } = useFormulario(initialFormData, resetForm);

  const [selectedGruposMusculares, setSelectedGruposMusculares] = useState<
    number[]
  >(initialFormData.gruposMuscularesIds || []);

  const handleGrupoMuscularChange = (grupoId: number) => {
    const updatedSelection = selectedGruposMusculares.includes(grupoId)
      ? selectedGruposMusculares.filter((id) => id !== grupoId)
      : [...selectedGruposMusculares, grupoId];

    setSelectedGruposMusculares(updatedSelection);
    setFormData((prev) => ({
      ...prev,
      gruposMuscularesIds: updatedSelection,
    }));
  };

  return (
    <div className="registro-formulario">
      <h1>Registro de Máquina</h1>

      {submitStatus && (
        <div
          className={`submit-status ${
            submitStatus.success ? "success" : "error"
          }`}
        >
          {submitStatus.message}
        </div>
      )}

      <form onSubmit={onSubmit}>
        {" "}
        {/* Usamos directamente onSubmit */}
        {/* Serial */}
        <div className={errors.serial ? "error-border" : ""}>
          <label htmlFor="serial">Serial*</label>
          <input
            type="text"
            id="serial"
            name="serial"
            value={formData.serial}
            onChange={handleChange}
            onBlur={() => handleBlur("serial")}
            required
          />
          {errors.serial && (
            <span className="error-message">{errors.serial}</span>
          )}
        </div>
        {/* Nombre */}
        <div className={errors.nombre ? "error-border" : ""}>
          <label htmlFor="nombre">Nombre*</label>
          <input
            type="text"
            id="nombre"
            name="nombre"
            value={formData.nombre}
            onChange={handleChange}
            onBlur={() => handleBlur("nombre")}
            required
          />
          {errors.nombre && (
            <span className="error-message">{errors.nombre}</span>
          )}
        </div>
        {/* Modelo */}
        <div className={errors.modelo ? "error-border" : ""}>
          <label htmlFor="modelo">Modelo*</label>
          <input
            type="text"
            id="modelo"
            name="modelo"
            value={formData.modelo}
            onChange={handleChange}
            onBlur={() => handleBlur("modelo")}
            required
          />
          {errors.modelo && (
            <span className="error-message">{errors.modelo}</span>
          )}
        </div>
        {/* Marca */}
        <div className={errors.marca ? "error-border" : ""}>
          <label htmlFor="marca">Marca*</label>
          <input
            type="text"
            id="marca"
            name="marca"
            value={formData.marca}
            onChange={handleChange}
            onBlur={() => handleBlur("marca")}
            required
          />
          {errors.marca && (
            <span className="error-message">{errors.marca}</span>
          )}
        </div>
        {/* Estado */}
        <div className={errors.estado ? "error-border" : ""}>
          <label htmlFor="estado">Estado*</label>
          <select
            id="estado"
            name="estado"
            value={formData.estado}
            onChange={handleChange}
            onBlur={() => handleBlur("estado")}
            required
          >
            <option value="">Seleccione un estado</option>
            <option value="operativa">Operativa</option>
            <option value="mantenimiento">Mantenimiento</option>
            <option value="inactiva">Inactiva</option>
          </select>
          {errors.estado && (
            <span className="error-message">{errors.estado}</span>
          )}
        </div>
        {/* Peso */}
        <div className={errors.peso ? "error-border" : ""}>
          <label htmlFor="peso">Peso (kg)*</label>
          <input
            type="number"
            id="peso"
            name="peso"
            value={formData.peso}
            onChange={handleChange}
            onBlur={() => handleBlur("peso")}
            required
            min="0"
          />
          {errors.peso && <span className="error-message">{errors.peso}</span>}
        </div>
        {/* Tipo de Equipo */}
        <div className={errors.tipoEquipoId ? "error-border" : ""}>
          <label htmlFor="tipoEquipoId">Tipo de Equipo*</label>
          <select
            id="tipoEquipoId"
            name="tipoEquipoId"
            value={formData.tipoEquipoId}
            onChange={handleChange}
            onBlur={() => handleBlur("tipoEquipoId")}
            required
          >
            <option value="">Seleccione un tipo</option>
            {tiposEquipo.map((tipo) => (
              <option key={tipo.id} value={tipo.id}>
                {tipo.nombre}
              </option>
            ))}
          </select>
          {errors.tipoEquipoId && (
            <span className="error-message">{errors.tipoEquipoId}</span>
          )}
        </div>
        {/* Zona */}
        <div className={errors.zonaId ? "error-border" : ""}>
          <label htmlFor="zonaId">Zona*</label>
          <select
            id="zonaId"
            name="zonaId"
            value={formData.zonaId}
            onChange={handleChange}
            onBlur={() => handleBlur("zonaId")}
            required
          >
            <option value="">Seleccione una zona</option>
            {zonas.map((zona) => (
              <option key={zona.id} value={zona.id}>
                {zona.nombre}
              </option>
            ))}
          </select>
          {errors.zonaId && (
            <span className="error-message">{errors.zonaId}</span>
          )}
        </div>
        {/* Fecha de Adquisición */}
        <div className={errors.fechaAdquisicion ? "error-border" : ""}>
          <label htmlFor="fechaAdquisicion">Fecha de Adquisición*</label>
          <input
            type="date"
            id="fechaAdquisicion"
            name="fechaAdquisicion"
            value={formData.fechaAdquisicion}
            onChange={handleChange}
            onBlur={() => handleBlur("fechaAdquisicion")}
            required
          />
          {errors.fechaAdquisicion && (
            <span className="error-message">{errors.fechaAdquisicion}</span>
          )}
        </div>
        {/* Grupos Musculares */}
        <div
          className="grupos-musculares-container"
          style={{ gridColumn: "span 2" }}
        >
          <span className="grupos-label">Grupos Musculares</span>
          <div className="grupos-botones">
            {gruposMusculares.map((grupo) => (
              <button
                key={grupo.id}
                type="button"
                className={`grupo-btn ${
                  selectedGruposMusculares.includes(grupo.id)
                    ? "seleccionado"
                    : ""
                }`}
                onClick={() => handleGrupoMuscularChange(grupo.id)}
              >
                {grupo.nombre}
              </button>
            ))}
          </div>
        </div>
        {/* Imagen (Opcional) */}
        <div style={{ gridColumn: "span 2" }}>
          <label htmlFor="imagen">Imagen (URL)</label>
          <input
            type="text"
            id="imagen"
            name="imagen"
            value={formData.imagen}
            onChange={handleChange}
          />
        </div>
        {/* Botón de envío */}
        <button
          type="submit"
          disabled={isSubmitting || !isFormValid()}
          className={isSubmitting ? "disabled-button" : ""}
        >
          {isSubmitting ? "Registrando..." : "Registrar Máquina"}
        </button>
      </form>
    </div>
  );
};

export default FormularioMaquina;
