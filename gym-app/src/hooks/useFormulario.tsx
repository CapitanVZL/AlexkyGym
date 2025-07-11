import { useEffect, useState } from "react";
import { FormData } from "./types";

export const useFormulario = (
  initialFormData: FormData,
  resetForm: boolean
) => {
  const [formData, setFormData] = useState<FormData>(initialFormData);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [touched, setTouched] = useState<Record<string, boolean>>({});

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value, type } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: type === "number" ? Number(value) : value,
    }));
  };

  const handleBlur = (field: string) => {
    setTouched((prev) => ({ ...prev, [field]: true }));
  };

  const validateField = (name: string, value: any) => {
    if (!touched[name]) return "";

    // Validaciones específicas para cada campo
    switch (name) {
      case "serial":
      case "nombre":
      case "modelo":
      case "marca":
        return !value.trim() ? "Este campo es requerido" : "";
      case "peso":
        return !value || value <= 0 ? "El peso debe ser mayor a 0" : "";
      case "tipoEquipoId":
      case "zonaId":
        return !value ? "Debe seleccionar una opción" : "";
      case "fechaAdquisicion":
        return !value ? "Seleccione una fecha válida" : "";
      default:
        return "";
    }
  };

  useEffect(() => {
    const newErrors: Record<string, string> = {};
    Object.entries(formData).forEach(([key, value]) => {
      if (key !== "imagen" && key !== "gruposMuscularesIds") {
        newErrors[key] = validateField(key, value);
      }
    });
    setErrors(newErrors);
  }, [formData, touched]);

  useEffect(() => {
    if (resetForm) {
      setFormData(initialFormData);
      setErrors({});
      setTouched({});
    }
  }, [resetForm, initialFormData]);

  const isFormValid = () => {
    return (
      Object.values(errors).every((e) => !e) && Object.keys(touched).length > 0
    );
  };

  return {
    formData,
    errors,
    handleChange,
    handleBlur,
    isFormValid,
    setFormData,
  };
};
