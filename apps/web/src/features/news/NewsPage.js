import React from 'react';
import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';

const NewsPage = () => {
  return (
    <>
      <Header />
      <main className="news-page-content">
        <div className="container">
          <h1>Berita & Pengumuman</h1>

          <div className="news-filters">
            <input type="text" placeholder="Cari berita..." className="filter-search-input" />
            <select className="filter-select">
              <option value="">Semua Kategori</option>
              <option value="umum">Umum</option>
              <option value="pemerintahan">Pemerintahan</option>
              <option value="pemilu">Pemilu</option>
            </select>
            <select className="filter-select">
              <option value="">Semua Wilayah</option>
              {/* Options will be dynamically loaded */}
            </select>
            <button className="btn-filter">Filter</button>
          </div>

          <div className="news-list-grid">
            {/* News articles will be loaded here */}
            <div className="news-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Berita Contoh 1" />
              <div className="card-content">
                <h3>Judul Berita Contoh Panjang 1</h3>
                <p className="news-meta">Kategori: Umum | Tanggal: 2025-09-22</p>
                <p>Ringkasan singkat dari berita ini. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                <Link to="/news/berita-contoh-1" className="read-more">Baca Selengkapnya</Link>
              </div>
            </div>
            <div className="news-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Berita Contoh 2" />
              <div className="card-content">
                <h3>Judul Berita Contoh Panjang 2</h3>
                <p className="news-meta">Kategori: Pemerintahan | Tanggal: 2025-09-21</p>
                <p>Ringkasan singkat dari berita ini. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                <Link to="/news/berita-contoh-2" className="read-more">Baca Selengkapnya</Link>
              </div>
            </div>
            <div className="news-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Berita Contoh 3" />
              <div className="card-content">
                <h3>Judul Berita Contoh Panjang 3</h3>
                <p className="news-meta">Kategori: Pemilu | Tanggal: 2025-09-20</p>
                <p>Ringkasan singkat dari berita ini. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
                <Link to="/news/berita-contoh-3" className="read-more">Baca Selengkapnya</Link>
              </div>
            </div>
          </div>

          <div className="pagination">
            <button className="btn-page prev">Sebelumnya</button>
            <span className="page-info">Halaman 1 dari 5</span>
            <button className="btn-page next">Selanjutnya</button>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
};

export default NewsPage;
