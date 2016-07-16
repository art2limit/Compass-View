package garin.artemiy.compassview.example.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import garin.artemiy.compassview.example.DemoActivity.ObjectAdapter;
import garin.artemiy.compassview.example.R;
import garin.artemiy.compassview.library.fragment.CompassSensorFragment;

/**
 * @author Ludovic Roland
 * @since 2016.07.16
 */
public final class DemoFragment
    extends CompassSensorFragment
{

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    final View rootView = inflater.inflate(R.layout.list, container, false);

    //Fake position : Paris
    final Location userLocation = new Location("");
    userLocation.setLatitude(48.856353);
    userLocation.setLongitude(2.354765);

    //Fake position : Brussels
    final Location originObjectLocation = new Location("");
    originObjectLocation.setLatitude(50.850169);
    originObjectLocation.setLongitude(4.350014);

    final ObjectAdapter objectAdapter = new ObjectAdapter(getContext(), userLocation, compassSensorManager);
    objectAdapter.add(originObjectLocation);

    ((ListView) rootView.findViewById(R.id.listView)).setAdapter(objectAdapter);

    return rootView;
  }

}
