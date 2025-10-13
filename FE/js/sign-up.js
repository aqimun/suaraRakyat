import { API_CONFIG } from './config.js';

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
    async function uploadFile(file) {
        const formData = new FormData();
        formData.append('file', file);

        const response = await fetch(`${API_CONFIG.BASE_URL}/files/upload`, {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`File upload failed: ${errorText}`);
        }

        return response.text();
    }

    signupForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        if (validateCurrentStep(4)) { // Validate final step before submission
            try {
                const ktpFile = document.getElementById('ktpUpload').files[0];
                const selfieFile = document.getElementById('selfieUpload').files[0];

                if (!ktpFile || !selfieFile) {
                    alert('Harap unggah file KTP dan Selfie.');
                    return;
                }

                // Upload files and get references
                const ktpRef = await uploadFile(ktpFile);
                const selfieRef = await uploadFile(selfieFile);

                const formData = new FormData(signupForm);
                const data = Object.fromEntries(formData.entries());

                const registerRequest = {
                    nameDisplay: data.fullName,
                    email: data.email,
                    phone: data.phone,
                    password: data.password,
                    ktpRef: ktpRef,
                    selfieRef: selfieRef
                };

                const registerResponse = await fetch(`${API_CONFIG.BASE_URL}/auth/register`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(registerRequest)
                });

                if (!registerResponse.ok) {
                    const errorText = await registerResponse.text();
                    throw new Error(errorText);
                }

                const result = await registerResponse.json(); // Assuming backend returns JSON on success
                alert('Pendaftaran berhasil! Status KYC Anda: PENDING.');
                window.location.href = '../pages/login.html'; // Redirect to login page

            } catch (error) {
                console.error('Error during registration:', error);
                alert('Pendaftaran gagal: ' + error.message);
            }
        } else {
            alert('Harap lengkapi semua bidang yang wajib diisi dan setujui persyaratan.');
        }
    });
});
