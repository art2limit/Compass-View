package garin.artemiy.compassview.library;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Author: Artemiy Garin
 * Date: 27.11.13
 */
public class CompassUtility {

    public static boolean isDeviceCompatible(Context context) {
        return context.getPackageManager() != null &&
                context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) &&
                context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
    }

}
