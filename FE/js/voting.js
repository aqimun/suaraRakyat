import { API_CONFIG } from './config.js';

document.addEventListener('DOMContentLoaded', () => {
    // Re-use accessibility toggle from Landing Page if needed
    const accessibilityButton = document.querySelector('.btn-accessibility');
    if (accessibilityButton) {
        accessibilityButton.addEventListener('click', () => {
            document.body.classList.toggle('simple-mode');
            // localStorage.setItem('simpleMode', document.body.classList.contains('simple-mode'));
        });
    }

    // Function to load election events (simulated)
    function loadElectionEvents() {
        console.log('Loading election events...');
        const electionEventsGrid = document.querySelector('.election-events-grid');
        
        fetch(`${API_CONFIG.BASE_URL}/elections`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .then(events => {
                electionEventsGrid.innerHTML = ''; // Clear existing content
                events.forEach(event => {
                    const candidateListHtml = event.candidates && event.candidates.length > 0 ?
                        `<h4>Kandidat:</h4><ul>${event.candidates.map(c => `<li>${c.name}</li>`).join('')}</ul>` :
                        `<p>Kandidat akan segera diumumkan.</p>`;

                    const statusText = event.status === 'ACTIVE' ? 'Aktif' : (event.status === 'UPCOMING' ? 'Mendatang' : 'Selesai');
                    const actionLink = `../pages/voting.html?id=${event.id}`; // Assuming voting.html can handle an ID
                    const actionText = event.status === 'ACTIVE' ? 'Lihat Detail & Voting' : (event.status === 'UPCOMING' ? 'Lihat Detail' : 'Lihat Hasil');
                    const actionClass = event.status === 'ACTIVE' ? 'btn-vote' : (event.status === 'UPCOMING' ? 'btn-detail' : 'btn-results');

                    const eventCardHtml = `
                        <div class="election-card">
                            <div class="card-header">
                                <h3>${event.title}</h3>
                                <span class="status-badge ${event.status.toLowerCase()}">${statusText}</span>
                            </div>
                            <p class="election-date">Periode Voting: ${new Date(event.startDate).toLocaleDateString()} - ${new Date(event.endDate).toLocaleDateString()}</p>
                            <p class="election-description">${event.description}</p>
                            <div class="candidate-list">
                                ${candidateListHtml}
                            </div>
                            <a href="${actionLink}" class="${actionClass}">${actionText}</a>
                        </div>
                    `;
                    electionEventsGrid.insertAdjacentHTML('beforeend', eventCardHtml);
                });
            })
            .catch(error => {
                console.error('Error loading election events:', error);
                electionEventsGrid.innerHTML = '<p>Gagal memuat acara pemilihan. Silakan coba lagi nanti.</p>';
            });
    }

    // Initial load of election events
    loadElectionEvents();

    // Handle "Ajukan Nominasi" button
    const nominateButton = document.querySelector('.btn-nominate');
    if (nominateButton) {
        nominateButton.addEventListener('click', () => {
            // In a real app, redirect to the official's dashboard nomination form
            window.location.href = '../pages/official-nomination.html'; // Assuming a new page for nomination
        });
    }
});
