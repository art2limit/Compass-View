package garin.artemiy.compassview.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import garin.artemiy.compassview.R;

/**
 * Author: Artemiy Garin
 * Date: 26.11.13
 */
public class CompassView extends ImageView {

    private static final long ANIMATION_DURATION = 250;

    private Location userLocation;
    private Location objectLocation;
    private Bitmap directionBitmap;
    private Context context;

    private float lastRotation;

    @SuppressWarnings("unused")
    public CompassView(Context context) {
        super(context);
        init(context);
    }

    @SuppressWarnings("unused")
    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        if (((Activity) context) instanceof FragmentActivity &&
                ((FragmentActivity) context) instanceof CompassSensorsActivity) {
            this.context = context;
        } else {
            throw new RuntimeException("Your activity must extends from CompassSensorsActivity");
        }
    }

    public void setLocation(Location userLocation, Location objectLocation) {
        this.userLocation = userLocation;
        this.objectLocation = objectLocation;
        startRotation();
    }

    private void startRotation() {

        GeomagneticField geomagneticField = new GeomagneticField(
                (float) objectLocation.getLatitude(), (float) objectLocation.getLongitude(),
                (float) objectLocation.getAltitude(), System.currentTimeMillis());

        float azimuth = ((CompassSensorsActivity) context).getAzimuth();
        azimuth -= geomagneticField.getDeclination();

        float bearTo = objectLocation.bearingTo(userLocation);

        if (bearTo < 0) {
            bearTo = bearTo + 360;
        }

        float rotation = bearTo - azimuth;

        if (rotation < 0) {
            rotation = rotation + 360;
        }

        rotateImageView(this, R.drawable.arrow, rotation);
    }

    private void rotateImageView(ImageView compassView, int drawable, float currentRotate) {

        if (directionBitmap == null) {

            directionBitmap = BitmapFactory.decodeResource(getResources(), drawable);
            Animation fadeInAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            fadeInAnimation.setAnimationListener(new CustomAnimationListener());
            compassView.startAnimation(fadeInAnimation);
            compassView.setImageDrawable(new BitmapDrawable(getResources(), directionBitmap));
            compassView.setScaleType(ScaleType.CENTER);

        } else {

            currentRotate = currentRotate % 360;

            RotateAnimation rotateAnimation = new RotateAnimation(lastRotation, currentRotate,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setDuration(ANIMATION_DURATION);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setAnimationListener(new CustomAnimationListener());

            lastRotation = currentRotate;

            compassView.startAnimation(rotateAnimation);
        }

    }

    private class CustomAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            startRotation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

}
