import React from 'react';
import { Link } from 'react-router-dom';

const PrivacyPolicyPage = () => {
  return (
    <div className="container" style={{ padding: '20px' }}>
      <h1>Kebijakan Privasi</h1>
      <p>Ini adalah halaman Kebijakan Privasi. Informasi lebih lanjut akan ditambahkan di sini.</p>
      <p><Link to="/">Kembali ke Beranda</Link></p>
    </div>
  );
};

export default PrivacyPolicyPage;
