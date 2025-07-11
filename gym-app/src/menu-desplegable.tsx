import React, { useState, useRef, useEffect } from "react";
import ReactDOM from "react-dom";
import { Link } from "react-router-dom";
import { Maquina } from "./hooks/types";
import "./menu-desplegable.css";

interface MenuDesplegableProps {
  maquina: Maquina;
  onEditar: (maquina: Maquina) => void;
  vistaHorizontal?: boolean;
}

const MenuDesplegable: React.FC<MenuDesplegableProps> = ({
  maquina,
  onEditar,
  vistaHorizontal = false,
}) => {
  const [estaAbierto, setEstaAbierto] = useState(false);
  const [posicion, setPosicion] = useState<{
    top: number;
    left: number;
  } | null>(null);

  const botonRef = useRef<HTMLButtonElement>(null);
  const menuRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (estaAbierto && botonRef.current) {
      const rect = botonRef.current.getBoundingClientRect();
      setPosicion({
        top: rect.bottom + window.scrollY,
        left: rect.right + window.scrollX - 160, // Ajusta según el ancho
      });
    } else {
      setPosicion(null);
    }
  }, [estaAbierto]);

  // Cierra si se hace clic fuera
  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (
        menuRef.current &&
        !menuRef.current.contains(e.target as Node) &&
        !botonRef.current?.contains(e.target as Node)
      ) {
        setEstaAbierto(false);
      }
    };

    if (estaAbierto) {
      document.addEventListener("mousedown", handleClickOutside);
    }

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [estaAbierto]);

  const renderMenu = () => {
    if (!estaAbierto || !posicion) return null;

    return ReactDOM.createPortal(
      <div
        ref={menuRef}
        className="opciones-menu-flotante"
        style={{
          position: "absolute",
          top: posicion.top,
          left: posicion.left,
          zIndex: 1000,
        }}
      >
        <button className="opcion-flotante">
          <span>🗑️</span> Eliminar
        </button>
        <button className="opcion-flotante">
          <Link to="/page-historico" className="no-underline">
            <span>🔄</span> Cambiar estado
          </Link>
        </button>
        <button
          className="opcion-flotante"
          onClick={() => {
            setEstaAbierto(false);
            onEditar(maquina);
          }}
        >
          <span>✏️</span> Editar
        </button>
      </div>,
      document.body
    );
  };

  return (
    <div
      className={`contenedor-menu-flotante ${
        vistaHorizontal ? "horizontal" : ""
      }`}
    >
      <button
        ref={botonRef}
        className="boton-menu-flotante"
        onClick={(e) => {
          e.stopPropagation();
          setEstaAbierto((prev) => !prev);
        }}
        aria-label="Menú de opciones"
      >
        <svg width="32" height="32" viewBox="0 0 32 32" fill="#666">
          <circle cx="16" cy="2" r="5" />
          <circle cx="16" cy="16" r="5" />
          <circle cx="16" cy="30" r="5" />
        </svg>
      </button>

      {renderMenu()}
    </div>
  );
};

export default MenuDesplegable;
