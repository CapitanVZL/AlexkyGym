import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Registro from "./Pagina registro/registro-page";
import Zones from "./Pantalla zonas/zona-page";
import "./App.css";
import Navbar from "./navbarra";
import Panel from "./panel-maquinas";
import Registra_zonas from "./Pantalla zonas/registro-zonas";
import Historico_page from "./Pantalla historico/histroico-estado-page";

const Home: React.FC = () => {
  return (
    <div className="parent">
      <div className="div1">
        <Navbar />
      </div>

      <div className="div3">
        <Panel />
      </div>

      <div className="div4"></div>
    </div>
  );
};

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/page-registro" element={<Registro />} />
        <Route path="/page-zonas" element={<Zones />} />
        <Route path="/page-zonas/register-zonas" element={<Registra_zonas />} />
        <Route path="/page-historico" element={<Historico_page />} />
      </Routes>
    </Router>
  );
};

export default App;
