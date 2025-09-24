document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const emailPhone = document.getElementById('emailPhone').value;
            const password = document.getElementById('password').value;
            const rememberMe = document.getElementById('rememberMe').checked;

            console.log('Login Attempt:', { emailPhone, password, rememberMe });

            fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ emailPhone, password }),
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .then(data => {
                alert('Login successful!');
                localStorage.setItem('accessToken', data.accessToken);
                window.location.href = '../Landing Page/Index.Html'; // Redirect to landing page
            })
            .catch(error => {
                console.error('Error during login:', error);
                alert('Login failed: ' + error.message);
            });
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
