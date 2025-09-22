document.addEventListener('DOMContentLoaded', () => {
    // Re-use accessibility toggle from Landing Page if needed, or define specific for Officials page
    const accessibilityButton = document.querySelector('.btn-accessibility');
    if (accessibilityButton) {
        accessibilityButton.addEventListener('click', () => {
            document.body.classList.toggle('simple-mode');
            // localStorage.setItem('simpleMode', document.body.classList.contains('simple-mode'));
        });
    }

    // Function to load officials (simulated)
    function loadOfficials(filters = {}) {
        console.log('Loading officials with filters:', filters);
        const officialsListGrid = document.querySelector('.officials-list-grid');
        // In a real application, this would fetch data from an API
        // Example: fetch('/api/officials?position=' + filters.position + '&region=' + filters.region + '&q=' + filters.query)
        // For now, we'll just simulate
        const simulatedOfficials = [
            {
                id: 1,
                name: "Budi Santoso",
                title: "Walikota Bandung",
                projectsCompleted: 12,
                complaintsHandled: 85,
                imageUrl: "../img/mederkkaa.jpg",
                slug: "budi-santoso"
            },
            {
                id: 2,
                name: "Siti Aminah",
                title: "Gubernur Jawa Barat",
                projectsCompleted: 25,
                complaintsHandled: 150,
                imageUrl: "../img/mederkkaa.jpg",
                slug: "siti-aminah"
            },
            {
                id: 3,
                name: "Joko Susilo",
                title: "Bupati Sleman",
                projectsCompleted: 8,
                complaintsHandled: 60,
                imageUrl: "../img/mederkkaa.jpg",
                slug: "joko-susilo"
            },
            {
                id: 4,
                name: "Dewi Lestari",
                title: "Kepala Dinas Pendidikan",
                projectsCompleted: 5,
                complaintsHandled: 30,
                imageUrl: "../img/mederkkaa.jpg",
                slug: "dewi-lestari"
            }
        ];

        officialsListGrid.innerHTML = ''; // Clear existing content
        simulatedOfficials.forEach(official => {
            const officialCardHtml = `
                <div class="official-card">
                    <img src="${official.imageUrl}" alt="Foto ${official.name}">
                    <div class="card-content">
                        <h3>${official.name}</h3>
                        <p class="official-title">${official.title}</p>
                        <p class="official-stats">Proyek Selesai: ${official.projectsCompleted} | Laporan Ditangani: ${official.complaintsHandled}</p>
                        <a href="/officials/${official.slug}" class="view-profile">Lihat Profil</a>
                    </div>
                </div>
            `;
            officialsListGrid.insertAdjacentHTML('beforeend', officialCardHtml);
        });
    }

    // Initial load of officials
    loadOfficials();

    // Handle filter and search
    const filterSearchInput = document.querySelector('.officials-filters .filter-search-input');
    const filterPositionSelect = document.querySelector('.officials-filters select:nth-of-type(1)');
    const filterRegionSelect = document.querySelector('.officials-filters select:nth-of-type(2)');
    const btnFilter = document.querySelector('.officials-filters .btn-filter');

    btnFilter.addEventListener('click', () => {
        const filters = {
            query: filterSearchInput.value.trim(),
            position: filterPositionSelect.value,
            region: filterRegionSelect.value
        };
        loadOfficials(filters);
    });

    // Populate region filter (simulated)
    function populateRegionFilter() {
        const regions = ["Jakarta", "Bandung", "Sleman", "Surabaya", "Medan"];
        const regionSelect = document.querySelector('.officials-filters select:nth-of-type(2)');
        regions.forEach(region => {
            const option = document.createElement('option');
            option.value = region.toLowerCase().replace(/\s/g, '-');
            option.textContent = region;
            regionSelect.appendChild(option);
        });
    }
    populateRegionFilter();

    // Pagination (simulated)
    const btnPrevPage = document.querySelector('.pagination .btn-page.prev');
    const btnNextPage = document.querySelector('.pagination .btn-page.next');
    const pageInfo = document.querySelector('.pagination .page-info');
    let currentPage = 1;
    const totalPages = 3; // Simulated total pages

    function updatePagination() {
        pageInfo.textContent = `Halaman ${currentPage} dari ${totalPages}`;
        btnPrevPage.disabled = currentPage === 1;
        btnNextPage.disabled = currentPage === totalPages;
    }

    btnPrevPage.addEventListener('click', () => {
        if (currentPage > 1) {
            currentPage--;
            updatePagination();
            loadOfficials(); // Reload officials for the new page (in real app, pass page number)
        }
    });

    btnNextPage.addEventListener('click', () => {
        if (currentPage < totalPages) {
            currentPage++;
            updatePagination();
            loadOfficials(); // Reload officials for the new page (in real app, pass page number)
        }
    });

    updatePagination(); // Initial pagination state
});
