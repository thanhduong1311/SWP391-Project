const platform = new H.service.Platform({
    'apikey': 'hdUeo8qlrCz1vOREZej-KBRIPRdNeNY7qoXeQ52Kp2w'
});




function locate(lo) {

    var service = platform.getSearchService();

    service.geocode({
        q: lo
    }, (result) => {
        // Add a marker for each location found
        result.items.forEach((item) => {

            map.addObject( new H.map.Marker(item.position));

            document.getElementById("loca").value = item.position.lat + "###" + item.position.lng

            // map.removeObject(mapitem);
        });
    }, showWaringToast("Can not detect current customer loaction."));

}



//  Obtain the default map types from the platform object:
var defaultLayers = platform.createDefaultLayers();

// Instantiate (and display) a map:
var map = new H.Map(
    document.getElementById("map"),
    // Center the map on Dublin, Republic of Ireland, with the zoom level of 10:
    defaultLayers.vector.normal.map, {
        zoom: 13,
        center: {
            lat:0 ,
            lng: 0
        }
    });


// MapEvents enables the event system.
// The behavior variable implements default interactions for pan/zoom (also on mobile touch environments).
const behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

// Enable dynamic resizing of the map, based on the current size of the enclosing cntainer
window.addEventListener('resize', () => map.getViewPort().resize());

const ui = H.ui.UI.createDefault(map, defaultLayers);



var cusLocationEle = document.getElementById("cusLoca")
// var empLocationEle = document.getElementById("emoLoca")

var cus = cusLocationEle.innerHTML
// var emp = empLocationEle.innerHTML

// var emLAT = emp.slice(0, emp.indexOf(","))
// var emLNG = emp.slice( emp.indexOf(",") +1,emp.length)
var cusLAT = cus.slice(0, cus.indexOf(","))
var cusLNG = cus.slice( cus.indexOf(",") +1,cus.length)

var centerLAT= cusLAT;
var centerLNG= cusLNG;




function sliceLocation() {
    addMarkersToMap(map,cusLAT,cusLNG);
}


function addMarkersToMap(map,cusLAT,cusLNG ) {
    console.log("Adding location");
    var pngCus = new H.map.Icon("<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"1em\" viewBox=\"0 0 448 512\"><path d=\"M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z\"/></svg>", { size: { w: 20, h: 20 } });
    var cusMarker = new H.map.Marker({lat:cusLAT, lng:cusLNG},{ icon: pngCus });
    map.addObject(cusMarker);

    // var pngEmp = new H.map.Icon("<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"1em\" viewBox=\"0 0 576 512\"><path d=\"M575.8 255.5c0 18-15 32.1-32 32.1h-32l.7 160.2c0 2.7-.2 5.4-.5 8.1V472c0 22.1-17.9 40-40 40H456c-1.1 0-2.2 0-3.3-.1c-1.4 .1-2.8 .1-4.2 .1H416 392c-22.1 0-40-17.9-40-40V448 384c0-17.7-14.3-32-32-32H256c-17.7 0-32 14.3-32 32v64 24c0 22.1-17.9 40-40 40H160 128.1c-1.5 0-3-.1-4.5-.2c-1.2 .1-2.4 .2-3.6 .2H104c-22.1 0-40-17.9-40-40V360c0-.9 0-1.9 .1-2.8V287.6H32c-18 0-32-14-32-32.1c0-9 3-17 10-24L266.4 8c7-7 15-8 22-8s15 2 21 7L564.8 231.5c8 7 12 15 11 24z\"/></svg>", { size: { w: 20, h: 20 } });
    // var empMarker = new H.map.Marker({lat:empLAT, lng:empLNG },{ icon: pngEmp });
    // map.addObject(empMarker);

    console.log("done");

}

window.onload = function () {


    cusLocationEle = document.getElementById("cusLoca")
    // empLocationEle = document.getElementById("emoLoca")

    cus = cusLocationEle.innerHTML
    // emp = empLocationEle.innerHTML
    //
    // emLAT = emp.slice(0, emp.indexOf(","))
    // emLNG = emp.slice( emp.indexOf(",") +1,emp.length)
    cusLAT = cus.slice(0, cus.indexOf(","))
    cusLNG = cus.slice( cus.indexOf(",") +1,cus.length)

    centerLAT= cusLAT;
    centerLNG= cusLNG;

    sliceLocation()
    map.setCenter({lat: centerLAT, lng: centerLNG});
}


