import React from "react";

import "./sube-img.css";

const UploadImagen: React.FC = () => {
  return (
    <div className="registro-imagen">
      <h2>Anexar Imagen</h2>
      <div className="imagen-contenedor">
        <p>Sube aquí la imagen</p>
      </div>
      <input type="file" accept="image/*" />
    </div>
  );
};

export default UploadImagen;
