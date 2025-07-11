import React from "react";
import Navbar from "../navbarra";
import Panel_zonas from "./panel-zonas";
import "./zona-page.css";

const Zonas: React.FC = () => {
  return (
    <div className="parent-zona">
      <div className="div1-zona">
        <Navbar />
      </div>

      <div className="div3-zona">
        <Panel_zonas />
      </div>

      <div className="div4-zona"></div>
    </div>
  );
};

export default Zonas;
