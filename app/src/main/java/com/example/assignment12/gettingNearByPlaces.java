package com.example.assignment12;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class gettingNearByPlaces extends AsyncTask<Object, String, String> {
    GoogleMap googleMap;
    String placeData;
    String url;


    @Override
    protected String doInBackground(Object... objects) {
        googleMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        fetchingURL fetchingURL = new fetchingURL();

        try{
            placeData = fetchingURL.readUrl(url);
//            placeData = fetchUrl.read
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return placeData;
    }




    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;
        dataParsing parser = new dataParsing();
        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String >> nearbyList){

        for(int i = 0; i< nearbyList.size(); i++){
            HashMap<String, String > place = nearbyList.get(i);

            String placeName = place.get("placeName");
            String vicinity = place.get("vicinity");
            double lat = Double.parseDouble(place.get("latitude"));
            double lng = Double.parseDouble(place.get("longitude"));

            LatLng latLng = new LatLng(lat, lng);

            //Marker options

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(placeName + ":" + vicinity)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            googleMap.addMarker(markerOptions);

        }
    }


}
