import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import ModalZonas, { Zona } from "./modal-zonas";
import "./panel-zonas.css";

const ZonasPanel: React.FC = () => {
  const navigate = useNavigate();
  const [zonas, setZonas] = useState<Zona[]>([
    {
      id: "crossfit-1",
      nombre: "Crossfit",
      descripcion: "Zona de entrenamiento funcional",
      maquinasSeleccionadas: [1, 2, 3],
    },
    {
      id: "crossfit-2",
      nombre: "Crossfit",
      descripcion: "Zona de entrenamiento funcional",
      maquinasSeleccionadas: [4, 5],
    },
    {
      id: "crossfit-3",
      nombre: "Crossfit",
      descripcion: "Zona de entrenamiento funcional",
      maquinasSeleccionadas: [6, 7],
    },
    {
      id: "crossfit-3",
      nombre: "Crossfit",
      descripcion: "Zona de entrenamiento funcional",
      maquinasSeleccionadas: [6, 7],
    },
    {
      id: "crossfit-3",
      nombre: "Crossfit",
      descripcion: "Zona de entrenamiento funcional",
      maquinasSeleccionadas: [6, 7],
    },
    {
      id: "crossfit-3",
      nombre: "Crossfit",
      descripcion: "Zona de entrenamiento funcional",
      maquinasSeleccionadas: [6, 7],
    },
    {
      id: "crossfit-3",
      nombre: "Crossfit",
      descripcion: "Zona de entrenamiento funcional",
      maquinasSeleccionadas: [6, 7],
    },
    {
      id: "crossfit-3",
      nombre: "Crossfit",
      descripcion: "Zona de entrenamiento funcional",
      maquinasSeleccionadas: [6, 7],
    },
  ]);

  const capacidadZona = 20;

  const [modalAbierto, setModalAbierto] = useState(false);
  const [zonaSeleccionada, setZonaSeleccionada] = useState<Zona | null>(null);

  const abrirModal = (zona: Zona) => {
    setZonaSeleccionada(zona);
    setModalAbierto(true);
  };

  const cerrarModal = () => {
    setModalAbierto(false);
    setZonaSeleccionada(null);
  };

  const guardarZona = (zonaActualizada: Zona) => {
    setZonas(
      zonas.map((z) => (z.id === zonaActualizada.id ? zonaActualizada : z))
    );
    cerrarModal();
  };

  const handleDelete = (id: string) => {
    setZonas(zonas.filter((z) => z.id !== id));
    cerrarModal();
  };

  const handleAgregar = () => {
    navigate("/page-zonas/register-zonas");
  };

  return (
    <div className="panel-container-zonas">
      <div className="container-header-zonas">
        <header className="header">
          <h1>Zonas</h1>
        </header>
      </div>

      <div className="zonas-grid">
        {zonas.map((zona) => (
          <div
            key={zona.id}
            className="zona-card"
            onClick={() => abrirModal(zona)}
          >
            <h2 className="zona-titulo">{zona.nombre}</h2>
            <p className="zona-ratio">
              {zona.maquinasSeleccionadas.length} / {capacidadZona}
            </p>
          </div>
        ))}

        <div className="zona-card" onClick={handleAgregar}>
          <h2 className="zona-titulo">Agregar</h2>
          <p className="zona-ratio">+</p>
        </div>
      </div>

      <ModalZonas
        isOpen={modalAbierto}
        onClose={cerrarModal}
        zona={zonaSeleccionada}
        onSave={guardarZona}
        onDelete={handleDelete}
        isLoading={false}
      />
    </div>
  );
};

export default ZonasPanel;
