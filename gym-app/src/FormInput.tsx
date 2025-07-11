import React from "react";

interface FormInputProps {
  label: string;
  name: string;
  value: string | number; // Acepta tanto string como number
  onChange: React.ChangeEventHandler<HTMLInputElement>;
  onBlur?: () => void; // Hacer opcional para mayor flexibilidad
  error?: string;
  type?: React.HTMLInputTypeAttribute; // Tipos de input más precisos
  required?: boolean;
  min?: number | string;
  max?: number | string;
  step?: number | string;
  placeholder?: string;
  className?: string;
  containerClassName?: string; // Para estilizar el contenedor
}

const FormInput: React.FC<FormInputProps> = ({
  label,
  name,
  value,
  onChange,
  onBlur,
  error,
  type = "text",
  required = false,
  min,
  max,
  step,
  placeholder,
  className = "",
  containerClassName = "",
}) => (
  <div className={`form-input-container ${containerClassName} ${error ? "error-border" : ""}`}>
    <label htmlFor={name}>
      {label}
      {required && <span className="required-asterisk">*</span>}
    </label>
    <input
      id={name}
      type={type}
      name={name}
      value={value}
      onChange={onChange}
      onBlur={onBlur}
      required={required}
      min={min}
      max={max}
      step={step}
      placeholder={placeholder}
      className={`form-input ${className}`}
    />
    {error && <div className="error-message">{error}</div>}
  </div>
);

export default FormInput;