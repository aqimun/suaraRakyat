import { API_CONFIG } from './config.js';

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
        
        const queryParams = new URLSearchParams(filters).toString();
        fetch(`${API_CONFIG.BASE_URL}/officials?${queryParams}`)
            .then(response => response.json())
            .then(data => {
                officialsListGrid.innerHTML = ''; // Clear existing content
                data.content.forEach(official => { // Assuming API returns { content: [], totalPages: X }
                    const officialCardHtml = `
                        <div class="official-card">
                            <img src="${official.imageUrl || '../img/mederkkaa.jpg'}" alt="Foto ${official.name}">
                            <div class="card-content">
                                <h3>${official.name}</h3>
                                <p class="official-title">${official.title}</p>
                                <p class="official-stats">Proyek Selesai: ${official.projectsCompleted || 0} | Laporan Ditangani: ${official.complaintsHandled || 0}</p>
                                <a href="../pages/official-profile.html?id=${official.id}" class="view-profile">Lihat Profil</a>
                            </div>
                        </div>
                    `;
                    officialsListGrid.insertAdjacentHTML('beforeend', officialCardHtml);
                });
                currentPage = data.pageNumber + 1;
                totalPages = data.totalPages;
                updatePaginationDisplay();
            })
            .catch(error => {
                console.error('Error loading officials:', error);
                officialsListGrid.innerHTML = '<p>Gagal memuat data pejabat. Silakan coba lagi nanti.</p>';
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

    // Populate region filter
    function populateRegionFilter() {
        fetch(`${API_CONFIG.BASE_URL}/regions/provinces`)
            .then(response => response.json())
            .then(regions => {
                const regionSelect = document.querySelector('.officials-filters select:nth-of-type(2)');
                regionSelect.innerHTML = '<option value="">Semua Wilayah</option>'; // Clear existing and add default
                regions.forEach(region => {
                    const option = document.createElement('option');
                    option.value = region.id;
                    option.textContent = region.name;
                    regionSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error loading regions:', error));
    }
    populateRegionFilter();

    // Pagination
    const btnPrevPage = document.querySelector('.pagination .btn-page.prev');
    const btnNextPage = document.querySelector('.pagination .btn-page.next');
    const pageInfo = document.querySelector('.pagination .page-info');
    let currentPage = 1;
    let totalPages = 1; // Will be updated by API response

    function updatePaginationDisplay() {
        pageInfo.textContent = `Halaman ${currentPage} dari ${totalPages}`;
        btnPrevPage.disabled = currentPage === 1;
        btnNextPage.disabled = currentPage === totalPages;
    }

    btnPrevPage.addEventListener('click', () => {
        if (currentPage > 1) {
            loadOfficials({ page: currentPage - 1, size: 10 }); // Assuming page is 0-indexed in backend
        }
    });

    btnNextPage.addEventListener('click', () => {
        if (currentPage < totalPages) {
            loadOfficials({ page: currentPage + 1, size: 10 });
        }
    });

    // Initial load with pagination
    loadOfficials({ page: 1, size: 10 });
});
