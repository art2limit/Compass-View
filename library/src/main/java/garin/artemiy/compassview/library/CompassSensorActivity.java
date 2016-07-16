package garin.artemiy.compassview.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Artemiy Garin
 * @since 26.11.13
 */
public class CompassSensorActivity
    extends AppCompatActivity
{

  protected CompassSensorManager compassSensorManager;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    compassSensorManager = new CompassSensorManager(this);
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    compassSensorManager.onResume();
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    compassSensorManager.onPause();
  }

}
