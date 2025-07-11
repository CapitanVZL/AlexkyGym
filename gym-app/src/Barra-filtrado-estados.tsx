import React, { useState } from "react";
import "./panel-maquinas.css"; // o tu propio archivo de estilos

interface FiltroEstadosProps {
  estados: string[];
  onFiltrar: (estado: string) => void;
}

const FiltroEstados: React.FC<FiltroEstadosProps> = ({
  estados,
  onFiltrar,
}) => {
  const [filtroActivo, setFiltroActivo] = useState<string>("todas");

  const handleFiltro = (estado: string) => {
    setFiltroActivo(estado);
    onFiltrar(estado);
  };

  return (
    <div className="filtro-container">
      {estados.map((estado) => (
        <button
          key={estado}
          className={`filtro-btn ${filtroActivo === estado ? "activo" : ""}`}
          onClick={() => handleFiltro(estado)}
        >
          {estado.charAt(0).toUpperCase() + estado.slice(1)}
        </button>
      ))}
    </div>
  );
};

export default FiltroEstados;
