import React from 'react';
import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';

const OfficialsPage = () => {
  return (
    <>
      <Header />
      <main className="officials-page-content">
        <div className="container">
          <h1>Direktori Pejabat</h1>
          <p className="page-description">Temukan dan pantau profil, kinerja, serta proyek-proyek yang telah dilakukan oleh para pejabat publik.</p>

          <div className="officials-filters">
            <input type="text" placeholder="Cari nama pejabat..." className="filter-search-input" />
            <select className="filter-select">
              <option value="">Semua Jabatan</option>
              <option value="walikota">Walikota</option>
              <option value="bupati">Bupati</option>
              <option value="gubernur">Gubernur</option>
              <option value="presiden">Presiden</option>
            </select>
            <select className="filter-select">
              <option value="">Semua Wilayah</option>
              {/* Options will be dynamically loaded */}
            </select>
            <button className="btn-filter">Filter</button>
          </div>

          <div className="officials-list-grid">
            {/* Official cards will be loaded here */}
            <div className="official-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Foto Pejabat 1" />
              <div className="card-content">
                <h3>Budi Santoso</h3>
                <p className="official-title">Walikota Bandung</p>
                <p className="official-stats">Proyek Selesai: 12 | Laporan Ditangani: 85</p>
                <Link to="/officials/budi-santoso" className="view-profile">Lihat Profil</Link>
              </div>
            </div>
            <div className="official-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Foto Pejabat 2" />
              <div className="card-content">
                <h3>Siti Aminah</h3>
                <p className="official-title">Gubernur Jawa Barat</p>
                <p className="official-stats">Proyek Selesai: 25 | Laporan Ditangani: 150</p>
                <Link to="/officials/siti-aminah" className="view-profile">Lihat Profil</Link>
              </div>
            </div>
            <div className="official-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Foto Pejabat 3" />
              <div className="card-content">
                <h3>Joko Susilo</h3>
                <p className="official-title">Bupati Sleman</p>
                <p className="official-stats">Proyek Selesai: 8 | Laporan Ditangani: 60</p>
                <Link to="/officials/joko-susilo" className="view-profile">Lihat Profil</Link>
              </div>
            </div>
          </div>

          <div className="pagination">
            <button className="btn-page prev">Sebelumnya</button>
            <span className="page-info">Halaman 1 dari 3</span>
            <button className="btn-page next">Selanjutnya</button>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
};

export default OfficialsPage;
