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
    function loadOfficialData(officialSlug) {
        console.log('Loading data for official:', officialSlug);
        // In a real application, this would fetch data from an API
        // Example: fetch('/api/officials/' + officialSlug)
        // For now, we'll just simulate
        const simulatedOfficialData = {
            name: "Budi Santoso",
            title: "Walikota Bandung",
            email: "budi.santoso@bandung.go.id",
            phone: "(022) 123456",
            imageUrl: "../img/mederkkaa.jpg",
            visi: "Mewujudkan Bandung sebagai kota yang maju, sejahtera, dan berbudaya dengan partisipasi aktif masyarakat.",
            misi: [
                "Meningkatkan kualitas pelayanan publik yang transparan dan akuntabel.",
                "Mengembangkan ekonomi kreatif dan UMKM untuk kesejahteraan masyarakat.",
                "Membangun infrastruktur yang berkelanjutan dan ramah lingkungan.",
                "Mendorong inovasi dan teknologi dalam tata kelola pemerintahan."
            ],
            projects: [
                {
                    id: 1,
                    title: "Revitalisasi Taman Kota",
                    status: "Selesai",
                    budget: "Rp 5 Miliar",
                    beforeImage: "../img/mederkkaa.jpg",
                    afterImage: "../img/mederkkaa.jpg",
                    detailLink: "#"
                },
                {
                    id: 2,
                    title: "Pembangunan Jembatan Layang",
                    status: "Berjalan",
                    budget: "Rp 15 Miliar",
                    beforeImage: "../img/mederkkaa.jpg",
                    afterImage: "../img/mederkkaa.jpg", // Could be a progress image
                    detailLink: "#"
                }
            ],
            complaints: [
                { date: "2025-09-20", description: "Laporan 'Jalan Rusak di Jl. Merdeka' ditangani.", status: "resolved" },
                { date: "2025-09-18", description: "Laporan 'Sampah Menumpuk di Pasar' sedang diproses.", status: "in-progress" }
            ],
            documents: [
                { name: "APBD Kota Bandung 2025", link: "#" },
                { name: "Laporan Akuntabilitas Kinerja Instansi Pemerintah (LAKIP) 2024", link: "#" }
            ]
        };

        // Update profile header
        document.querySelector('.profile-photo').src = simulatedOfficialData.imageUrl;
        document.querySelector('.profile-info h1').textContent = simulatedOfficialData.name;
        document.querySelector('.profile-info .official-title').textContent = simulatedOfficialData.title;
        document.querySelector('.profile-info .public-contact').textContent = `Email: ${simulatedOfficialData.email} | Telepon: ${simulatedOfficialData.phone}`;

        // Update Visi & Misi
        document.querySelector('#visi-misi h2:nth-of-type(1)').textContent = 'Visi';
        document.querySelector('#visi-misi p:nth-of-type(1)').textContent = simulatedOfficialData.visi;
        document.querySelector('#visi-misi h2:nth-of-type(2)').textContent = 'Misi';
        const misiList = document.querySelector('#visi-misi ul');
        misiList.innerHTML = '';
        simulatedOfficialData.misi.forEach(item => {
            const li = document.createElement('li');
            li.textContent = item;
            misiList.appendChild(li);
        });

        // Update Projects
        const projectsGrid = document.querySelector('#projects .projects-grid');
        projectsGrid.innerHTML = '';
        simulatedOfficialData.projects.forEach(project => {
            const projectCardHtml = `
                <div class="project-card">
                    <img src="${project.beforeImage}" alt="Proyek ${project.title}">
                    <h3>${project.title}</h3>
                    <p>Status: ${project.status}</p>
                    <p>Anggaran: ${project.budget}</p>
                    <div class="project-evidence">
                        <img src="${project.beforeImage}" alt="Sebelum Proyek" class="before-after-img">
                        <img src="${project.afterImage}" alt="Sesudah Proyek" class="before-after-img">
                    </div>
                    <a href="${project.detailLink}" class="btn-detail">Lihat Detail</a>
                </div>
            `;
            projectsGrid.insertAdjacentHTML('beforeend', projectCardHtml);
        });

        // Update Complaints
        const complaintTimeline = document.querySelector('#complaints .complaint-timeline');
        const timelineItemsContainer = complaintTimeline.querySelector('.timeline-items-container') || document.createElement('div');
        timelineItemsContainer.className = 'timeline-items-container';
        timelineItemsContainer.innerHTML = '';
        simulatedOfficialData.complaints.forEach(complaint => {
            const timelineItemHtml = `
                <div class="timeline-item">
                    <p class="timeline-date">${complaint.date}</p>
                    <p class="timeline-description">${complaint.description}</p>
                    <span class="status-badge ${complaint.status}">${complaint.status === 'resolved' ? 'Selesai' : 'Dalam Proses'}</span>
                </div>
            `;
            timelineItemsContainer.insertAdjacentHTML('beforeend', timelineItemHtml);
        });
        if (!complaintTimeline.querySelector('.timeline-items-container')) {
            complaintTimeline.appendChild(timelineItemsContainer);
        }


        // Update Documents
        const documentsList = document.querySelector('#documents ul');
        documentsList.innerHTML = '';
        simulatedOfficialData.documents.forEach(doc => {
            const li = document.createElement('li');
            li.innerHTML = `<a href="${doc.link}">${doc.name}</a>`;
            documentsList.appendChild(li);
        });
    }

    // Get official slug from URL (example: /officials/budi-santoso -> budi-santoso)
    const pathSegments = window.location.pathname.split('/');
    const officialSlug = pathSegments[pathSegments.length - 1];
    if (officialSlug && officialSlug !== 'officials') { // Ensure it's a profile page, not the directory
        loadOfficialData(officialSlug);
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
