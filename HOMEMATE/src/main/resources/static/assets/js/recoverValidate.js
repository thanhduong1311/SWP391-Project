
var changePassInput = document.getElementById("floatingInput")
changePassInput.addEventListener('change', function(event) {
    const password = event.target.value;

    // Check if the password meets the requirements
    const hasNumber = /[0-9]/.test(password);
    const hasUppercase = /[A-Z]/.test(password);
    const hasLowercase = /[a-z]/.test(password);
    const hasSpecialCharacter = /[!@#$%^&*()_+{}:<>?]/.test(password);

    if (!hasNumber || !hasUppercase || !hasLowercase || !hasSpecialCharacter) {
        // Display an error message
        showErrorToast("Password must include at least one number, one uppercase letter, one lowercase letter, and one special character.'")
        // Disable the submit button
        const submitButton = document.querySelector('button[type="submit"]');
        submitButton.disabled = true;
    } else {
        // Clear the error message and enable the submit button

        const submitButton = document.querySelector('button[type="submit"]');
        submitButton.disabled = false;
    }
});

document.querySelector("form").addEventListener("keydown", function(event) {
    if (event.keyCode === 13) {
        event.preventDefault();
    }
});