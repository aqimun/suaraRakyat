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

    // Tab switching functionality
    const tabButtons = document.querySelectorAll('.profile-tabs .tab-button');
    const tabContents = document.querySelectorAll('.tab-content');

    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Remove active class from all buttons and contents
            tabButtons.forEach(btn => btn.classList.remove('active'));
            tabContents.forEach(content => content.classList.remove('active'));

            // Add active class to the clicked button
            button.classList.add('active');

            // Show the corresponding tab content
            const targetTabId = button.dataset.tab;
            const targetTabContent = document.getElementById(targetTabId);
            if (targetTabContent) {
                targetTabContent.classList.add('active');
            }
        });
    });

    // Function to load official data (simulated)
    function loadOfficialData(officialId) { // Changed to officialId for API consistency
        console.log('Loading data for official ID:', officialId);
        fetch(`${API_CONFIG.BASE_URL}/officials/${officialId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .then(data => {
                // Update profile header
                document.querySelector('.profile-photo').src = data.imageUrl || '../img/mederkkaa.jpg';
                document.querySelector('.profile-info h1').textContent = data.name;
                document.querySelector('.profile-info .official-title').textContent = data.title;
                document.querySelector('.profile-info .public-contact').textContent = `Email: ${data.email} | Telepon: ${data.phone}`;

                // Update Visi & Misi
                document.querySelector('#visi-misi h2:nth-of-type(1)').textContent = 'Visi';
                document.querySelector('#visi-misi p:nth-of-type(1)').textContent = data.visi;
                document.querySelector('#visi-misi h2:nth-of-type(2)').textContent = 'Misi';
                const misiList = document.querySelector('#visi-misi ul');
                misiList.innerHTML = '';
                data.misi.forEach(item => {
                    const li = document.createElement('li');
                    li.textContent = item;
                    misiList.appendChild(li);
                });

                // Update Projects
                const projectsGrid = document.querySelector('#projects .projects-grid');
                projectsGrid.innerHTML = '';
                data.projects.forEach(project => {
                    const projectCardHtml = `
                        <div class="project-card">
                            <img src="${project.imageUrl || '../img/mederkkaa.jpg'}" alt="Proyek ${project.title}">
                            <h3>${project.title}</h3>
                            <p>Status: ${project.status}</p>
                            <p>Anggaran: ${project.budget}</p>
                            <div class="project-evidence">
                                <img src="${project.beforeImage || '../img/mederkkaa.jpg'}" alt="Sebelum Proyek" class="before-after-img">
                                <img src="${project.afterImage || '../img/mederkkaa.jpg'}" alt="Sesudah Proyek" class="before-after-img">
                            </div>
                            <a href="${project.detailLink || '#'}" class="btn-detail">Lihat Detail</a>
                        </div>
                    `;
                    projectsGrid.insertAdjacentHTML('beforeend', projectCardHtml);
                });

                // Update Complaints
                const complaintTimeline = document.querySelector('#complaints .complaint-timeline');
                let timelineItemsContainer = complaintTimeline.querySelector('.timeline-items-container');
                if (!timelineItemsContainer) {
                    timelineItemsContainer = document.createElement('div');
                    timelineItemsContainer.className = 'timeline-items-container';
                    complaintTimeline.appendChild(timelineItemsContainer);
                }
                timelineItemsContainer.innerHTML = '';
                data.complaints.forEach(complaint => {
                    const timelineItemHtml = `
                        <div class="timeline-item">
                            <p class="timeline-date">${new Date(complaint.date).toLocaleDateString()}</p>
                            <p class="timeline-description">${complaint.description}</p>
                            <span class="status-badge ${complaint.status.toLowerCase()}">${complaint.status === 'RESOLVED' ? 'Selesai' : 'Dalam Proses'}</span>
                        </div>
                    `;
                    timelineItemsContainer.insertAdjacentHTML('beforeend', timelineItemHtml);
                });

                // Update Documents
                const documentsList = document.querySelector('#documents ul');
                documentsList.innerHTML = '';
                data.documents.forEach(doc => {
                    const li = document.createElement('li');
                    li.innerHTML = `<a href="${doc.link || '#'}">${doc.name}</a>`;
                    documentsList.appendChild(li);
                });
            })
            .catch(error => {
                console.error('Error loading official data:', error);
                alert('Gagal memuat data pejabat: ' + error.message);
            });
    }

    // Get official ID from URL (example: /officials/123 -> 123)
    const pathSegments = window.location.pathname.split('/');
    const officialId = pathSegments[pathSegments.length - 1];
    if (officialId && officialId !== 'officials') { // Ensure it's a profile page, not the directory
        loadOfficialData(officialId);
    }

    // Handle "Minta Klarifikasi" button
    const clarificationButton = document.querySelector('.btn-clarification');
    if (clarificationButton) {
        clarificationButton.addEventListener('click', () => {
            alert('Formulir permintaan klarifikasi akan muncul di sini (simulasi).');
            // In a real app, this would open a modal or redirect to a form
        });
    }
});
