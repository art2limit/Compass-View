package garin.artemiy.compassview.example;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import garin.artemiy.compassview.library.CompassSensorActivity;
import garin.artemiy.compassview.library.CompassSensorManager;
import garin.artemiy.compassview.library.widget.CompassView;

/**
 * @author Ludovic Roland
 * @since 2016.07.16
 */
public final class DemoActivity
    extends CompassSensorActivity
{

  public static final class ObjectAdapter
      extends ArrayAdapter<Location>
  {

    private Location userLocation;

    private CompassSensorManager compassSensorManager;

    public ObjectAdapter(Context context, Location userLocation, CompassSensorManager compassSensorManager)
    {
      super(context, R.layout.list_item);
      this.userLocation = userLocation;
      this.compassSensorManager = compassSensorManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
      final ViewHolder viewHolder;

      if (convertView == null)
      {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.titleView = (TextView) convertView.findViewById(R.id.titleView);
        viewHolder.compassView = (CompassView) convertView.findViewById(R.id.compassView);

        convertView.setTag(viewHolder);
      }
      else
      {
        viewHolder = (ViewHolder) convertView.getTag();
      }

      final Location itemLocation = getItem(position);

      viewHolder.titleView.setText(itemLocation.getLatitude() + " - " + itemLocation.getLongitude());
      viewHolder.compassView.initializeCompass(compassSensorManager, userLocation, itemLocation, R.drawable.arrow);

      return convertView;
    }

  }

  public static final class ViewHolder
  {

    public TextView titleView;

    public CompassView compassView;

  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list);

    //Fake position : Paris
    final Location userLocation = new Location("");
    userLocation.setLatitude(48.856353);
    userLocation.setLongitude(2.354765);

    //Fake position : Brussels
    final Location originObjectLocation = new Location("");
    originObjectLocation.setLatitude(50.850169);
    originObjectLocation.setLongitude(4.350014);

    final ObjectAdapter objectAdapter = new ObjectAdapter(this, userLocation, compassSensorManager);
    objectAdapter.add(originObjectLocation);

    ((ListView) findViewById(R.id.listView)).setAdapter(objectAdapter);
  }

}
