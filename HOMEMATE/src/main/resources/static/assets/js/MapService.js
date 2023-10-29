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
    }, alert);

}



// service.reverseGeocode({
//     at: '10.0116,105.76507'
// }, (result) => {
//     result.items.forEach((item) => {
//         // Assumption: ui is instantiated
//         // Create an InfoBubble at the returned location with
//         // the address as its contents:
//         ui.addBubble(new H.ui.InfoBubble(item.position, {
//             content: item.address.label
//         }));
//     });
// }, alert);


//  Obtain the default map types from the platform object:
var defaultLayers = platform.createDefaultLayers();

// Instantiate (and display) a map:
const map = new H.Map(
    document.getElementById("map"),
    // Center the map on Dublin, Republic of Ireland, with the zoom level of 10:
    defaultLayers.vector.normal.map, {
        zoom: 15,
        center: {
            lat: 10.0116,
            lng: 105.76507
        }
    });


// MapEvents enables the event system.
// The behavior variable implements default interactions for pan/zoom (also on mobile touch environments).
const behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

// Enable dynamic resizing of the map, based on the current size of the enclosing cntainer
window.addEventListener('resize', () => map.getViewPort().resize());

const ui = H.ui.UI.createDefault(map, defaultLayers);


