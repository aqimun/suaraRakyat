document.addEventListener('DOMContentLoaded', () => {
    const forgotPasswordForm = document.getElementById('forgotPasswordForm');

    if (forgotPasswordForm) {
        forgotPasswordForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const emailPhone = document.getElementById('emailPhone').value;

            console.log('Forgot Password Request for:', { emailPhone });

            // In a real application, you would send this data to your backend API
            // fetch('/auth/forgot', {
            //     method: 'POST',
            //     headers: {
            //         'Content-Type': 'application/json',
            //     },
            //     body: JSON.stringify({ emailPhone }),
            // })
            // .then(response => response.json())
            // .then(data => {
            //     if (data.success) {
            //         alert('Kode reset telah dikirim ke ' + emailPhone + '. Silakan cek email/HP Anda.');
            //         // Optionally redirect to a page to enter the OTP and new password
            //         // window.location.href = '/reset-password?token=' + data.token;
            //     } else {
            //         alert('Gagal mengirim kode reset: ' + data.message);
            //     }
            // })
            // .catch(error => {
            //     console.error('Error during forgot password request:', error);
            //     alert('Terjadi kesalahan. Silakan coba lagi.');
            // });

            // For demonstration purposes:
            if (emailPhone) {
                alert('Kode reset telah dikirim ke ' + emailPhone + ' (simulasi). Silakan cek email/HP Anda.');
                // In a real app, this would lead to a new page for OTP and new password
                // For now, we'll just redirect to login
                window.location.href = '/login';
            } else {
                alert('Harap masukkan email atau nomor HP Anda.');
            }
        });
    }
});
