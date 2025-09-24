document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('accessToken');
    const loginButton = document.querySelector('.btn-login');
    const registerButton = document.querySelector('.btn-register');
    const headerActions = document.querySelector('.header-actions');

    if (token) {
        if (loginButton) loginButton.style.display = 'none';
        if (registerButton) registerButton.style.display = 'none';

        const logoutButton = document.createElement('button');
        logoutButton.textContent = 'Logout';
        logoutButton.className = 'btn-logout';
        logoutButton.addEventListener('click', () => {
            localStorage.removeItem('accessToken');
            window.location.href = '/';
        });

        if (headerActions) {
            headerActions.appendChild(logoutButton);
        }
    }
});
