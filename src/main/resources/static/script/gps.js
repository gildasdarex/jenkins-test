$(document).ready(function() {

    function initGeolocation()
    {
        if( navigator.geolocation )
        {
            // Call getCurrentPosition with success and failure callbacks
            navigator.geolocation.getCurrentPosition( success, fail );
        }
        else
        {
            console.log("Sorry, your browser does not support geolocation services.");
        }
    }


    function success(position)
    {
       console.log(position);
        document.getElementById('longitude').value = position.coords.longitude;
        document.getElementById('latitude').value = position.coords.latitude;
    }

    function fail(position)
    {
        // Could not obtain location
        console.log(position);
    }

    if(document.location.href.indexOf("pej/entreprises/calendrierformateur") != -1){
        initGeolocation();
    }


    // var submitButton = $("#submit-candidat-filter");
    // var nextLink = $("#candidat-next-page");
    // var previousLink = $("#candidat-previous-page");



});