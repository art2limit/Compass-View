package garin.artemiy.compassview.example;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import garin.artemiy.compassview.R;
import garin.artemiy.compassview.library.CompassSensorsActivity;
import garin.artemiy.compassview.library.CompassView;

import java.util.UUID;

public class MainActivity extends CompassSensorsActivity {

    private static final double DELTA = 0.5;

    private Location userLocation;
    private Location originObjectLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        ObjectAdapter objectAdapter = new ObjectAdapter(this);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        userLocation = getBestLastKnowLocation(locationManager);
        originObjectLocation = getBestLastKnowLocation(locationManager);

        addTestData(objectAdapter);

        ((ListView) findViewById(R.id.listView)).setAdapter(objectAdapter);
    }

    private void addTestData(ObjectAdapter objectAdapter) {
        Location testObject1 = getTestObject();
        testObject1.setLongitude(testObject1.getLongitude() - DELTA);
        testObject1.setLatitude(testObject1.getLatitude() - DELTA);
        objectAdapter.add(testObject1);

        Location testObject2 = getTestObject();
        testObject2.setLongitude(testObject2.getLongitude() + DELTA);
        testObject2.setLatitude(testObject2.getLatitude() + DELTA);
        objectAdapter.add(testObject2);

        Location testObject3 = getTestObject();
        testObject3.setLongitude(testObject3.getLongitude() + DELTA);
        testObject3.setLatitude(testObject3.getLatitude() - DELTA);
        objectAdapter.add(testObject3);

        Location testObject4 = getTestObject();
        testObject4.setLongitude(testObject4.getLongitude() - DELTA);
        testObject4.setLatitude(testObject4.getLatitude() + DELTA);
        objectAdapter.add(testObject4);
    }

    private Location getTestObject() {
        Location objectLocation = new Location("");
        objectLocation.setLatitude(originObjectLocation.getLatitude());
        objectLocation.setLongitude(originObjectLocation.getLongitude());
        return objectLocation;
    }

    private class ObjectAdapter extends ArrayAdapter<Location> {

        public ObjectAdapter(Context context) {
            super(context, R.layout.list_item_layout);
        }

        @SuppressLint("InflateParams")
        @SuppressWarnings("ConstantConditions")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.list_item_layout, null);

            TextView titleView = (TextView) convertView.findViewById(R.id.titleView);
            titleView.setText(UUID.randomUUID().toString());

            CompassView compassView = (CompassView) convertView.findViewById(R.id.compassView);
            compassView.initializeCompass(userLocation, getItem(position), R.drawable.arrow);

            return convertView;
        }

    }

    private Location getBestLastKnowLocation(LocationManager locationManager) {
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) location = new Location("");
        return location;
    }

}
