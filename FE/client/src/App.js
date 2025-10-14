import React, { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import ErrorBoundary from './components/ErrorBoundary'; // Import ErrorBoundary
import './App.css';

// Lazy load page components
const LandingPage = lazy(() => import('./pages/LandingPage'));
const LoginPage = lazy(() => import('./features/auth/LoginPage'));
const SignUpPage = lazy(() => import('./features/auth/SignUpPage'));
const ForgotPasswordPage = lazy(() => import('./features/auth/ForgotPasswordPage'));
const NewsPage = lazy(() => import('./features/news/NewsPage'));
const OfficialsPage = lazy(() => import('./features/officials/OfficialsPage'));
const OfficialProfilePage = lazy(() => import('./features/officials/OfficialProfilePage'));
const VotingPage = lazy(() => import('./features/voting/VotingPage'));
const ComplaintPage = lazy(() => import('./features/complaints/ComplaintPage'));
const ComplaintTrackingPage = lazy(() => import('./features/complaints/ComplaintTrackingPage'));
const PrivacyPolicyPage = lazy(() => import('./features/legal/PrivacyPolicyPage'));
const TermsAndConditionsPage = lazy(() => import('./features/legal/TermsAndConditionsPage'));
const ProjectPage = lazy(() => import('./features/projects/ProjectPage'));

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
