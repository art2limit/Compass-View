package garin.artemiy.compassview.library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import garin.artemiy.compassview.library.CompassSensorManager;

/**
 * @author Ludovic Roland
 * @since 2016.07.16
 */
public class CompassSensorFragment
    extends Fragment
{

  protected CompassSensorManager compassSensorManager;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    compassSensorManager = new CompassSensorManager(getActivity());
  }

  @Override
  public void onResume()
  {
    super.onResume();
    compassSensorManager.onResume();
  }

  @Override
  public void onPause()
  {
    super.onPause();
    compassSensorManager.onPause();
  }

}
