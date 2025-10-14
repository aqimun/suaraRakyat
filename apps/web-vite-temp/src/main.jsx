import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import './styles/complaint-tracking.css';
import './styles/complaint.css';
import './styles/forgot-password.css';
import './styles/landing-page.css';
import './styles/login.css';
import './styles/news.css';
import './styles/official-profile.css';
import './styles/officials.css';
import './styles/sign-up.css';
import './styles/voting.css';
import './styles/style.css';
import App from './App.js'; // Changed to .js as the original App.js is copied
import { AuthProvider } from './context/AuthContext.js';
import { ProjectProvider } from './context/ProjectContext.js';
import { HeadProvider } from 'react-head';
import reportWebVitals from './reportWebVitals.js';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <HeadProvider>
      <AuthProvider>
        <ProjectProvider>
          <App />
        </ProjectProvider>
      </AuthProvider>
    </HeadProvider>
  </StrictMode>,
);

reportWebVitals();
