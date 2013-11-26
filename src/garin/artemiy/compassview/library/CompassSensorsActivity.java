package garin.artemiy.compassview.library;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Author: Artemiy Garin
 * Date: 26.11.13
 */
public class CompassSensorsActivity extends FragmentActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor magneticFieldSensor;

    private float[] rotationMatrix = new float[16];
    private float[] accelerometerData = new float[3];
    private float[] magneticData = new float[3];
    private float[] orientationData = new float[3];
    private float azimuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public float getAzimuth() {
        return azimuth;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometerSensor);
        sensorManager.unregisterListener(this, magneticFieldSensor);
    }

    private void loadNewSensorData(SensorEvent event) {
        int sensorType = event.sensor.getType();
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            accelerometerData = event.values;
        } else if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticData = event.values;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        loadNewSensorData(event);
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magneticData);
        SensorManager.getOrientation(rotationMatrix, orientationData);

        azimuth = (float) Math.toDegrees(orientationData[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}
