import { API_CONFIG } from './config.js';

document.addEventListener('DOMContentLoaded', () => {
    const forgotPasswordForm = document.getElementById('forgotPasswordForm');

    if (forgotPasswordForm) {
        forgotPasswordForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const emailPhone = document.getElementById('emailPhone').value;

            console.log('Forgot Password Request for:', { emailPhone });

            fetch(`${API_CONFIG.BASE_URL}/auth/forgot-password`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ emailPhone }),
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .then(data => {
                if (data.success) {
                    alert('Kode reset telah dikirim ke ' + emailPhone + '. Silakan cek email/HP Anda.');
                    // Optionally redirect to a page to enter the OTP and new password
                    // window.location.href = '../pages/reset-password.html?token=' + data.token;
                    window.location.href = '../pages/login.html'; // For now, redirect to login
                } else {
                    alert('Gagal mengirim kode reset: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error during forgot password request:', error);
                alert('Terjadi kesalahan. Silakan coba lagi: ' + error.message);
            });
        });
    }
});
