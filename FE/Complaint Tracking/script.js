document.addEventListener('DOMContentLoaded', () => {
    // Re-use accessibility toggle from Landing Page if needed
    const accessibilityButton = document.querySelector('.btn-accessibility');
    if (accessibilityButton) {
        accessibilityButton.addEventListener('click', () => {
            document.body.classList.toggle('simple-mode');
            // localStorage.setItem('simpleMode', document.body.classList.contains('simple-mode'));
        });
    }

    const trackingForm = document.getElementById('trackingForm');
    const trackingResultsDiv = document.getElementById('trackingResults');
    const displayTicketId = document.getElementById('displayTicketId');
    const detailCategory = document.getElementById('detailCategory');
    const detailTitle = document.getElementById('detailTitle');
    const detailDescription = document.getElementById('detailDescription');
    const detailLocation = document.getElementById('detailLocation');
    const detailDate = document.getElementById('detailDate');
    const detailCurrentStatus = document.getElementById('detailCurrentStatus');
    const timelineItemsContainer = document.getElementById('timelineItems');
    const attachedMediaContainer = document.getElementById('attachedMedia');
    const reportMediaSection = document.querySelector('.report-media');

    const statusSteps = {
        submitted: document.getElementById('status-submitted'),
        processing: document.getElementById('status-processing'),
        assigned: document.getElementById('status-assigned'),
        resolved: document.getElementById('status-resolved')
    };

    function updateStatusIndicator(currentStatus) {
        // Reset all steps
        Object.values(statusSteps).forEach(step => step.classList.remove('active'));

        // Activate steps up to the current status
        let activate = false;
        for (const statusKey in statusSteps) {
            if (statusSteps.hasOwnProperty(statusKey)) {
                if (statusKey === currentStatus) {
                    activate = true;
                }
                if (activate) {
                    statusSteps[statusKey].classList.add('active');
                }
            }
        }
    }

    function displayTrackingResults(data) {
        displayTicketId.textContent = data.ticketId;
        detailCategory.textContent = data.category;
        detailTitle.textContent = data.title;
        detailDescription.textContent = data.description;
        detailLocation.textContent = data.location;
        detailDate.textContent = data.submissionDate;
        detailCurrentStatus.textContent = data.currentStatusText;
        detailCurrentStatus.className = `status-badge ${data.currentStatus.toLowerCase()}`; // Update class for styling

        // Update status indicator
        updateStatusIndicator(data.currentStatus.toLowerCase());

        // Populate timeline
        timelineItemsContainer.innerHTML = '';
        data.timeline.forEach(item => {
            const timelineItemHtml = `
                <div class="timeline-item">
                    <p class="timeline-date">${item.date}</p>
                    <p class="timeline-event">${item.event}</p>
                </div>
            `;
            timelineItemsContainer.insertAdjacentHTML('beforeend', timelineItemHtml);
        });

        // Populate attached media
        attachedMediaContainer.innerHTML = '';
        if (data.media && data.media.length > 0) {
            reportMediaSection.style.display = 'block';
            data.media.forEach(mediaItem => {
                const mediaHtml = `
                    ${mediaItem.type === 'image' ? `<img src="${mediaItem.url}" alt="Media Laporan">` : `<video src="${mediaItem.url}" controls></video>`}
                `;
                attachedMediaContainer.insertAdjacentHTML('beforeend', mediaHtml);
            });
        } else {
            reportMediaSection.style.display = 'none';
        }

        trackingResultsDiv.style.display = 'block';
    }

    if (trackingForm) {
        trackingForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const ticketId = document.getElementById('ticketId').value.trim();

            if (!ticketId) {
                alert('Harap masukkan ID Laporan.');
                return;
            }

            console.log('Tracking Report for ID:', ticketId);

            // In a real application, you would fetch data from your backend API
            // fetch('/api/complaints/' + ticketId)
            // .then(response => response.json())
            // .then(data => {
            //     if (data.success) {
            //         displayTrackingResults(data.report);
            //     } else {
            //         alert('Laporan tidak ditemukan: ' + data.message);
            //         trackingResultsDiv.style.display = 'none';
            //     }
            // })
            // .catch(error => {
            //     console.error('Error during tracking request:', error);
            //     alert('Terjadi kesalahan saat melacak laporan. Silakan coba lagi.');
            // });

            // For demonstration purposes:
            const simulatedReport = {
                ticketId: ticketId,
                category: "Infrastruktur",
                title: "Jalan Rusak Parah di Jl. Merdeka",
                description: "Terdapat banyak lubang besar di sepanjang Jl. Merdeka, sangat membahayakan pengendara.",
                location: "Jl. Merdeka No. 123, Bandung",
                submissionDate: "2025-09-22 10:00",
                currentStatus: "Processing", // Can be Submitted, Processing, Assigned, Resolved
                currentStatusText: "Diproses",
                timeline: [
                    { date: "2025-09-22 10:00", event: "Laporan diajukan." },
                    { date: "2025-09-22 14:30", event: "Laporan sedang dalam proses verifikasi oleh Staff Admin." },
                    { date: "2025-09-23 09:00", event: "Laporan ditugaskan kepada Dinas Pekerjaan Umum." }
                ],
                media: [
                    { type: "image", url: "../img/mederkkaa.jpg" },
                    // { type: "video", url: "path/to/video.mp4" }
                ]
            };

            if (ticketId === "SR-2025-XXXXX") { // Simulate a found report
                displayTrackingResults(simulatedReport);
            } else {
                alert('Laporan dengan ID tersebut tidak ditemukan.');
                trackingResultsDiv.style.display = 'none';
            }
        });
    }

    // Check for ticket ID in URL on page load (e.g., /report/track?id=SR-2025-XXXXX)
    const urlParams = new URLSearchParams(window.location.search);
    const urlTicketId = urlParams.get('id');
    if (urlTicketId) {
        document.getElementById('ticketId').value = urlTicketId;
        trackingForm.dispatchEvent(new Event('submit')); // Automatically submit the form
    }
});
