import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import ForgotPasswordPage from './pages/ForgotPasswordPage';
import NewsPage from './pages/NewsPage';
import OfficialsPage from './pages/OfficialsPage';
import OfficialProfilePage from './pages/OfficialProfilePage';
import VotingPage from './pages/VotingPage';
import ComplaintPage from './pages/ComplaintPage';
import ComplaintTrackingPage from './pages/ComplaintTrackingPage';
import PrivacyPolicyPage from './pages/PrivacyPolicyPage';
import TermsAndConditionsPage from './pages/TermsAndConditionsPage';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/forgot-password" element={<ForgotPasswordPage />} />
        <Route path="/news" element={<NewsPage />} />
        <Route path="/officials" element={<OfficialsPage />} />
        <Route path="/officials/:id" element={<OfficialProfilePage />} />
        <Route path="/elections" element={<VotingPage />} />
        <Route path="/report" element={<ComplaintPage />} />
        <Route path="/report/track" element={<ComplaintTrackingPage />} />
        <Route path="/privacy" element={<PrivacyPolicyPage />} />
        <Route path="/terms" element={<TermsAndConditionsPage />} />
      </Routes>
    </Router>
  );
}

export default App;
