import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext'; // Import useAuth hook

const Header = () => {
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
  const [isAccessibilityMode, setIsAccessibilityMode] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const { isLoggedIn, user, logout } = useAuth(); // Use the auth context

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  const toggleAccessibilityMode = () => {
    setIsAccessibilityMode(!isAccessibilityMode);
  };

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = (event) => {
    event.preventDefault();
    console.log('Search term:', searchTerm);
    // Implement actual search logic here, e.g., navigate to a search results page
    // navigate(`/search?query=${searchTerm}`);
  };

  useEffect(() => {
    if (isAccessibilityMode) {
      document.body.classList.add('accessibility-mode');
    } else {
      document.body.classList.remove('accessibility-mode');
    }
  }, [isAccessibilityMode]);

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
            <li><Link to="/projects" onClick={toggleMobileMenu}>Projects</Link></li>
            <li><Link to="/help" onClick={toggleMobileMenu}>Bantuan</Link></li>
          </ul>
        </nav>
        <div className="header-actions">
          <form onSubmit={handleSearchSubmit}>
            <input
              type="text"
              placeholder="Cari..."
              className="search-input"
              value={searchTerm}
              onChange={handleSearchChange}
            />
            <button type="submit" style={{ display: 'none' }}>Search</button> {/* Hidden submit button */}
          </form>
          {isLoggedIn ? (
            <>
              <Link to="/profile" className="btn-profile">Profil ({user?.nameDisplay || user?.email})</Link>
              <button onClick={logout} className="btn-logout">Logout</button>
            </>
          ) : (
            <>
              <Link to="/login" className="btn-login">Login</Link>
              <Link to="/signup" className="btn-register">Daftar</Link>
            </>
          )}
          <button className="btn-accessibility" onClick={toggleAccessibilityMode}>A11y</button>
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
