import { Link } from "react-router-dom";
import "./navbarra.css"; // Archivo de estilos separado

const Navbar: React.FC = () => {
  return (
    <nav className="navbar-container">
      <div className="navbar-content">
        <div className="navbar-logo">
          <div className="navbar-logo-img"></div>
          <span className="logo-text">ALESKYGYM</span>
        </div>
        <div className="navbar-buttons">
          <Link to="/" className="no-underline">
            <button className="nav-button primary">Inicio</button>
          </Link>
          <Link to="/page-zonas" className="no-underline">
            <button className="nav-button secondary">Zonas</button>
          </Link>
        </div>
        <div className="navbar-spacer"></div> {/* Espacio flexible opcional */}
      </div>
    </nav>
  );
};

export default Navbar;
