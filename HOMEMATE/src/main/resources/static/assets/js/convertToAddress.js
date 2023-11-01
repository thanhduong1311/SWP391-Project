// var locationPoint = "10.0102,105.7663"

function getAddress(locationPoint) {
    function reverseGeocode(platform) {
        var geocoder = platform.getSearchService(),
            reverseGeocodingParameters = {
                at: locationPoint, // Berlin
                limit: '1'
            };

        geocoder.reverseGeocode(
            reverseGeocodingParameters,
            onSuccess,
            onError
        );
    }



    function onSuccess(result) {
        var locations = result.items;

        addLocationsToMap(locations);
        addLocationsToPanel(locations);

    }


    function onError(error) {
        showErrorToast("Can\\'t reach the remote server");
    }
///////////////////////////////////////////////////////////////////////////////////////////////

    var platform = new H.service.Platform({
        apikey: 'hdUeo8qlrCz1vOREZej-KBRIPRdNeNY7qoXeQ52Kp2w'
    });
    var defaultLayers = platform.createDefaultLayers();

    //Step 2: initialize a map - this map is centered over California
    var map = new H.Map(document.getElementById('mapHide'),
        defaultLayers.vector.normal.map, {
            center: { lat: 37.376, lng: -122.034 },
            zoom: 15,
            pixelRatio: window.devicePixelRatio || 1
        });
    // add a resize listener to make sure that the map occupies the whole container
    window.addEventListener('resize', () => map.getViewPort().resize());
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var locationsContainer = document.getElementById('panel');

    var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

    // Create the default UI components
    var ui = H.ui.UI.createDefault(map, defaultLayers);

    var bubble;

    function openBubble(position, text) {
        if (!bubble) {
            bubble = new H.ui.InfoBubble(
                position,
                { content: text });
            ui.addBubble(bubble);
        } else {
            bubble.setPosition(position);
            bubble.setContent(text);
            bubble.open();
        }
    }

    function addLocationsToPanel(locations) {

        var nodeOL = document.createElement('ul'),
            i;

        // nodeOL.style.fontSize = 'small';
        // nodeOL.style.marginLeft = '5%';
        // nodeOL.style.marginRight = '5%';

        for (i = 0; i < locations.length; i += 1) {
            let location = locations[i],
                address = location.address,
                position = location.position;
            document.getElementById("address").value = address.label

        }

        // locationsContainer.appendChild(nodeOL);
    }


    function addLocationsToMap(locations) {
        var group = new H.map.Group(),
            position,
            i;

        // Add a marker for each location found
        for (i = 0; i < locations.length; i += 1) {
            let location = locations[i];
            marker = new H.map.Marker(location.position);
            marker.label = location.address.label;
            group.addObject(marker);
        }

        group.addEventListener('tap', function (evt) {
            map.setCenter(evt.target.getGeometry());
            openBubble(
                evt.target.getGeometry(), evt.target.label);
        }, false);

        // Add the locations group to the map
        map.addObject(group);
        map.getViewModel().setLookAtData({
            bounds: group.getBoundingBox()
        });
    }

    reverseGeocode(platform);
}



