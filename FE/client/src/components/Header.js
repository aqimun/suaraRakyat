import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  return (
    <header className="main-header">
      <div className="container">
        <div className="logo">
          <img src="/assets/img/logo-suara-rakyat.png" alt="Logo Suara Rakyat" />
        </div>
        <nav className={`main-nav ${isMobileMenuOpen ? 'mobile-open' : ''}`}>
          <ul className="nav-links">
            <li><Link to="/" onClick={toggleMobileMenu}>Beranda</Link></li>
            <li><Link to="/officials" onClick={toggleMobileMenu}>Pejabat</Link></li>
            <li><Link to="/news" onClick={toggleMobileMenu}>Berita</Link></li>
            <li><Link to="/elections" onClick={toggleMobileMenu}>Voting</Link></li>
            <li><Link to="/help" onClick={toggleMobileMenu}>Bantuan</Link></li>
          </ul>
        </nav>
        <div className="header-actions">
          <input type="text" placeholder="Cari..." className="search-input" />
          <Link to="/login" className="btn-login">Login</Link>
          <Link to="/signup" className="btn-register">Daftar</Link>
          <button className="btn-accessibility">A11y</button>
        </div>
        <button className="hamburger-menu" onClick={toggleMobileMenu}>
          <span></span>
          <span></span>
          <span></span>
        </button>
      </div>
    </header>
  );
};

export default Header;
