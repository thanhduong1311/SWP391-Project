var profileDOB = document.getElementById('userDob');

profileDOB.addEventListener('focusout', function(event) {
    const dob = event.target.value;

    console.log("check")
    // Check if the user is old enough to register
    if (isDateUnderage(dob)) {
        // Display an error message
        const messageDiv = document.getElementById('message');
        showErrorToast("Invalid age.")

        // Disable the submit button
        const submitButton = document.querySelector("#saveEdit");
        submitButton.disabled = true;
    } else {
        // Clear the error message and enable the submit button
        const messageDiv = document.getElementById('message');

        const submitButton = document.querySelector("#saveEdit");
        submitButton.disabled = false;
    }
});

function isDateUnderage(dob) {
    // Convert the date of birth to a Date object
    const dobDate = new Date(dob);

    // Calculate the user's age
    const today = new Date();
    const age = today.getFullYear() - dobDate.getFullYear();

    // Check if the user is under 16 years old
    return age <= 18;
}

var changePassInput = document.getElementById("userNewPassword")
passwordInput.addEventListener('change', function(event) {
    const password = event.target.value;

    // Check if the password meets the requirements
    const hasNumber = /[0-9]/.test(password);
    const hasUppercase = /[A-Z]/.test(password);
    const hasLowercase = /[a-z]/.test(password);
    const hasSpecialCharacter = /[!@#$%^&*()_+{}:<>?]/.test(password);

    if (!hasNumber || !hasUppercase || !hasLowercase || !hasSpecialCharacter) {
        // Display an error message
        const messageDiv = document.getElementById('message');
        showErrorToast("Password must include at least one number, one uppercase letter, one lowercase letter, and one special character.'")
        // Disable the submit button
        const submitButton = document.querySelector('button[type="submit"]');
        submitButton.disabled = true;
    } else {
        // Clear the error message and enable the submit button
        const messageDiv = document.getElementById('message');

        const submitButton = document.querySelector('button[type="submit"]');
        submitButton.disabled = false;
    }
});