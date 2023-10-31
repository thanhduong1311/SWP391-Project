const form = document.querySelector('form');
const nameInput = document.querySelector('#floatingNameService');
const imageInput = document.querySelector('#floatingImageService');
const priceInput = document.querySelector('#floatingPriceService');
const discountInput = document.querySelector('#floatingDiscountService');
const introInput = document.querySelector('#floatingIntroService');
const descriptionInput = document.querySelector('#floatingDescriptionService');
const detailInput = document.querySelector('#floatingDetailService');
const btn = document.querySelector('#addBtn');
nameInput.addEventListener('focusout', () => {
    if (nameInput.value.match(/[^a-zA-Z0-9 ]/g)) {
        showErrorToast('Name must not contain special characters');
        const btn = document.querySelector('#addBtn');
        btn.disabled = true;
    } else {
        const btn = document.querySelector('#addBtn');
        btn.disabled = false
    }
});

priceInput.addEventListener('focusout', () => {
    if (priceInput.value <= 0) {
        showErrorToast('Price must be greater than 0');
        const btn = document.querySelector('#addBtn');
        btn.disabled = true;
    } else {
        const btn = document.querySelector('#addBtn');
        btn.disabled = false
    }
});

discountInput.addEventListener('focusout', () => {
    if (discountInput.value < 0) {
        showErrorToast('Discount must be greater than or equal to 0');
        const btn = document.querySelector('#addBtn');
        btn.disabled = true;
    } else {
        const btn = document.querySelector('#addBtn');
        btn.disabled = false
    }
});

introInput.addEventListener('focusout', () => {
    if (introInput.value.trim() === '') {
        showErrorToast('Intro is required');
        const btn = document.querySelector('#addBtn');
        btn.disabled = true;
    } else {
        const btn = document.querySelector('#addBtn');
        btn.disabled = false
    }
});

descriptionInput.addEventListener('focusout', () => {
    if (descriptionInput.value.trim() === '') {
        showErrorToast('Description is required');
        const btn = document.querySelector('#addBtn');
        btn.disabled = true;
    } else {
        const btn = document.querySelector('#addBtn');
        btn.disabled = false
    }
});

detailInput.addEventListener('change', () => {
    if (detailInput.value.trim() === '') {
        showErrorToast('Detail is required');
        const btn = document.querySelector('#addBtn');
        btn.disabled = true;
    } else {
        const btn = document.querySelector('#addBtn');
        btn.disabled = false
    }
});

imageInput.addEventListener('change', () => {
    if (imageInput.value.trim() === '') {
        showErrorToast('Image is required');
        const btn = document.querySelector('#addBtn');
        btn.disabled = true;
    } else {
        const btn = document.querySelector('#addBtn');
        btn.disabled = false
    }
});