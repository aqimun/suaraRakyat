import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { API_BASE_URL } from '../../config.js';
import Header from '../../components/Header.jsx';
import Footer from '../../components/Footer.jsx';

const LoginPage = () => {
  const [emailPhone, setEmailPhone] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ emailPhone, password }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('jwtToken', data.accessToken);
        alert('Login successful!');
        navigate('/'); // Redirect to home page or dashboard
      } else {
        const errorData = await response.text();
        alert(`Login failed: ${errorData}`);
      }
    } catch (error) {
      console.error('Error during login:', error);
      alert('An error occurred during login.');
    }
  };

  return (
    <>
      <Header />
      <div className="login-container">
        <div className="login-box">
          <h2>Masuk ke Akun Anda</h2>
          <form id="loginForm" onSubmit={handleSubmit}>
            <div className="input-group">
              <label htmlFor="emailPhone">Email / Nomor HP</label>
              <input
                type="text"
                id="emailPhone"
                name="emailPhone"
                autoComplete="username"
                value={emailPhone}
                onChange={(e) => setEmailPhone(e.target.value)}
                required
              />
            </div>
            <div className="input-group">
              <label htmlFor="password">Kata Sandi</label>
              <input
                type="password"
                id="password"
                name="password"
                autoComplete="current-password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <div className="options-group">
              <label className="remember-me">
                <input type="checkbox" id="rememberMe" name="rememberMe" /> Ingat Saya
              </label>
              <Link to="/forgot-password" className="forgot-password-link">Lupa kata sandi?</Link>
            </div>
            <button type="submit" className="btn-login-submit">Masuk</button>
          </form>
          <div className="social-login-divider">
            <span>Atau</span>
          </div>
          <button className="btn-social-login">Masuk dengan SSO Pemerintah (Opsional)</button>
          <p className="signup-link">Belum punya akun? <Link to="/signup">Daftar Sekarang</Link></p>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default LoginPage;
