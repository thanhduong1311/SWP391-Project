

// Get the start input and select elements
var startInput = document.querySelector('#start');
var endInput = document.querySelector('#end');
var selectElement = document.querySelector('#forTime');
var bookingbtn = document.querySelector("#bookingBtn")
bookingbtn.disabled = true;


// Disable the select element by default
selectElement.disabled = true;

// Add an event listener to the start input element
startInput.addEventListener('change', function() {
    // If the start input value is not empty, enable the select element
    if (startInput.value !== '') {
        selectElement.disabled = false;
        bookingbtn.disabled = false;

    } else {
        // Otherwise, disable the select element
        selectElement.disabled = true;
    }
});


startInput.addEventListener("change", function() {
        // Lấy thời gian hiện tại
        const now = new Date();
        console.log(now)

        // Lấy thời gian bắt đầu được chọn bởi người dùng
        const start =  new Date(startInput.value)
        console.log(start)

        // Kiểm tra thời gian bắt đầu
        if (start.getTime() < now.getTime() + 1000 * 60 * 60) {
            // Disable nút booking
            bookingbtn.disabled = true;
            console.log("False")
            // Thêm lớp CSS để hiển thị thông báo
            showErrorToast("You need to book at least 1 hour before the start time.")
        } else {
            // Enable nút booking
            bookingbtn.disabled = false;
            console.log("True")
        }
});


