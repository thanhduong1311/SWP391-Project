

// Get the start input and select elements
var startInput = document.querySelector('#start');
var endInput = document.querySelector('#end');
var selectElement = document.querySelector('#forTime');
var bookingbtn = document.querySelector("#bookingBtn")
var addressinput = document.getElementById("address")
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



var currentMarker;
var locationOfAddress
function setUpClickListener(map) {
    // Attach an event listener to map display
    // obtain the coordinates and display in an alert box.

    map.addEventListener('tap', function (evt) {
        var coord = map.screenToGeo(evt.currentPointer.viewportX,
            evt.currentPointer.viewportY);

        if (currentMarker) {
            map.removeObject(currentMarker);
        }

        logEvent('Clicked at ' + Math.abs(coord.lat.toFixed(4)) +
            ' ' + Math.abs(coord.lng.toFixed(4)))

        var cusMarker = new H.map.Marker({ lat: Math.abs(coord.lat.toFixed(4)), lng: Math.abs(coord.lng.toFixed(4)) });

        console.log("LAT: " + Math.abs(coord.lat.toFixed(4)))
        console.log("LNG: " + Math.abs(coord.lng.toFixed(4)))
        currentMarker = cusMarker;

        // Add the new marker to the map
        map.addObject(cusMarker);


        var locationOfAddress =""+Math.abs(coord.lat.toFixed(4))+','+Math.abs(coord.lng.toFixed(4))

        document.getElementById('rawLocation').value = ""+Math.abs(coord.lat.toFixed(4))+","+Math.abs(coord.lng.toFixed(4))
        console.log("input: " + document.getElementById('rawLocation').value)

    });
}

var platform = new H.service.Platform({
    apikey: 'hdUeo8qlrCz1vOREZej-KBRIPRdNeNY7qoXeQ52Kp2w'
});
var defaultLayers = platform.createDefaultLayers();

var map = new H.Map(document.getElementById('map'),
    defaultLayers.vector.normal.map, {
        center: { lat: 10.05165, lng: 105.77329 },
        zoom: 10,
        pixelRatio: window.devicePixelRatio || 1
    });

window.addEventListener('resize', () => map.getViewPort().resize());


var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

// Step 4: create custom logging facilities
var logContainer = document.createElement('ul');
logContainer.className = 'log';
logContainer.innerHTML = '<li class="log-entry">Try clicking on the map</li>';
map.getElement().appendChild(logContainer);

// Helper for logging events
function logEvent(str) {
    var entry = document.createElement('li');
    entry.className = 'log-entry';
    entry.textContent = str;
    logContainer.insertBefore(entry, logContainer.firstChild);
}


setUpClickListener(map);

//////////////////////////////////////
//change form herez


var currentLAT = 10.05165
var currentLNG = 105.77329
function locate(lo) {

    var service = platform.getSearchService();

    service.geocode({
        q: lo
    }, (result) => {
        // Add a marker for each location found
        result.items.forEach((item) => {

            map.addObject( new H.map.Marker(item.position));

            currentLAT = item.position.lat;
            currentLNG =item.position.lng ;


            // map.removeObject(mapitem);
        });
    }, showWaringToast("Can not detect current customer loaction."));

}


window.onload = function () {
    // var locateCenter  = document.getElementById("address").value
    // locate(locateCenter);
    // map.setCenter({lat: currentLAT, lng: currentLNG});


}



function checkCondition() {
    // Lấy giá trị của trường input
    let name = document.querySelector("input[name='name']").value;

    if(addressinput.value.trim() == "") {
        showErrorToast("Please, pick an address!")
    } else {
        document.querySelector("#bookingForm").submit();
    }


}

// Liên kết hàm với sự kiện onclick() của nút submit
document.querySelector("#bookingBtn").onclick = checkCondition;