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
    return age <= 16;
}


