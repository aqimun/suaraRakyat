document.addEventListener('DOMContentLoaded', () => {
    // Re-use accessibility toggle from Landing Page if needed
    const accessibilityButton = document.querySelector('.btn-accessibility');
    if (accessibilityButton) {
        accessibilityButton.addEventListener('click', () => {
            document.body.classList.toggle('simple-mode');
            // localStorage.setItem('simpleMode', document.body.classList.contains('simple-mode'));
        });
    }

    const complaintForm = document.getElementById('complaintForm');
    const formSteps = document.querySelectorAll('.form-step');
    const stepperSteps = document.querySelectorAll('.stepper .step');
    let currentStep = 1;

    function showStep(stepNumber) {
        formSteps.forEach((step, index) => {
            if (index + 1 === stepNumber) {
                step.classList.add('active');
            } else {
                step.classList.remove('active');
            }
        });
        stepperSteps.forEach((step, index) => {
            if (index + 1 === stepNumber) {
                step.classList.add('active');
            } else {
                step.classList.remove('active');
            }
        });
        currentStep = stepNumber;
    }

    // Initial display
    showStep(currentStep);

    // Handle Next/Previous buttons
    document.querySelectorAll('.btn-next').forEach(button => {
        button.addEventListener('click', () => {
            const nextStep = parseInt(button.dataset.step);
            if (validateCurrentStep(currentStep)) {
                showStep(nextStep);
            } else {
                alert('Harap lengkapi semua bidang yang wajib diisi.');
            }
        });
    });

    document.querySelectorAll('.btn-prev').forEach(button => {
        button.addEventListener('click', () => {
            const prevStep = parseInt(button.dataset.step);
            showStep(prevStep);
        });
    });

    // Category selection
    const categoryIcons = document.querySelectorAll('.icon-card');
    const categoryInput = document.getElementById('category');

    categoryIcons.forEach(icon => {
        icon.addEventListener('click', () => {
            categoryIcons.forEach(i => i.classList.remove('selected'));
            icon.classList.add('selected');
            categoryInput.value = icon.dataset.category;
        });
    });

    // Media preview functionality
    const mediaUpload = document.getElementById('mediaUpload');
    const mediaPreview = document.getElementById('mediaPreview');

    mediaUpload.addEventListener('change', (event) => {
        mediaPreview.innerHTML = ''; // Clear existing previews
        const files = event.target.files;
        if (files) {
            Array.from(files).forEach(file => {
                const reader = new FileReader();
                reader.onload = (e) => {
                    const previewItem = document.createElement('div');
                    previewItem.classList.add('preview-item');
                    if (file.type.startsWith('image/')) {
                        const img = document.createElement('img');
                        img.src = e.target.result;
                        previewItem.appendChild(img);
                    } else if (file.type.startsWith('video/')) {
                        const video = document.createElement('video');
                        video.src = e.target.result;
                        video.controls = true;
                        previewItem.appendChild(video);
                    }
                    mediaPreview.appendChild(previewItem);
                };
                reader.readAsDataURL(file);
            });
        }
    });

    // Basic form validation for each step
    function validateCurrentStep(step) {
        let isValid = true;
        const currentFormStep = document.getElementById(`formStep${step}`);
        const requiredInputs = currentFormStep.querySelectorAll('[required]');

        requiredInputs.forEach(input => {
            if (!input.value.trim()) {
                isValid = false;
                input.style.borderColor = 'red';
            } else {
                input.style.borderColor = '#ccc';
            }
        });

        if (step === 1 && !categoryInput.value) {
            isValid = false;
            alert('Harap pilih kategori laporan.');
        }

        return isValid;
    }

    // Handle form submission
    complaintForm.addEventListener('submit', (e) => {
        e.preventDefault();
        if (validateCurrentStep(3)) { // Validate final step before submission
            const formData = new FormData(complaintForm);
            const data = Object.fromEntries(formData.entries());

            console.log('Complaint Submitted:', data);
            // In a real application, send data to API
            // fetch('/complaints', {
            //     method: 'POST',
            //     body: formData // Use formData directly for file uploads
            // })
            // .then(response => response.json())
            // .then(result => {
            //     if (result.success) {
            //         alert('Laporan Anda terkirim! ID: ' + result.ticketId);
            //         window.location.href = '/report/track?id=' + result.ticketId; // Redirect to tracking page
            //     } else {
            //         alert('Gagal mengirim laporan: ' + result.message);
            //     }
            // })
            // .catch(error => {
            //     console.error('Error during complaint submission:', error);
            //     alert('Terjadi kesalahan saat mengirim laporan. Silakan coba lagi.');
            // });

            // For demonstration purposes:
            alert('Laporan Anda terkirim (simulasi)! ID: SR-2025-XXXXX. Est. tanggapan: 3 hari kerja.');
            window.location.href = '/report/track'; // Redirect to a simulated tracking page
        } else {
            alert('Harap lengkapi semua bidang yang wajib diisi.');
        }
    });
});
