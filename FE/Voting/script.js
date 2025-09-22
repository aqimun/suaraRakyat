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
        // In a real application, this would fetch data from an API
        // Example: fetch('/api/elections')
        // For now, we'll just simulate
        const simulatedEvents = [
            {
                id: 1,
                title: "Pemilihan Walikota Bandung 2026",
                status: "active",
                dateRange: "10 Okt - 20 Okt 2026",
                description: "Pilih pemimpin terbaik untuk Kota Bandung. Pelajari visi misi kandidat sebelum memilih.",
                candidates: ["Budi Santoso", "Dewi Lestari"],
                slug: "walikota-bandung-2026",
                actionText: "Lihat Detail & Voting",
                actionLink: "/elections/walikota-bandung-2026",
                actionClass: "btn-vote"
            },
            {
                id: 2,
                title: "Pemilihan Gubernur Jawa Barat 2027",
                status: "upcoming",
                dateRange: "01 Jan - 10 Jan 2027",
                description: "Persiapkan diri Anda untuk memilih Gubernur Jawa Barat. Informasi kandidat akan segera tersedia.",
                candidates: [],
                slug: "gubernur-jabar-2027",
                actionText: "Lihat Detail",
                actionLink: "/elections/gubernur-jabar-2027",
                actionClass: "btn-detail"
            },
            {
                id: 3,
                title: "Pemilihan Presiden 2029",
                status: "finished",
                dateRange: "14 Feb - 24 Feb 2029",
                description: "Hasil pemilihan presiden sebelumnya. Lihat audit dan transparansi hasil.",
                candidates: ["Joko Widodo", "Prabowo Subianto"],
                slug: "presiden-2029",
                actionText: "Lihat Hasil",
                actionLink: "/elections/presiden-2029",
                actionClass: "btn-results"
            }
        ];

        electionEventsGrid.innerHTML = ''; // Clear existing content
        simulatedEvents.forEach(event => {
            const candidateListHtml = event.candidates.length > 0 ?
                `<h4>Kandidat:</h4><ul>${event.candidates.map(c => `<li>${c}</li>`).join('')}</ul>` :
                `<p>Kandidat akan segera diumumkan.</p>`;

            const eventCardHtml = `
                <div class="election-card">
                    <div class="card-header">
                        <h3>${event.title}</h3>
                        <span class="status-badge ${event.status}">${event.status === 'active' ? 'Aktif' : (event.status === 'upcoming' ? 'Mendatang' : 'Selesai')}</span>
                    </div>
                    <p class="election-date">Periode Voting: ${event.dateRange}</p>
                    <p class="election-description">${event.description}</p>
                    <div class="candidate-list">
                        ${candidateListHtml}
                    </div>
                    <a href="${event.actionLink}" class="${event.actionClass}">${event.actionText}</a>
                </div>
            `;
            electionEventsGrid.insertAdjacentHTML('beforeend', eventCardHtml);
        });
    }

    // Initial load of election events
    loadElectionEvents();

    // Handle "Ajukan Nominasi" button
    const nominateButton = document.querySelector('.btn-nominate');
    if (nominateButton) {
        nominateButton.addEventListener('click', () => {
            alert('Anda akan diarahkan ke halaman pengajuan nominasi (simulasi).');
            // In a real app, redirect to the official's dashboard nomination form
            // window.location.href = '/dashboard/official/nominate';
        });
    }
});
