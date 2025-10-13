import { API_CONFIG } from './config.js';

document.addEventListener('DOMContentLoaded', () => {
    // Re-use accessibility toggle from Landing Page if needed, or define specific for News page
    const accessibilityButton = document.querySelector('.btn-accessibility');
    if (accessibilityButton) {
        accessibilityButton.addEventListener('click', () => {
            document.body.classList.toggle('simple-mode');
            // localStorage.setItem('simpleMode', document.body.classList.contains('simple-mode'));
        });
    }

    // Function to load news articles (simulated)
    function loadNewsArticles(filters = {}) {
        console.log('Loading news with filters:', filters);
        const newsListGrid = document.querySelector('.news-list-grid');
        
        const queryParams = new URLSearchParams(filters).toString();
        fetch(`${API_CONFIG.BASE_URL}/news?${queryParams}`)
            .then(response => response.json())
            .then(newsItems => {
                newsListGrid.innerHTML = ''; // Clear existing content
                newsItems.forEach(news => {
                    const newsCardHtml = `
                        <div class="news-card">
                            <img src="${news.imageUrl || '../img/mederkkaa.jpg'}" alt="${news.title}">
                            <div class="card-content">
                                <h3>${news.title}</h3>
                                <p class="news-meta">Kategori: ${news.category} | Tanggal: ${new Date(news.date).toLocaleDateString()}</p>
                                <p>${news.summary}</p>
                                <a href="/news/${news.slug || news.id}" class="read-more">Baca Selengkapnya</a>
                            </div>
                        </div>
                    `;
                    newsListGrid.insertAdjacentHTML('beforeend', newsCardHtml);
                });
            })
            .catch(error => {
                console.error('Error loading news:', error);
                newsListGrid.innerHTML = '<p>Gagal memuat berita. Silakan coba lagi nanti.</p>';
            });
    }

    // Initial load of news
    loadNewsArticles();

    // Handle filter and search
    const filterSearchInput = document.querySelector('.filter-search-input');
    const filterCategorySelect = document.querySelector('.news-filters select:nth-of-type(1)');
    const filterRegionSelect = document.querySelector('.news-filters select:nth-of-type(2)');
    const btnFilter = document.querySelector('.btn-filter');

    btnFilter.addEventListener('click', () => {
        const filters = {
            query: filterSearchInput.value.trim(),
            category: filterCategorySelect.value,
            region: filterRegionSelect.value
        };
        loadNewsArticles(filters);
    });

    // Populate region filter
    function populateRegionFilter() {
        fetch(`${API_CONFIG.BASE_URL}/regions/provinces`)
            .then(response => response.json())
            .then(regions => {
                const regionSelect = document.querySelector('.news-filters select:nth-of-type(2)');
                regionSelect.innerHTML = '<option value="">Semua Wilayah</option>'; // Clear existing and add default
                regions.forEach(region => {
                    const option = document.createElement('option');
                    option.value = region.id; // Assuming region object has an 'id'
                    option.textContent = region.name; // Assuming region object has a 'name'
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

    function loadNewsWithPagination(page) {
        const filters = {
            query: filterSearchInput.value.trim(),
            category: filterCategorySelect.value,
            region: filterRegionSelect.value,
            page: page,
            size: 10 // Example page size
        };
        fetch(`${API_CONFIG.BASE_URL}/news?${new URLSearchParams(filters).toString()}`)
            .then(response => response.json())
            .then(data => {
                const newsListGrid = document.querySelector('.news-list-grid');
                newsListGrid.innerHTML = '';
                data.content.forEach(news => { // Assuming API returns { content: [], totalPages: X }
                    const newsCardHtml = `
                        <div class="news-card">
                            <img src="${news.imageUrl || '../img/mederkkaa.jpg'}" alt="${news.title}">
                            <div class="card-content">
                                <h3>${news.title}</h3>
                                <p class="news-meta">Kategori: ${news.category} | Tanggal: ${new Date(news.date).toLocaleDateString()}</p>
                                <p>${news.summary}</p>
                                <a href="/news/${news.slug || news.id}" class="read-more">Baca Selengkapnya</a>
                            </div>
                        </div>
                    `;
                    newsListGrid.insertAdjacentHTML('beforeend', newsCardHtml);
                });
                currentPage = data.pageNumber + 1; // Assuming 0-indexed pageNumber from backend
                totalPages = data.totalPages;
                updatePaginationDisplay();
            })
            .catch(error => {
                console.error('Error loading paginated news:', error);
                newsListGrid.innerHTML = '<p>Gagal memuat berita. Silakan coba lagi nanti.</p>';
            });
    }

    btnPrevPage.addEventListener('click', () => {
        if (currentPage > 1) {
            loadNewsWithPagination(currentPage - 1);
        }
    });

    btnNextPage.addEventListener('click', () => {
        if (currentPage < totalPages) {
            loadNewsWithPagination(currentPage + 1);
        }
    });

    // Initial load with pagination
    loadNewsWithPagination(1);
});
