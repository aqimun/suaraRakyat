import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Header from '../../components/Header.jsx';
import Footer from '../../components/Footer.jsx';

const ComplaintPage = () => {
  const [step, setStep] = useState(1);
  const [category, setCategory] = useState('');

  const handleNext = () => {
    setStep(prevStep => prevStep + 1);
  };

  const handlePrev = () => {
    setStep(prevStep => prevStep - 1);
  };

  const handleCategorySelect = (selectedCategory) => {
    setCategory(selectedCategory);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Complaint submitted!'); // Placeholder for actual submission logic
  };

  return (
    <>
      <Header />
      <main className="complaint-page-content">
        <div className="container">
          <h1>Laporkan Keluh Kesah Anda</h1>
          <p className="page-description">Sampaikan laporan Anda mengenai masalah publik. Laporan Anda akan diproses dan ditindaklanjuti oleh pihak berwenang.</p>

          <div className="complaint-form-box">
            <div className="stepper">
              <div className={`step ${step === 1 ? 'active' : ''}`} id="step1">1. Kategori & Lokasi</div>
              <div className={`step ${step === 2 ? 'active' : ''}`} id="step2">2. Detail Laporan</div>
              <div className={`step ${step === 3 ? 'active' : ''}`} id="step3">3. Media & Privasi</div>
            </div>
            <form id="complaintForm" onSubmit={handleSubmit}>
              {/* Step 1: Category & Location */}
              {step === 1 && (
                <div className="form-step active" id="formStep1">
                  <div className="input-group">
                    <label htmlFor="category">Pilih Kategori Laporan</label>
                    <div className="category-icons">
                      <div className={`icon-card ${category === 'infrastruktur' ? 'selected' : ''}`} onClick={() => handleCategorySelect('infrastruktur')} data-category="infrastruktur">
                        <img src="https://via.placeholder.com/50?text=Infra" alt="Infrastruktur" />
                        <span>Infrastruktur</span>
                      </div>
                      <div className={`icon-card ${category === 'lingkungan' ? 'selected' : ''}`} onClick={() => handleCategorySelect('lingkungan')} data-category="lingkungan">
                        <img src="https://via.placeholder.com/50?text=Lingk" alt="Lingkungan" />
                        <span>Lingkungan</span>
                      </div>
                      <div className={`icon-card ${category === 'pelayanan-publik' ? 'selected' : ''}`} onClick={() => handleCategorySelect('pelayanan-publik')} data-category="pelayanan-publik">
                        <img src="https://via.placeholder.com/50?text=Pelayanan" alt="Pelayanan Publik" />
                        <span>Pelayanan Publik</span>
                      </div>
                      <div className={`icon-card ${category === 'lainnya' ? 'selected' : ''}`} onClick={() => handleCategorySelect('lainnya')} data-category="lainnya">
                        <img src="https://via.placeholder.com/50?text=Lainnya" alt="Lainnya" />
                        <span>Lainnya</span>
                      </div>
                    </div>
                    <input type="hidden" id="category" name="category" value={category} required />
                  </div>
                  <div className="input-group">
                    <label htmlFor="location">Lokasi Kejadian</label>
                    <input type="text" id="location" name="location" placeholder="Cari lokasi atau gunakan peta" required />
                    <div id="map-placeholder" style={{ height: '200px', backgroundColor: '#e0e0e0', marginTop: '10px', display: 'flex', justifyContent: 'center', alignItems: 'center', color: '#6c757d' }}>
                      Placeholder Peta
                    </div>
                  </div>
                  <button type="button" className="btn-next" onClick={handleNext}>Lanjut</button>
                </div>
              )}

              {/* Step 2: Report Details */}
              {step === 2 && (
                <div className="form-step active" id="formStep2">
                  <div className="input-group">
                    <label htmlFor="title">Judul Laporan</label>
                    <input type="text" id="title" name="title" required />
                  </div>
                  <div className="input-group">
                    <label htmlFor="description">Deskripsi Lengkap</label>
                    <textarea id="description" name="description" rows="8" required></textarea>
                  </div>
                  <button type="button" className="btn-prev" onClick={handlePrev}>Kembali</button>
                  <button type="button" className="btn-next" onClick={handleNext}>Lanjut</button>
                </div>
              )}

              {/* Step 3: Media & Privacy */}
              {step === 3 && (
                <div className="form-step active" id="formStep3">
                  <div className="input-group">
                    <label htmlFor="mediaUpload">Unggah Foto/Video (Opsional)</label>
                    <input type="file" id="mediaUpload" name="mediaUpload" accept="image/*,video/*" multiple />
                    <p className="hint">Ukuran file maksimal 25MB per file.</p>
                    <div className="media-preview" id="mediaPreview"></div>
                  </div>
                  <div className="input-group checkbox-group">
                    <input type="checkbox" id="privacyFlag" name="privacyFlag" />
                    <label htmlFor="privacyFlag">Laporkan secara anonim (data pribadi Anda tidak akan ditampilkan ke publik).</label>
                  </div>
                  <button type="button" className="btn-prev" onClick={handlePrev}>Kembali</button>
                  <button type="submit" className="btn-submit">Kirim Laporan</button>
                </div>
              )}
            </form>
            <p className="track-report-link">Sudah melaporkan? <Link to="/report/track">Lacak Laporan Anda</Link></p>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
};

export default ComplaintPage;
