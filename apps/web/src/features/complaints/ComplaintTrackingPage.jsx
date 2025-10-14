import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Header from '../../components/Header.jsx';
import Footer from '../../components/Footer.jsx';

const ComplaintTrackingPage = () => {
  const [ticketId, setTicketId] = useState('');
  const [trackingResults, setTrackingResults] = useState(null);

  const handleInputChange = (e) => {
    setTicketId(e.target.value);
  };

  const handleTrackSubmit = (e) => {
    e.preventDefault();
    // Placeholder for actual API call to fetch complaint status
    const dummyData = {
      id: ticketId,
      category: 'Infrastruktur',
      title: 'Jalan Rusak di Jl. Merdeka',
      description: 'Ada banyak lubang di jalan utama yang menyebabkan kemacetan dan bahaya bagi pengendara.',
      location: 'Jl. Merdeka, Bandung',
      dateSubmitted: '2025-09-22 09:00',
      currentStatus: 'Selesai',
      timeline: [
        { date: '2025-09-22 10:00', event: 'Laporan diajukan.' },
        { date: '2025-09-22 14:30', event: 'Laporan sedang dalam proses verifikasi oleh Staff Admin.' },
        { date: '2025-09-23 09:00', event: 'Laporan ditugaskan kepada Dinas Pekerjaan Umum.' },
        { date: '2025-09-25 16:00', event: 'Perbaikan jalan telah selesai dilakukan.' },
        { date: '2025-09-26 10:00', event: 'Laporan ditutup dan diselesaikan.' },
      ],
      media: [
        '/assets/img/mederkkaa.jpg',
      ],
    };
    setTrackingResults(dummyData);
  };

  const getStatusClass = (status) => {
    switch (status) {
      case 'Diajukan': return 'active';
      case 'Diproses': return 'in-progress';
      case 'Ditugaskan': return 'assigned';
      case 'Selesai': return 'resolved';
      default: return '';
    }
  };

  return (
    <>
      <Header />
      <main className="complaint-tracking-content">
        <div className="container">
          <h1>Lacak Status Laporan Anda</h1>
          <p className="page-description">Masukkan ID laporan Anda untuk melihat status dan riwayat penanganan.</p>

          <div className="tracking-box">
            <form id="trackingForm" onSubmit={handleTrackSubmit}>
              <div className="input-group">
                <label htmlFor="ticketId">ID Laporan</label>
                <input
                  type="text"
                  id="ticketId"
                  name="ticketId"
                  placeholder="Contoh: SR-YYYY-XXXXX"
                  value={ticketId}
                  onChange={handleInputChange}
                  required
                />
              </div>
              <button type="submit" className="btn-track">Lacak Laporan</button>
            </form>

            {trackingResults && (
              <div id="trackingResults" className="tracking-results">
                <h2>Detail Laporan <span id="displayTicketId">{trackingResults.id}</span></h2>
                <div className="status-indicator">
                  <div className={`status-step ${getStatusClass('Diajukan')}`} id="status-submitted">
                    <div className="circle"></div>
                    <span>Diajukan</span>
                  </div>
                  <div className="status-line"></div>
                  <div className={`status-step ${getStatusClass('Diproses')}`} id="status-processing">
                    <div className="circle"></div>
                    <span>Diproses</span>
                  </div>
                  <div className="status-line"></div>
                  <div className={`status-step ${getStatusClass('Ditugaskan')}`} id="status-assigned">
                    <div className="circle"></div>
                    <span>Ditugaskan</span>
                  </div>
                  <div className="status-line"></div>
                  <div className={`status-step ${getStatusClass('Selesai')}`} id="status-resolved">
                    <div className="circle"></div>
                    <span>Selesai</span>
                  </div>
                </div>

                <div className="report-details">
                  <h3>Informasi Laporan</h3>
                  <p><strong>Kategori:</strong> <span id="detailCategory">{trackingResults.category}</span></p>
                  <p><strong>Judul:</strong> <span id="detailTitle">{trackingResults.title}</span></p>
                  <p><strong>Deskripsi:</strong> <span id="detailDescription">{trackingResults.description}</span></p>
                  <p><strong>Lokasi:</strong> <span id="detailLocation">{trackingResults.location}</span></p>
                  <p><strong>Tanggal Diajukan:</strong> <span id="detailDate">{trackingResults.dateSubmitted}</span></p>
                  <p><strong>Status Saat Ini:</strong> <span id="detailCurrentStatus" className="status-badge">{trackingResults.currentStatus}</span></p>
                </div>

                <div className="report-timeline">
                  <h3>Riwayat Penanganan</h3>
                  <div id="timelineItems">
                    {trackingResults.timeline.map((item, index) => (
                      <div className="timeline-item" key={index}>
                        <p className="timeline-date">{item.date}</p>
                        <p className="timeline-event">{item.event}</p>
                      </div>
                    ))}
                  </div>
                </div>

                {trackingResults.media.length > 0 && (
                  <div className="report-media">
                    <h3>Media Terlampir</h3>
                    <div id="attachedMedia" className="media-grid">
                      {trackingResults.media.map((src, index) => (
                        <img src={src} alt={`Media Laporan ${index + 1}`} key={index} />
                      ))}
                    </div>
                  </div>
                )}
              </div>
            )}
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
};

export default ComplaintTrackingPage;
