package airtriangle.compassview;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.LinearLayout;

import compassview.airtriangle.R;

public class MainActivity extends Activity {

    private static final int GRID_SIZE = 10;

    private CompassSensorManager compassSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compassSensorManager = new CompassSensorManager(this);

        LinearLayout contentView = findViewById(R.id.contentView);
        for (int x = 0; x <= GRID_SIZE; x++) {
            LinearLayout xLineLayout = new LinearLayout(this);
            contentView.addView(xLineLayout);
            for (int y = 0; y <= GRID_SIZE; y++)
                xLineLayout.addView(generateCompassView());
        }
    }

    private CompassView generateCompassView() {
        Location moscowLocation = new Location("");
        moscowLocation.setLatitude(55.751244);
        moscowLocation.setLongitude(37.618423);

        Location krakowLocation = new Location("");
        krakowLocation.setLatitude(50.0646501);
        krakowLocation.setLongitude(19.9449799);

        CompassView compassView = new CompassView(this);
        compassView.init(compassSensorManager, moscowLocation, krakowLocation, R.drawable.ic_action_name);
        return compassView;
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
