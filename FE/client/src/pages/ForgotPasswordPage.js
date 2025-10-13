import React from 'react';
import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';

const ForgotPasswordPage = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Reset code sent!'); // Placeholder for actual submission logic
  };

  return (
    <>
      <Header />
      <div className="forgot-password-container">
        <div className="forgot-password-box">
          <h2>Lupa Kata Sandi?</h2>
          <p>Masukkan email atau nomor HP Anda yang terdaftar untuk menerima kode reset kata sandi.</p>
          <form id="forgotPasswordForm" onSubmit={handleSubmit}>
            <div className="input-group">
              <label htmlFor="emailPhone">Email / Nomor HP</label>
              <input type="text" id="emailPhone" name="emailPhone" autoComplete="username" required />
            </div>
            <button type="submit" className="btn-submit">Kirim Kode Reset</button>
          </form>
          <p className="back-to-login">Kembali ke <Link to="/login">Login</Link></p>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default ForgotPasswordPage;
