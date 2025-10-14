import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { API_BASE_URL } from '../../config.js';
import Header from '../../components/Header.jsx';
import Footer from '../../components/Footer.jsx';

const SignUpPage = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: '',
    ktpUpload: null,
    selfieUpload: null,
    privacyConsent: false,
    dataConsent: false,
  });
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value, type, checked, files } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: type === 'checkbox' ? checked : (type === 'file' ? files[0] : value)
    }));
  };

  const handleNext = () => {
    // Basic validation before moving to next step
    if (step === 1) {
      if (!formData.fullName || !formData.email || !formData.phone || !formData.password || !formData.confirmPassword) {
        alert('Please fill in all contact information fields.');
        return;
      }
      if (formData.password !== formData.confirmPassword) {
        alert('Passwords do not match.');
        return;
      }
    }
    setStep(prevStep => prevStep + 1);
  };

  const handlePrev = () => {
    setStep(prevStep => prevStep - 1);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.privacyConsent || !formData.dataConsent) {
      alert('You must agree to the Privacy Policy and Terms & Conditions, and data usage for KYC verification.');
      return;
    }

    // In a real application, you would send formData to your backend
    // For now, we'll simulate a successful registration
    try {
      const response = await fetch(`${API_BASE_URL}/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          fullName: formData.fullName,
          email: formData.email,
          phone: formData.phone,
          password: formData.password,
          // KTP and Selfie would typically be uploaded separately or as base64 strings
          // For this example, we'll omit them from the JSON body
        }),
      });

      if (response.ok) {
        const result = await response.text(); // Assuming text response for success
        alert(`Registration successful: ${result}`);
        navigate('/login'); // Redirect to login page after successful registration
      } else {
        const errorData = await response.text();
        alert(`Registration failed: ${errorData}`);
      }
    } catch (error) {
      console.error('Error during registration:', error);
      alert('An error occurred during registration.');
    }
  };

  return (
    <>
      <Header />
      <div className="signup-container">
        <div className="signup-box">
          <h2>Daftar Akun Baru</h2>
          <div className="stepper">
            <div className={`step ${step === 1 ? 'active' : ''}`} id="step1">1. Kontak</div>
            <div className={`step ${step === 2 ? 'active' : ''}`} id="step2">2. KTP</div>
            <div className={`step ${step === 3 ? 'active' : ''}`} id="step3">3. Selfie</div>
            <div className={`step ${step === 4 ? 'active' : ''}`} id="step4">4. Persetujuan</div>
          </div>
          <form id="signupForm" onSubmit={handleSubmit}>
            {/* Step 1: Contact Info */}
            {step === 1 && (
              <div className="form-step active" id="formStep1">
                <div className="input-group">
                  <label htmlFor="fullName">Nama Lengkap</label>
                  <input type="text" id="fullName" name="fullName" autoComplete="name" value={formData.fullName} onChange={handleInputChange} required />
                </div>
                <div className="input-group">
                  <label htmlFor="email">Email</label>
                  <input type="email" id="email" name="email" autoComplete="email" value={formData.email} onChange={handleInputChange} required />
                </div>
                <div className="input-group">
                  <label htmlFor="phone">Nomor HP</label>
                  <input type="tel" id="phone" name="phone" autoComplete="tel" value={formData.phone} onChange={handleInputChange} required />
                </div>
                <div className="input-group">
                  <label htmlFor="password">Kata Sandi</label>
                  <input type="password" id="password" name="password" autoComplete="new-password" value={formData.password} onChange={handleInputChange} required />
                </div>
                <div className="input-group">
                  <label htmlFor="confirmPassword">Konfirmasi Kata Sandi</label>
                  <input type="password" id="confirmPassword" name="confirmPassword" autoComplete="new-password" value={formData.confirmPassword} onChange={handleInputChange} required />
                </div>
                <button type="button" className="btn-next" onClick={handleNext}>Lanjut</button>
              </div>
            )}

            {/* Step 2: Upload KTP */}
            {step === 2 && (
              <div className="form-step active" id="formStep2">
                <div className="input-group">
                  <label htmlFor="ktpUpload">Unggah KTP Anda (foto jelas)</label>
                  <input type="file" id="ktpUpload" name="ktpUpload" accept="image/*" onChange={handleInputChange} required />
                  <p className="hint">Ukuran file maksimal 10MB. Pastikan semua sudut KTP terlihat jelas.</p>
                  <div className="image-preview" id="ktpPreview"></div>
                </div>
                <button type="button" className="btn-prev" onClick={handlePrev}>Kembali</button>
                <button type="button" className="btn-next" onClick={handleNext}>Lanjut</button>
              </div>
            )}

            {/* Step 3: Selfie + Liveness */}
            {step === 3 && (
              <div className="form-step active" id="formStep3">
                <div className="input-group">
                  <label htmlFor="selfieUpload">Ambil Foto Selfie Anda</label>
                  <input type="file" id="selfieUpload" name="selfieUpload" accept="image/*" onChange={handleInputChange} required />
                  <p className="hint">Pastikan wajah terlihat jelas dan tidak ada halangan.</p>
                  <div className="image-preview" id="selfiePreview"></div>
                  {/* Placeholder for liveness detection overlay */}
                  <div className="liveness-overlay"></div>
                </div>
                <button type="button" className="btn-prev" onClick={handlePrev}>Kembali</button>
                <button type="button" className="btn-next" onClick={handleNext}>Lanjut</button>
              </div>
            )}

            {/* Step 4: Consent */}
            {step === 4 && (
              <div className="form-step active" id="formStep4">
                <div className="input-group checkbox-group">
                  <input type="checkbox" id="privacyConsent" name="privacyConsent" checked={formData.privacyConsent} onChange={handleInputChange} required />
                  <label htmlFor="privacyConsent">Saya menyetujui <Link to="/privacy">Kebijakan Privasi</Link> dan <Link to="/terms">Syarat & Ketentuan</Link>.</label>
                </div>
                <div className="input-group checkbox-group">
                  <input type="checkbox" id="dataConsent" name="dataConsent" checked={formData.dataConsent} onChange={handleInputChange} required />
                  <label htmlFor="dataConsent">Saya menyetujui penggunaan data pribadi untuk verifikasi KYC.</label>
                </div>
                <button type="button" className="btn-prev" onClick={handlePrev}>Kembali</button>
                <button type="submit" className="btn-submit">Daftar</button>
              </div>
            )}
          </form>
          <p className="login-link">Sudah punya akun? <Link to="/login">Masuk</Link></p>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default SignUpPage;
