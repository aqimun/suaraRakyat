import React from 'react';
import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';

const VotingPage = () => {
  return (
    <>
      <Header />
      <main className="voting-page-content">
        <div className="container">
          <h1>Voting & Pemilu</h1>
          <p className="page-description">Ikuti pemilihan umum, lihat informasi kandidat, dan berikan suara Anda untuk masa depan yang lebih baik.</p>

          <div className="election-events-grid">
            {/* Election event cards will be loaded here */}
            <div className="election-card">
              <div className="card-header">
                <h3>Pemilihan Walikota Bandung 2026</h3>
                <span className="status-badge active">Aktif</span>
              </div>
              <p className="election-date">Periode Voting: 10 Okt - 20 Okt 2026</p>
              <p className="election-description">Pilih pemimpin terbaik untuk Kota Bandung. Pelajari visi misi kandidat sebelum memilih.</p>
              <div className="candidate-list">
                <h4>Kandidat:</h4>
                <ul>
                  <li>Budi Santoso</li>
                  <li>Dewi Lestari</li>
                </ul>
              </div>
              <Link to="/elections/walikota-bandung-2026" className="btn-vote">Lihat Detail & Voting</Link>
            </div>

            <div className="election-card">
              <div className="card-header">
                <h3>Pemilihan Gubernur Jawa Barat 2027</h3>
                <span className="status-badge upcoming">Mendatang</span>
              </div>
              <p className="election-date">Periode Voting: 01 Jan - 10 Jan 2027</p>
              <p className="election-description">Persiapkan diri Anda untuk memilih Gubernur Jawa Barat. Informasi kandidat akan segera tersedia.</p>
              <Link to="/elections/gubernur-jabar-2027" className="btn-detail">Lihat Detail</Link>
            </div>

            <div className="election-card">
              <div className="card-header">
                <h3>Pemilihan Presiden 2029</h3>
                <span className="status-badge finished">Selesai</span>
              </div>
              <p className="election-date">Periode Voting: 14 Feb - 24 Feb 2029</p>
              <p className="election-description">Hasil pemilihan presiden sebelumnya. Lihat audit dan transparansi hasil.</p>
              <Link to="/elections/presiden-2029" className="btn-results">Lihat Hasil</Link>
            </div>
          </div>

          <div className="candidate-nomination-info">
            <h2>Ingin Mencalonkan Diri?</h2>
            <p>Jika Anda seorang Pejabat dan ingin mencalonkan diri dalam pemilihan mendatang, silakan ajukan nominasi Anda.</p>
            <Link to="/dashboard/official/nominate" className="btn-nominate">Ajukan Nominasi</Link>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
};

export default VotingPage;
