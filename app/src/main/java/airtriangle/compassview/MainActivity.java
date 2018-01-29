package airtriangle.compassview;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import compassview.airtriangle.R;

public class MainActivity extends Activity {

    private CompassSensorManager compassSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compassSensorManager = new CompassSensorManager(this);

        Location moscowLocation = new Location("");
        moscowLocation.setLatitude(55.751244);
        moscowLocation.setLongitude(37.618423);

        Location krakowLocation = new Location("");
        krakowLocation.setLatitude(50.0646501);
        krakowLocation.setLongitude(19.9449799);

        CompassView compassView = findViewById(R.id.compassView);
        compassView.init(compassSensorManager, moscowLocation, krakowLocation, R.drawable.ic_launcher_foreground);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compassSensorManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compassSensorManager.onPause();
    }

}
