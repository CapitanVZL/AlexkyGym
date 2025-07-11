import React from "react";
import { FormFieldProps } from "../hooks/types";

const FormSelect: React.FC<FormFieldProps> = ({
  label,
  name,
  value,
  onChange,
  onBlur,
  error,
  required = false,
  options = [],
  placeholder = "Seleccione...",
  containerClassName = "",
}) => (
  <div
    className={`form-group ${containerClassName} ${error ? "has-error" : ""}`}
  >
    <label htmlFor={name}>
      {label}
      {required && <span className="required">*</span>}
    </label>
    <select
      id={name}
      name={name}
      value={value}
      onChange={onChange}
      onBlur={onBlur}
      required={required}
      className={`form-control ${error ? "error-border" : ""}`}
    >
      <option value="">{placeholder}</option>
      {options.map((option) => (
        <option key={option.id} value={option.id}>
          {option.nombre}
        </option>
      ))}
    </select>
    {error && <div className="error-message">{error}</div>}
  </div>
);

export default FormSelect;
