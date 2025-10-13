import React from 'react';
import { Link } from 'react-router-dom';

const TermsAndConditionsPage = () => {
  return (
    <div className="container" style={{ padding: '20px' }}>
      <h1>Syarat & Ketentuan</h1>
      <p>Ini adalah halaman Syarat & Ketentuan. Informasi lebih lanjut akan ditambahkan di sini.</p>
      <p><Link to="/">Kembali ke Beranda</Link></p>
    </div>
  );
};

export default TermsAndConditionsPage;
