import React, { useState } from "react";
import { Link } from "react-router-dom";
import Modal from "./modal";
import MaquinaCard from "./Card-maquinas";
import FiltroEstados from "./Barra-filtrado-estados";
import { useMaquinas } from "./hooks/useMaquinas";
import { Maquina } from "./hooks/types";
import { ESTADOS } from "./hooks/constants";
import "./panel-maquinas.css";

const PanelMaquinas: React.FC = () => {
  const {
    maquinasFiltradas,
    isLoading,
    error,
    filtrarPorEstado,
    actualizarMaquina,
  } = useMaquinas();

  const [vistaHorizontal, setVistaHorizontal] = useState(false);
  const [modalAbierto, setModalAbierto] = useState(false);
  const [maquinaEditando, setMaquinaEditando] = useState<Maquina | null>(null);

  const abrirModalEditar = (maquina: Maquina) => {
    setMaquinaEditando(maquina);
    setModalAbierto(true);
  };

  const handleGuardarCambios = async (maquinaActualizada: Maquina) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/equipos/${maquinaActualizada.serial}`,
        {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(maquinaActualizada),
        }
      );

      if (!response.ok) throw new Error("Error al actualizar la máquina");

      actualizarMaquina(maquinaActualizada);
    } catch (error) {
      alert("Ocurrió un error al guardar los cambios");
    }
  };

  if (isLoading)
    return <div className="loading-message">Cargando máquinas...</div>;

  if (error) return <div className="error-message">Error: {error}</div>;

  return (
    <div className="panel-maquinas-container">
      <FiltroEstados estados={ESTADOS} onFiltrar={filtrarPorEstado} />

      <div className="container-boton-horizontal">
        <Link to="/page-registro" className="no-underline">
          <button className="boton-agregar-maquinas">
            Agregar
            <p className="icono-agregar">+</p>
          </button>
        </Link>
        <div className="boton-ordenar-maquinas">
          <button
            onClick={() => setVistaHorizontal(!vistaHorizontal)}
            className={`Boton-horizontal ${
              vistaHorizontal ? "horizontal-activo" : "vertical-activo"
            }`}
          >
            {vistaHorizontal ? "Cuadricula" : "Fila"}
          </button>
          <p>Ordenar por</p>
        </div>
      </div>

      {maquinasFiltradas.length === 0 ? (
        <div className="no-results">No se encontraron máquinas</div>
      ) : (
        <div className={`maquinas-grid ${vistaHorizontal ? "horizontal" : ""}`}>
          {maquinasFiltradas.map((maquina) => (
            // En el map, cambias:
            <MaquinaCard
              key={maquina.serial}
              maquina={maquina}
              vistaHorizontal={vistaHorizontal}
              onEditar={abrirModalEditar} // Aquí pasas directamente la función
            />
          ))}
        </div>
      )}

      <Modal
        isOpen={modalAbierto}
        onClose={() => {
          setModalAbierto(false);
          setMaquinaEditando(null);
        }}
        maquina={maquinaEditando}
        onSave={(maquinaActualizada) => {
          handleGuardarCambios(maquinaActualizada);
          setModalAbierto(false);
          setMaquinaEditando(null);
        }}
        isLoading={isLoading}
      />
    </div>
  );
};

export default PanelMaquinas;
