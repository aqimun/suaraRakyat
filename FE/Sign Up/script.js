document.addEventListener('DOMContentLoaded', () => {
    const signupForm = document.getElementById('signupForm');
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
            // Basic validation before moving to next step
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

    // Image preview functionality
    const ktpUpload = document.getElementById('ktpUpload');
    const ktpPreview = document.getElementById('ktpPreview');
    const selfieUpload = document.getElementById('selfieUpload');
    const selfiePreview = document.getElementById('selfiePreview');

    function setupImagePreview(inputFile, previewElement) {
        inputFile.addEventListener('change', (event) => {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = (e) => {
                    previewElement.style.backgroundImage = `url('${e.target.result}')`;
                    previewElement.textContent = ''; // Clear any text
                };
                reader.readAsDataURL(file);
            } else {
                previewElement.style.backgroundImage = 'none';
                previewElement.textContent = 'Tidak ada gambar';
            }
        });
    }

    setupImagePreview(ktpUpload, ktpPreview);
    setupImagePreview(selfieUpload, selfiePreview);

    // Basic form validation for each step
    function validateCurrentStep(step) {
        let isValid = true;
        const currentFormStep = document.getElementById(`formStep${step}`);
        const requiredInputs = currentFormStep.querySelectorAll('[required]');

        requiredInputs.forEach(input => {
            if (!input.value.trim()) {
                isValid = false;
                input.style.borderColor = 'red'; // Highlight invalid input
            } else {
                input.style.borderColor = '#ccc';
            }
        });

        if (step === 1) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            if (password !== confirmPassword) {
                alert('Kata sandi dan konfirmasi kata sandi tidak cocok.');
                document.getElementById('confirmPassword').style.borderColor = 'red';
                isValid = false;
            } else {
                document.getElementById('confirmPassword').style.borderColor = '#ccc';
            }
        } else if (step === 4) {
            const privacyConsent = document.getElementById('privacyConsent');
            const dataConsent = document.getElementById('dataConsent');
            if (!privacyConsent.checked || !dataConsent.checked) {
                alert('Anda harus menyetujui Kebijakan Privasi dan penggunaan data.');
                isValid = false;
            }
        }
        return isValid;
    }

    // Handle form submission
    signupForm.addEventListener('submit', (e) => {
        e.preventDefault();
        if (validateCurrentStep(4)) { // Validate final step before submission
            const formData = new FormData(signupForm);
            const data = Object.fromEntries(formData.entries());

            console.log('Form Submitted:', data);
            // In a real application, send data to API
            // fetch('/auth/register', {
            //     method: 'POST',
            //     body: formData // Use formData directly for file uploads
            // })
            // .then(response => response.json())
            // .then(result => {
            //     if (result.success) {
            //         alert('Pendaftaran berhasil! KYC Anda sedang diverifikasi.');
            //         window.location.href = '/login'; // Redirect to login or status page
            //     } else {
            //         alert('Pendaftaran gagal: ' + result.message);
            //     }
            // })
            // .catch(error => {
            //     console.error('Error during registration:', error);
            //     alert('Terjadi kesalahan saat pendaftaran. Silakan coba lagi.');
            // });

            alert('Pendaftaran berhasil (simulasi)! KYC Anda sedang diverifikasi.');
            window.location.href = '/login'; // Redirect to login page
        } else {
            alert('Harap lengkapi semua bidang yang wajib diisi dan setujui persyaratan.');
        }
    });
});
