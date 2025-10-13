import React from 'react';
import { Link } from 'react-router-dom';
import { Title, Meta } from 'react-head';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../App.css';

const LandingPage = () => {
  return (
    <>
      <Title>Suara Rakyat - Platform Partisipasi Publik</Title>
      <Meta name="description" content="Laporkan keluhan, pantau kinerja pejabat, dan berpartisipasi dalam pemilu melalui Suara Rakyat, platform untuk transparansi dan akuntabilitas." />
      <Header />
      <section className="hero">
        <div className="container">
          <h1>Suara Rakyat: Wujudkan Transparansi & Akuntabilitas</h1>
          <p>Platform partisipasi publik untuk melaporkan keluh kesah, memantau kinerja pejabat, dan berpartisipasi dalam pemilihan umum.</p>
          <div className="hero-cta">
            <Link to="/report" className="btn-primary">Laporkan Sekarang</Link>
            <Link to="/elections" className="btn-secondary">Lihat Kandidat</Link>
          </div>
        </div>
      </section>

      <section className="quick-stats">
        <div className="container">
          <div className="stat-card">
            <h3>Laporan Hari Ini</h3>
            <p>1,234</p>
          </div>
          <div className="stat-card">
            <h3>Proyek Selesai</h3>
            <p>567</p>
          </div>
          <div className="stat-card">
            <h3>Pemilu Aktif</h3>
            <p>3</p>
          </div>
        </div>
      </section>

      <section className="map-filter">
        <div className="container">
          <h2>Cari Berdasarkan Wilayah</h2>
          <select>
            <option value="">Pilih Provinsi</option>
          </select>
          <select>
            <option value="">Pilih Kabupaten</option>
          </select>
        </div>
      </section>

      <section className="featured-news">
        <div className="container">
          <h2>Berita & Pengumuman Terbaru</h2>
          <div className="news-grid">
            <div className="news-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Berita 1" />
              <h3>Judul Berita Penting 1</h3>
              <p>Ringkasan singkat berita atau pengumuman...</p>
              <Link to="/news/berita-penting-1">Baca Selengkapnya</Link>
            </div>
            <div className="news-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Berita 2" />
              <h3>Judul Berita Penting 2</h3>
              <p>Ringkasan singkat berita atau pengumuman...</p>
              <Link to="/news/berita-penting-2">Baca Selengkapnya</Link>
            </div>
            <div className="news-card">
              <img src="/assets/img/mederkkaa.jpg" alt="Berita 3" />
              <h3>Judul Berita Penting 3</h3>
              <p>Ringkasan singkat berita atau pengumuman...</p>
              <Link to="/news/berita-penting-3">Baca Selengkapnya</Link>
            </div>
          </div>
        </div>
      </section>
      <Footer />
    </>
  );
};

export default LandingPage;
