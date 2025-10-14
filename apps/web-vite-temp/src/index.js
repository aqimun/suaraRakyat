import React from 'react';
import ReactDOM from 'react-dom/client';
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
import './styles/style.css'; /* Import global styles */
import App from './App';
import { AuthProvider } from './context/AuthContext'; // Import AuthProvider
import { ProjectProvider } from './context/ProjectContext'; // Import ProjectProvider
import { HeadProvider } from 'react-head'; // Import HeadProvider
import reportWebVitals from './reportWebVitals';

if (process.env.NODE_ENV !== 'production') {
  const axe = require('react-axe');
  axe(React, ReactDOM, 1000);
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <HeadProvider>
      <AuthProvider> {/* Wrap App with AuthProvider */}
        <ProjectProvider> {/* Wrap App with ProjectProvider */}
          <App />
        </ProjectProvider>
      </AuthProvider>
    </HeadProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
