const dobInput = document.getElementById('floatingBirthday');

dobInput.addEventListener('change', function(event) {
    const dob = event.target.value;

    // Check if the user is old enough to register
    if (isDateUnderage(dob)) {
        // Display an error message
        const messageDiv = document.getElementById('message');
        messageDiv.innerHTML = 'You must be at least 16 years old to register.';

        // Disable the submit button
        const submitButton = document.querySelector('button[type="submit"]');
        submitButton.disabled = true;
    } else {
        // Clear the error message and enable the submit button
        const messageDiv = document.getElementById('message');
        messageDiv.innerHTML = '';

        const submitButton = document.querySelector('button[type="submit"]');
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
    return age < 16;
}