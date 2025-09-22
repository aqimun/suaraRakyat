document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const emailPhone = document.getElementById('emailPhone').value;
            const password = document.getElementById('password').value;
            const rememberMe = document.getElementById('rememberMe').checked;

            console.log('Login Attempt:', { emailPhone, password, rememberMe });

            // In a real application, you would send this data to your backend API
            // fetch('/auth/login', {
            //     method: 'POST',
            //     headers: {
            //         'Content-Type': 'application/json',
            //     },
            //     body: JSON.stringify({ emailPhone, password, rememberMe }),
            // })
            // .then(response => response.json())
            // .then(data => {
            //     if (data.success) {
            //         alert('Login successful!');
            //         window.location.href = '/dashboard'; // Redirect to user dashboard
            //     } else {
            //         alert('Login failed: ' + data.message);
            //     }
            // })
            // .catch(error => {
            //     console.error('Error during login:', error);
            //     alert('An error occurred during login. Please try again.');
            // });

            // For demonstration purposes:
            if (emailPhone && password) {
                alert('Login successful (simulated)!');
                window.location.href = '/'; // Redirect to home or dashboard
            } else {
                alert('Please enter both email/phone and password.');
            }
        });
    }

    // Optional: Handle SSO button click
    const ssoButton = document.querySelector('.btn-social-login');
    if (ssoButton) {
        ssoButton.addEventListener('click', () => {
            alert('Redirecting to Government SSO (simulated)...');
            // In a real app, initiate SSO flow
            // window.location.href = '/auth/sso/gov';
        });
    }
});
