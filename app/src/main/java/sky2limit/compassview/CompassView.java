package sky2limit.compassview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CompassView extends ImageView implements Animation.AnimationListener, CompassModeCallback {

    private static final int FAST_ANIMATION_DURATION = 200;
    private static final int DEGREES_360 = 360;
    private static final float CENTER = 0.5f;

    private Location userLocation;
    private Location objectLocation;
    private Bitmap directionBitmap;
    private int drawableResource;
    private float lastRotation;
    private CompassSensorManager compassSensorManager;

    public CompassView(Context context) {
        super(context);
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(CompassSensorManager compassSensorManager, Location userLocation,
                     Location objectLocation, int drawableResource) {
        this.compassSensorManager = compassSensorManager;
        this.userLocation = userLocation;
        this.objectLocation = objectLocation;
        this.drawableResource = drawableResource;

        start();
        compassSensorManager.addCompassCallback(this);
    }

    @Override
    public void start() {
        GeomagneticField geomagneticField = new GeomagneticField(
                (float) userLocation.getLatitude(),
                (float) userLocation.getLongitude(),
                (float) userLocation.getAltitude(), System.currentTimeMillis());

        float azimuth = compassSensorManager.getAzimuth();
        azimuth -= geomagneticField.getDeclination();

        float bearTo = userLocation.bearingTo(objectLocation);
        if (bearTo < 0) bearTo = bearTo + DEGREES_360;

        float rotation = bearTo - azimuth;
        if (rotation < 0) rotation = rotation + DEGREES_360;

        rotateImageView(this, drawableResource, rotation);
    }

    private void rotateImageView(ImageView compassView, int drawable, float currentRotate) {
        if (compassSensorManager.isSuspended()) return;

        if (directionBitmap == null) {
            directionBitmap = BitmapFactory.decodeResource(getResources(), drawable);
            compassView.setImageDrawable(new BitmapDrawable(getResources(), directionBitmap));
            compassView.setScaleType(ScaleType.CENTER);
        }

        currentRotate = currentRotate % DEGREES_360;
        float animToDegree = getShortestPathEndPoint(lastRotation, currentRotate);

        final RotateAnimation rotateAnimation = new RotateAnimation(lastRotation, animToDegree,
                Animation.RELATIVE_TO_SELF, CENTER, Animation.RELATIVE_TO_SELF, CENTER);
        rotateAnimation.setDuration(FAST_ANIMATION_DURATION);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(this);

        lastRotation = currentRotate;

        compassView.startAnimation(rotateAnimation);
    }

    private float getShortestPathEndPoint(float start, float end) {
        float delta = deltaRotation(start, end);
        float invertedDelta = invertedDelta(start, end);
        if (Math.abs(invertedDelta) < Math.abs(delta)) end = start + invertedDelta;
        return end;
    }

    private float deltaRotation(float start, float end) {
        return end - start;
    }

    private float invertedDelta(float start, float end) {
        float delta = end - start;
        if (delta < 0) end += DEGREES_360;
        else end += -DEGREES_360;
        return end - start;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        start();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}
