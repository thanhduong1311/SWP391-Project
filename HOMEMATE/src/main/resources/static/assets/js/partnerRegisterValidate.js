
// cardIdNumber


var profileDOB = document.getElementById('floatingInput12');

profileDOB.addEventListener('focusout', function(event) {
    const dob = event.target.value;

    console.log("check")
    // Check if the user is old enough to register
    if (isDateUnderage(dob)) {
        // Display an error message
        showErrorToast("You must enough 18 years old to register.")

        // Disable the submit button
        const submitButton = document.querySelector("#submitbtn");
        submitButton.disabled = true;
    } else {
        // Clear the error message and enable the submit button

        const submitButton = document.querySelector("#submitbtn");
        submitButton.disabled = false;
    }
});

function isDateUnderage(dob) {
    // Convert the date of birth to a Date object
    const dobDate = new Date(dob);

    // Calculate the user's age
    const today = new Date();
    const age = today.getFullYear() - dobDate.getFullYear();

    // Check if the user is under 18 years old
    return age < 18;
}

const passwordInput = document.getElementById('floatingpw');

passwordInput.addEventListener('focusout', function(event) {
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
        const submitButton = document.querySelector("#submitbtn");
        submitButton.disabled = true;
    } else {
        // Clear the error message and enable the submit button

        const submitButton = document.querySelector("#submitbtn");
        submitButton.disabled = false;
    }
});

document.querySelector("#floatingId").addEventListener("change", function() {
    // Kiểm tra tính hợp lệ của số CMND
    const idCard = document.querySelector("#floatingId").value;
    const isIdCardValid = validateIdCard(idCard);

    // Nếu số CMND không hợp lệ, thì disable button submit
    if (!isIdCardValid) {
        showErrorToast("ID Card number is not valid!")
        document.querySelector("#submitbtn").disabled = true;
    } else {

        document.querySelector("#submitbtn").disabled = false;
    }
});

function validateIdCard(idCard) {
    // Kiểm tra độ dài của số CMND
    if (idCard.length !== 9 && idCard.length !== 12) {
        return false;
    }

    // Kiểm tra tính hợp lệ của các ký tự trong số CMND
    for (let i = 0; i < idCard.length; i++) {
        if (isNaN(idCard[i])) {
            return false;
        }
    }

    return true;
}