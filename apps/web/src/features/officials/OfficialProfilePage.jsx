import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Header from '../../components/Header.jsx';
import Footer from '../../components/Footer.jsx';

const OfficialProfilePage = () => {
  const [activeTab, setActiveTab] = useState('visi-misi');

  const handleTabClick = (tabId) => {
    setActiveTab(tabId);
  };

  return (
    <>
      <Header />
      <main className="official-profile-content">
        <div className="container">
          <div className="profile-header">
            <img src="/assets/img/mederkkaa.jpg" alt="Foto Pejabat" className="profile-photo" />
            <div className="profile-info">
              <h1>Budi Santoso</h1>
              <p className="official-title">Walikota Bandung</p>
              <p className="public-contact">Email: budi.santoso@bandung.go.id | Telepon: (022) 123456</p>
              <button className="btn-clarification">Minta Klarifikasi</button>
            </div>
          </div>

          <div className="profile-tabs">
            <button
              className={`tab-button ${activeTab === 'visi-misi' ? 'active' : ''}`}
              onClick={() => handleTabClick('visi-misi')}
            >
              Visi & Misi
            </button>
            <button
              className={`tab-button ${activeTab === 'projects' ? 'active' : ''}`}
              onClick={() => handleTabClick('projects')}
            >
              Proyek
            </button>
            <button
              className={`tab-button ${activeTab === 'complaints' ? 'active' : ''}`}
              onClick={() => handleTabClick('complaints')}
            >
              Laporan Masyarakat
            </button>
            <button
              className={`tab-button ${activeTab === 'documents' ? 'active' : ''}`}
              onClick={() => handleTabClick('documents')}
            >
              Dokumen
            </button>
          </div>

          {activeTab === 'visi-misi' && (
            <div className="tab-content active" id="visi-misi">
              <h2>Visi</h2>
              <p>Mewujudkan Bandung sebagai kota yang maju, sejahtera, dan berbudaya dengan partisipasi aktif masyarakat.</p>
              <h2>Misi</h2>
              <ul>
                <li>Meningkatkan kualitas pelayanan publik yang transparan dan akuntabel.</li>
                <li>Mengembangkan ekonomi kreatif dan UMKM untuk kesejahteraan masyarakat.</li>
                <li>Membangun infrastruktur yang berkelanjutan dan ramah lingkungan.</li>
                <li>Mendorong inovasi dan teknologi dalam tata kelola pemerintahan.</li>
              </ul>
            </div>
          )}

          {activeTab === 'projects' && (
            <div className="tab-content" id="projects">
              <h2>Proyek-Proyek</h2>
              <div className="projects-grid">
                <div className="project-card">
                  <img src="/assets/img/mederkkaa.jpg" alt="Proyek Revitalisasi Taman Kota" />
                  <h3>Revitalisasi Taman Kota</h3>
                  <p>Status: Selesai</p>
                  <p>Anggaran: Rp 5 Miliar</p>
                  <div className="project-evidence">
                    <img src="/assets/img/mederkkaa.jpg" alt="Sebelum Proyek" className="before-after-img" />
                    <img src="/assets/img/mederkkaa.jpg" alt="Sesudah Proyek" className="before-after-img" />
                  </div>
                  <Link to="#" className="btn-detail">Lihat Detail</Link>
                </div>
                <div className="project-card">
                  <img src="/assets/img/mederkkaa.jpg" alt="Proyek Pembangunan Jembatan" />
                  <h3>Pembangunan Jembatan Layang</h3>
                  <p>Status: Berjalan</p>
                  <p>Anggaran: Rp 15 Miliar</p>
                  <div className="project-evidence">
                    <img src="/assets/img/mederkkaa.jpg" alt="Proses Pembangunan" className="before-after-img" />
                  </div>
                  <Link to="#" className="btn-detail">Lihat Detail</Link>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'complaints' && (
            <div className="tab-content" id="complaints">
              <h2>Statistik Laporan Masyarakat</h2>
              <div className="complaint-stats-chart">
                {/* Chart placeholder */}
                <img src="https://via.placeholder.com/600x300?text=Grafik+Statistik+Laporan" alt="Grafik Statistik Laporan" />
              </div>
              <div className="complaint-timeline">
                <h3>Timeline Laporan Terbaru</h3>
                <div className="timeline-item">
                  <p className="timeline-date">2025-09-20</p>
                  <p className="timeline-description">Laporan "Jalan Rusak di Jl. Merdeka" ditangani.</p>
                  <span className="status-badge resolved">Selesai</span>
                </div>
                <div className="timeline-item">
                  <p className="timeline-date">2025-09-18</p>
                  <p className="timeline-description">Laporan "Sampah Menumpuk di Pasar" sedang diproses.</p>
                  <span className="status-badge in-progress">Dalam Proses</span>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'documents' && (
            <div className="tab-content" id="documents">
              <h2>Dokumen Terkait</h2>
              <ul>
                <li><Link to="#">APBD Kota Bandung 2025</Link></li>
                <li><Link to="#">Laporan Akuntabilitas Kinerja Instansi Pemerintah (LAKIP) 2024</Link></li>
              </ul>
            </div>
          )}
        </div>
      </main>
      <Footer />
    </>
  );
};

export default OfficialProfilePage;
