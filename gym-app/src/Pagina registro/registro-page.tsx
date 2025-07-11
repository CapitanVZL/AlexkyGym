import React, { useState } from "react";
import { Link } from "react-router-dom";
import FormularioMaquina from "./formulario-maquina";
import Subeimagen from "./sube-img";
import "./registro-page.css";
import type {
  FormData,
  SubmitStatus,
  Zona,
  TipoEquipo,
  GrupoMuscular,
} from "../hooks/types";

const RegistroPage: React.FC = () => {
  // Datos de ejemplo - deberías obtenerlos de tu API o contexto
  const [zonas] = useState<Zona[]>([
    { id: 1, nombre: "Cardio" },
    { id: 2, nombre: "Fuerza" },
  ]);

  const [tiposEquipo] = useState<TipoEquipo[]>([
    { id: 1, nombre: "Máquina" },
    { id: 2, nombre: "Banco" },
  ]);

  const [gruposMusculares] = useState<GrupoMuscular[]>([
    { id: 1, nombre: "Pecho" },
    { id: 2, nombre: "Espalda" },
  ]);

  const [formData, setFormData] = useState<FormData>({
    serial: "",
    nombre: "",
    modelo: "",
    marca: "",
    estado: "Operativa",
    imagen: "",
    peso: 0,
    tipoEquipoId: 0,
    zonaId: 0,
    gruposMuscularesIds: [],
    fechaAdquisicion: new Date().toISOString().split("T")[0],
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitStatus, setSubmitStatus] = useState<SubmitStatus | null>(null);
  const [resetFormFlag, setResetFormFlag] = useState(false);

  const validateForm = () => {
    const requiredFields: (keyof FormData)[] = [
      "serial",
      "nombre",
      "modelo",
      "marca",
      "estado",
      "imagen",
      "peso",
      "tipoEquipoId",
      "zonaId",
      "gruposMuscularesIds",
      "fechaAdquisicion",
    ];

    return requiredFields.every((field) => {
      const value = formData[field];
      if (Array.isArray(value)) return value.length > 0;
      if (typeof value === "number") return value > 0;
      if (typeof value === "string") return value.trim() !== "";
      return true;
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) {
      setSubmitStatus({
        success: false,
        message: "Por favor complete todos los campos requeridos",
      });
      return;
    }

    setIsSubmitting(true);
    setSubmitStatus(null);

    try {
      const response = await fetch("http://localhost:8080/api/equipos/crear", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          ...formData,
          fechaAdquisicion: formData.fechaAdquisicion.split("-").map(Number),
        }),
      });

      if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);

      setSubmitStatus({
        success: true,
        message: "¡Máquina registrada exitosamente!",
      });

      // Resetear formulario
      setFormData({
        serial: "",
        nombre: "",
        modelo: "",
        marca: "",
        estado: "Operativa",
        imagen: "",
        peso: 0,
        tipoEquipoId: 0,
        zonaId: 0,
        gruposMuscularesIds: [],
        fechaAdquisicion: new Date().toISOString().split("T")[0],
      });

      setResetFormFlag((prev) => !prev);
    } catch (error) {
      console.error("Error al registrar:", error);
      setSubmitStatus({
        success: false,
        message: "Error al registrar. Por favor, inténtalo de nuevo.",
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="registro-page">
      <FormularioMaquina
        onSubmit={handleSubmit}
        isSubmitting={isSubmitting}
        submitStatus={submitStatus}
        resetForm={resetFormFlag}
        zonas={zonas}
        tiposEquipo={tiposEquipo}
        gruposMusculares={gruposMusculares}
        initialFormData={formData}
      />
      <Subeimagen />
      <Link to="/">
        <button className="boton-volver">Volver</button>
      </Link>
    </div>
  );
};

export default RegistroPage;
