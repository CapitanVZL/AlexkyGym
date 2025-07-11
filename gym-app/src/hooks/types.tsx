// src/types/maquina.ts
import type React from "react";

export type EstadoMaquina = "operativa" | "mantenimiento" | "inactiva";

export interface EntidadBase {
  id: number;
  nombre: string;
}

export interface GrupoMuscular extends EntidadBase {}
export interface TipoEquipo extends EntidadBase {}
export interface Zona extends EntidadBase {}

export interface Reporte {
  id: number;
  fecha: Date;
  descripcion: string;
  tecnico: string;
}

export interface Maquina {
  id?: number;
  serial: string;
  nombre: string;
  modelo: string;
  marca: string;
  estado: EstadoMaquina;
  imagen: string;
  peso: number;
  tipoEquipo: TipoEquipo;
  zona: Zona;
  gruposMusculares: GrupoMuscular[];
  reportes: Reporte[];
  fechaAdquisicion: Date | string;
}

export interface FormData {
  serial: string;
  nombre: string;
  modelo: string;
  marca: string;
  estado: string;
  imagen: string;
  peso: number;
  tipoEquipoId: number;
  zonaId: number;
  gruposMuscularesIds: number[];
  fechaAdquisicion: string;
}

export interface SubmitStatus {
  success: boolean;
  message: string;
}

export interface FormularioMaquinaProps {
  onSubmit: (e: React.FormEvent) => void;
  isSubmitting?: boolean;
  submitStatus?: SubmitStatus | null;
  resetForm?: boolean;
  zonas: Zona[];
  tiposEquipo: TipoEquipo[];
  gruposMusculares: GrupoMuscular[];
  initialFormData?: Partial<FormData>;
}

export interface FormFieldProps {
  name: string;
  label: string;
  value: string | number;
  error?: string;
  onChange: (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => void;
  onBlur?: () => void;
  required?: boolean;
  type?: React.HTMLInputTypeAttribute;
  options?: EntidadBase[];
  placeholder?: string;
  min?: number | string;
  max?: number | string;
  containerClassName?: string;
}
