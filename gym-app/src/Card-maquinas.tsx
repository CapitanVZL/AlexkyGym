import React from "react";
import MenuDesplegable from "./menu-desplegable";
import "./panel-maquinas.css";
import type { Maquina } from "./hooks/types";

interface MaquinaCardProps {
  maquina: Maquina;
  vistaHorizontal: boolean;
  onEditar: (maquina: Maquina) => void;
}

const MaquinaCard: React.FC<MaquinaCardProps> = ({
  maquina,
  vistaHorizontal,
  onEditar,
}) => {
  const getImagenUrl = (url: string) =>
    url?.trim()
      ? url
      : "https://via.placeholder.com/300x200?text=Imagen+no+disponible";

  // Función para obtener los colores de cada estado correspondiente
  const getEstadoColor = (estado: string) => {
    switch (estado.toLowerCase()) {
      case "operativa":
        return "estado-operativa";
      case "mantenimiento":
        return "estado-mantenimiento";
      default:
        return "estado-inactiva";
    }
  };

  // Función para obtener los nombres de los grupos musculares como string
  const getGruposMusculares = () => {
    if (!maquina.gruposMusculares || maquina.gruposMusculares.length === 0) {
      return "No especificado";
    }

    // Tomar los primeros 2 grupos musculares
    const primerosDos = maquina.gruposMusculares.slice(0, 2);
    const nombres = primerosDos.map((g) => g.nombre);

    // Si hay más de 2, añadir "etc."
    if (maquina.gruposMusculares.length > 2) {
      return `${nombres.join(", ")} , etc.`;
    }

    return nombres.join(", ");
  };

  return (
    <div className={`maquina-card ${vistaHorizontal ? "card-horizontal" : ""}`}>
      <button className="card-button">
        {!vistaHorizontal ? (
          <>
            <div className="maquina-imagen-container">
              <img
                src={getImagenUrl(maquina.imagen)}
                alt={`Máquina ${maquina.modelo}`}
                className="maquina-imagen"
                onError={(e) => {
                  const img = e.target as HTMLImageElement;
                  const fallback =
                    "https://via.placeholder.com/300x200?text=Imagen+no+disponible";
                  if (!img.src.includes("placeholder")) {
                    img.src = fallback;
                  }
                }}
              />
            </div>
            <div className="maquina-info-container">
              <div className="maquina-header">
                <h2 className="maquina-modelo">{maquina.modelo}</h2>
                <span
                  className={`maquina-estado ${getEstadoColor(maquina.estado)}`}
                >
                  {maquina.estado}
                </span>
              </div>
              <div className="maquina-details">
                <p>
                  <span className="detail-label">Marca:</span> {maquina.marca}
                </p>
                <p>
                  <span className="detail-label">Grupo Muscular:</span>{" "}
                  {getGruposMusculares()}
                </p>
              </div>
              <div className="menu-flotante-wrapper">
                <MenuDesplegable
                  maquina={maquina}
                  onEditar={onEditar}
                  vistaHorizontal={vistaHorizontal}
                />
              </div>
            </div>
          </>
        ) : (
          <div className="maquina-info-container">
            <div className="maquina-header">
              <h2 className="maquina-modelo">{maquina.modelo}</h2>
            </div>
            <div className="maquina-details">
              <p>Marca: {maquina.marca}</p>
              <p>
                <span className="detail-label">Grupo Muscular:</span>{" "}
                {getGruposMusculares()}
              </p>
              <span
                className={`maquina-estado ${getEstadoColor(maquina.estado)}`}
              >
                {maquina.estado}
              </span>
              <div className="menu-flotante-wrapper">
                <MenuDesplegable
                  maquina={maquina}
                  onEditar={onEditar}
                  vistaHorizontal={vistaHorizontal}
                />
              </div>
            </div>
          </div>
        )}
      </button>
    </div>
  );
};

export default MaquinaCard;
