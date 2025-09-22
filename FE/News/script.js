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
        // In a real application, this would fetch data from an API
        // Example: fetch('/api/news?category=' + filters.category + '&region=' + filters.region + '&q=' + filters.query)
        // For now, we'll just simulate
        const simulatedNews = [
            {
                id: 1,
                title: "Pemerintah Luncurkan Program Bantuan Sosial Baru",
                category: "Pemerintahan",
                date: "2025-09-22",
                summary: "Pemerintah meluncurkan program bantuan sosial baru untuk membantu masyarakat yang terdampak ekonomi.",
                imageUrl: "../img/mederkkaa.jpg",
                slug: "pemerintah-luncurkan-program-bantuan-sosial-baru"
            },
            {
                id: 2,
                title: "Persiapan Pemilu Serentak 2026 Dimulai",
                category: "Pemilu",
                date: "2025-09-21",
                summary: "KPU mengumumkan dimulainya persiapan untuk Pemilu serentak tahun 2026 dengan berbagai tahapan awal.",
                imageUrl: "../img/mederkkaa.jpg",
                slug: "persiapan-pemilu-serentak-2026-dimulai"
            },
            {
                id: 3,
                title: "Inovasi Teknologi untuk Pelayanan Publik Lebih Baik",
                category: "Umum",
                date: "2025-09-20",
                summary: "Berbagai inovasi teknologi diterapkan untuk meningkatkan efisiensi dan transparansi pelayanan publik.",
                imageUrl: "../img/mederkkaa.jpg",
                slug: "inovasi-teknologi-pelayanan-publik"
            },
            {
                id: 4,
                title: "Peningkatan Infrastruktur Jalan di Wilayah Pedesaan",
                category: "Pemerintahan",
                date: "2025-09-19",
                summary: "Proyek peningkatan infrastruktur jalan di beberapa wilayah pedesaan telah selesai dan siap digunakan.",
                imageUrl: "../img/mederkkaa.jpg",
                slug: "peningkatan-infrastruktur-jalan-pedesaan"
            }
        ];

        newsListGrid.innerHTML = ''; // Clear existing content
        simulatedNews.forEach(news => {
            const newsCardHtml = `
                <div class="news-card">
                    <img src="${news.imageUrl}" alt="${news.title}">
                    <div class="card-content">
                        <h3>${news.title}</h3>
                        <p class="news-meta">Kategori: ${news.category} | Tanggal: ${news.date}</p>
                        <p>${news.summary}</p>
                        <a href="/news/${news.slug}" class="read-more">Baca Selengkapnya</a>
                    </div>
                </div>
            `;
            newsListGrid.insertAdjacentHTML('beforeend', newsCardHtml);
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

    // Populate region filter (simulated)
    function populateRegionFilter() {
        const regions = ["Jakarta", "Jawa Barat", "Jawa Tengah", "Jawa Timur", "Sumatera Utara"];
        const regionSelect = document.querySelector('.news-filters select:nth-of-type(2)');
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
    const totalPages = 5; // Simulated total pages

    function updatePagination() {
        pageInfo.textContent = `Halaman ${currentPage} dari ${totalPages}`;
        btnPrevPage.disabled = currentPage === 1;
        btnNextPage.disabled = currentPage === totalPages;
    }

    btnPrevPage.addEventListener('click', () => {
        if (currentPage > 1) {
            currentPage--;
            updatePagination();
            loadNewsArticles(); // Reload news for the new page (in real app, pass page number)
        }
    });

    btnNextPage.addEventListener('click', () => {
        if (currentPage < totalPages) {
            currentPage++;
            updatePagination();
            loadNewsArticles(); // Reload news for the new page (in real app, pass page number)
        }
    });

    updatePagination(); // Initial pagination state
});
