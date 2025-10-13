import React, { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import ErrorBoundary from './components/ErrorBoundary'; // Import ErrorBoundary
import './App.css';

// Lazy load page components
const LandingPage = lazy(() => import('./pages/LandingPage'));
const LoginPage = lazy(() => import('./pages/LoginPage'));
const SignUpPage = lazy(() => import('./pages/SignUpPage'));
const ForgotPasswordPage = lazy(() => import('./pages/ForgotPasswordPage'));
const NewsPage = lazy(() => import('./pages/NewsPage'));
const OfficialsPage = lazy(() => import('./pages/OfficialsPage'));
const OfficialProfilePage = lazy(() => import('./pages/OfficialProfilePage'));
const VotingPage = lazy(() => import('./pages/VotingPage'));
const ComplaintPage = lazy(() => import('./pages/ComplaintPage'));
const ComplaintTrackingPage = lazy(() => import('./pages/ComplaintTrackingPage'));
const PrivacyPolicyPage = lazy(() => import('./pages/PrivacyPolicyPage'));
const TermsAndConditionsPage = lazy(() => import('./pages/TermsAndConditionsPage'));
const ProjectPage = lazy(() => import('./pages/ProjectPage'));

function App() {
  return (
    <Router>
      <ErrorBoundary> {/* Wrap Layout with ErrorBoundary */}
        <Layout>
          <Suspense fallback={<div>Loading...</div>}> {/* Add Suspense for lazy loaded components */}
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
              <Route path="/projects" element={<ProjectPage />} />
            </Routes>
          </Suspense>
        </Layout>
      </ErrorBoundary>
    </Router>
  );
}

export default App;
