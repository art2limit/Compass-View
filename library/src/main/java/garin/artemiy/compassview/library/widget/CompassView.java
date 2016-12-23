package garin.artemiy.compassview.library.widget;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import garin.artemiy.compassview.library.BuildConfig;
import garin.artemiy.compassview.library.CompassSensorManager;

/**
 * @author Artemiy Garin
 * @since 26.11.13
 */
public class CompassView
    extends ImageView
    implements Animation.AnimationListener
{

  private static final String TAG = CompassView.class.getSimpleName();

  private static final int FAST_ANIMATION_DURATION = 200;

  private static final int DEGREES_360 = 360;

  private static final float CENTER = 0.5f;

  private Context context;

  private Location userLocation;

  private Location objectLocation;

  private Bitmap directionBitmap;

  private int drawableResource;

  private float lastRotation;

  private CompassSensorManager compassSensorManager;

  @SuppressWarnings("unused")
  public CompassView(Context context)
  {
    super(context);
    init(context);
  }

  @SuppressWarnings("unused")
  public CompassView(Context context, AttributeSet attrs)
  {
    super(context, attrs);
    init(context);
  }

  private void init(Context context)
  {
    this.context = context;

    if (isDeviceCompatible(context) == false)
    {
      setVisibility(View.GONE);
    }
  }

  public void initializeCompass(CompassSensorManager compassSensorManager, Location userLocation,
      Location objectLocation, int drawableResource)
  {
    if (isDeviceCompatible(context) == true)
    {
      this.compassSensorManager = compassSensorManager;
      this.userLocation = userLocation;
      this.objectLocation = objectLocation;
      this.drawableResource = drawableResource;
      startRotation();
    }
  }

  private void startRotation()
  {
    final GeomagneticField geomagneticField = new GeomagneticField((float) userLocation.getLatitude(), (float) userLocation.getLongitude(), (float) userLocation.getAltitude(), System.currentTimeMillis());

    float azimuth = compassSensorManager.getAzimuth();
    azimuth -= geomagneticField.getDeclination();

    float bearTo = userLocation.bearingTo(objectLocation);
    if (bearTo < 0)
    {
      bearTo = bearTo + DEGREES_360;
    }

    float rotation = bearTo - azimuth;
    if (rotation < 0)
    {
      rotation = rotation + DEGREES_360;
    }

    rotateImageView(this, drawableResource, rotation);

    if (BuildConfig.DEBUG)
    {
      Log.d(CompassView.TAG, String.valueOf(rotation));
    }
  }

  @SuppressWarnings("ConstantConditions")
  private void rotateImageView(ImageView compassView, int drawable, float currentRotate)
  {
    if (directionBitmap == null)
    {
      directionBitmap = BitmapFactory.decodeResource(getResources(), drawable);
      final Animation fadeInAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
      fadeInAnimation.setAnimationListener(this);
      compassView.startAnimation(fadeInAnimation);
      compassView.setImageDrawable(new BitmapDrawable(getResources(), directionBitmap));
      compassView.setScaleType(ScaleType.CENTER);
    }
    else
    {
      currentRotate = currentRotate % DEGREES_360;
      int animationDuration = FAST_ANIMATION_DURATION;

      float animToDegree = getShortestPathEndPoint(lastRotation, currentRotate);

      final RotateAnimation rotateAnimation = new RotateAnimation(lastRotation, animToDegree, Animation.RELATIVE_TO_SELF, CENTER, Animation.RELATIVE_TO_SELF, CENTER);
      rotateAnimation.setInterpolator(new LinearInterpolator());
      rotateAnimation.setDuration(animationDuration);
      rotateAnimation.setFillAfter(true);
      rotateAnimation.setAnimationListener(this);

      lastRotation = currentRotate;

      compassView.startAnimation(rotateAnimation);
    }
  }

  /**
   * Computes the shortest path delta for reaching the same "end" degree by looking both direction around a circle. For example, rather than traveling from degree 0 to 355 around a circle, it would be shorter to go from degree 0 to -5.
   * @param start
   * @param end
   * @return An updated "end" degree that's based on the shortest path to reach it.
   */
  private float getShortestPathEndPoint(float start, float end) {
    float delta = deltaRotation(start, end);
    float invertedDelta = invertedDelta(start, end);

    if (Math.abs(invertedDelta) < Math.abs(delta)) {
      end = start + invertedDelta;
    }

    return end;
  }

  /**
   * Calculates the standard delta (end - start). Note: This is how the delta is calculated by the RotateAnimation()
   * @param start
   * @param end
   * @return The value of (end - start)
   */
  private float deltaRotation(float start, float end) {
    return end - start;
  }

  /**
   *
   * @param start the starting degree
   * @param end the ending degree
   * @return Rather than calculating a standard delta (end - start), this function calculates the distance it would take to reach "end" going the opposite direction around a circle.
   */
  private float invertedDelta(float start, float end) {
    final float delta = end - start;
    //adjust "end" so that we go the opposite direction around the circle
    if (delta < 0) {
      //delta is going backwards, so lets adjust "end" to go forward
      end += DEGREES_360;
    } else {
      //delta is going forwards, so lets adjust "end" to go backward
      end += -DEGREES_360;
    }

    //calculate and return the delta going the opposite direction
    return end - start;
  }

  @Override
  public void onAnimationStart(Animation animation)
  {

  }

  @Override
  public void onAnimationEnd(Animation animation)
  {
    startRotation();
  }

  @Override
  public void onAnimationRepeat(Animation animation)
  {

  }

  public static boolean isDeviceCompatible(Context context)
  {
    return context.getPackageManager() != null && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) == true && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS) == true;
  }

}
