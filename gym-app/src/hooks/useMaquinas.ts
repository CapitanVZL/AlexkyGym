// hooks/useMaquinas.ts
import { useState, useEffect } from "react";
import { Maquina } from "./types";

export const useMaquinas = () => {
  const [maquinasData, setMaquinasData] = useState<Maquina[]>([]);
  const [maquinasFiltradas, setMaquinasFiltradas] = useState<Maquina[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchMaquinas = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/equipos");
        if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);
        const data = await response.json();
        setMaquinasData(data);
        setMaquinasFiltradas(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : "Error desconocido");
      } finally {
        setIsLoading(false);
      }
    };

    fetchMaquinas();
  }, []);

  const filtrarPorEstado = (estado: string) => {
    if (estado === "todas") {
      setMaquinasFiltradas(maquinasData);
    } else {
      setMaquinasFiltradas(
        maquinasData.filter((m) => m.estado.toLowerCase() === estado)
      );
    }
  };

  const actualizarMaquina = (maquinaActualizada: Maquina) => {
    setMaquinasData((prev) =>
      prev.map((m) =>
        m.serial === maquinaActualizada.serial ? maquinaActualizada : m
      )
    );
    setMaquinasFiltradas((prev) =>
      prev.map((m) =>
        m.serial === maquinaActualizada.serial ? maquinaActualizada : m
      )
    );
  };

  return {
    maquinasFiltradas,
    isLoading,
    error,
    filtrarPorEstado,
    actualizarMaquina,
    setMaquinasData,
  };
};
