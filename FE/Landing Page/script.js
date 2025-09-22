document.addEventListener('DOMContentLoaded', () => {
    // Handle accessibility toggle
    const accessibilityButton = document.querySelector('.btn-accessibility');
    accessibilityButton.addEventListener('click', () => {
        document.body.classList.toggle('simple-mode');
        // You might want to save this preference in localStorage
        // localStorage.setItem('simpleMode', document.body.classList.contains('simple-mode'));
    });

    // Example for dynamic content loading (e.g., news, stats)
    // In a real application, this would fetch data from an API
    function loadDynamicContent() {
        // Example: Load news
        // fetch('/api/news/featured')
        //     .then(response => response.json())
        //     .then(newsItems => {
        //         const newsGrid = document.querySelector('.news-grid');
        //         newsGrid.innerHTML = ''; // Clear existing placeholders
        //         newsItems.forEach(news => {
        //             const newsCard = `
        //                 <div class="news-card">
        //                     <img src="${news.imageUrl}" alt="${news.title}">
        //                     <h3>${news.title}</h3>
        //                     <p>${news.summary}</p>
        //                     <a href="/news/${news.slug}">Baca Selengkapnya</a>
        //                 </div>
        //             `;
        //             newsGrid.insertAdjacentHTML('beforeend', newsCard);
        //         });
        //     })
        //     .catch(error => console.error('Error loading news:', error));

        // Example: Load quick stats
        // fetch('/api/stats')
        //     .then(response => response.json())
        //     .then(stats => {
        //         document.querySelector('.stat-card:nth-child(1) p').textContent = stats.reportsToday;
        //         document.querySelector('.stat-card:nth-child(2) p').textContent = stats.projectsCompleted;
        //         document.querySelector('.stat-card:nth-child(3) p').textContent = stats.activeElections;
        //     })
        //     .catch(error => console.error('Error loading stats:', error));

        // Example: Load province/kabupaten options
        // fetch('/api/regions/provinces')
        //     .then(response => response.json())
        //     .then(provinces => {
        //         const provinceSelect = document.querySelector('.map-filter select:nth-child(2)');
        //         provinces.forEach(province => {
        //             const option = document.createElement('option');
        //             option.value = province.id;
        //             option.textContent = province.name;
        //             provinceSelect.appendChild(option);
        //         });
        //     })
        //     .catch(error => console.error('Error loading provinces:', error));
    }

    loadDynamicContent();

    // Basic search functionality (client-side example, would be server-side in real app)
    const searchInput = document.querySelector('.search-input');
    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            const query = searchInput.value.trim();
            if (query) {
                console.log('Searching for:', query);
                // In a real app, redirect to a search results page:
                // window.location.href = `/search?q=${encodeURIComponent(query)}`;
            }
        }
    });
});
