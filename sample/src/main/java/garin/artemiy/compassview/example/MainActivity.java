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
import garin.artemiy.compassview.library.CompassSensorsActivity;
import garin.artemiy.compassview.library.CompassView;

public final class MainActivity
    extends CompassSensorsActivity
{

  private static final class ObjectAdapter
      extends ArrayAdapter<Location>
  {

    private Location userLocation;

    public ObjectAdapter(Context context, Location userLocation)
    {
      super(context, R.layout.list_item);
      this.userLocation = userLocation;
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

      final Location item = getItem(position);

      viewHolder.titleView.setText(item.getLatitude() + " - " + item.getLongitude());
      viewHolder.compassView.initializeCompass(userLocation, item, R.drawable.arrow);

      return convertView;
    }

  }

  private static final class ViewHolder
  {

    public TextView titleView;

    public CompassView compassView;

  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Fake position : Paris
    final Location userLocation = new Location("");
    userLocation.setLatitude(48.856353);
    userLocation.setLongitude(2.354765);

    //Fake position : Brussels
    final Location originObjectLocation = new Location("");
    originObjectLocation.setLatitude(50.850169);
    originObjectLocation.setLongitude(4.350014);

    final ObjectAdapter objectAdapter = new ObjectAdapter(this, userLocation);
    objectAdapter.add(originObjectLocation);

    ((ListView) findViewById(R.id.listView)).setAdapter(objectAdapter);
  }

}
