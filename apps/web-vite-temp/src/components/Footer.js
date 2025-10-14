import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {
  return (
    <footer className="main-footer">
      <div className="container">
        <div className="footer-links">
          <Link to="/help">Bantuan</Link>
          <Link to="/privacy">Kebijakan Privasi</Link>
          <Link to="/terms">Syarat & Ketentuan</Link>
        </div>
        <p>&copy; {new Date().getFullYear()} Suara Rakyat. Dikelola oleh KPU.</p>
      </div>
    </footer>
  );
};

export default Footer;
