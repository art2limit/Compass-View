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
import garin.artemiy.compassview.R;
import garin.artemiy.compassview.library.CompassSensorsActivity;
import garin.artemiy.compassview.library.CompassView;
import java.util.UUID;

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

      viewHolder.titleView.setText(UUID.randomUUID().toString());
      viewHolder.compassView.initializeCompass(userLocation, getItem(position), R.drawable.arrow);

      return convertView;
    }

  }

  private static final class ViewHolder
  {

    public TextView titleView;

    public CompassView compassView;

  }

  private static final double DELTA = 0.5;

  private Location originObjectLocation;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Fake position : Paris
    final Location userLocation = new Location("");
    userLocation.setLatitude(48.857257);
    userLocation.setLongitude(2.333333);

    //Fake position : Brussels
    originObjectLocation = new Location("");
    originObjectLocation.setLatitude(50.850402);
    originObjectLocation.setLongitude(4.349983);

    final ObjectAdapter objectAdapter = new ObjectAdapter(this, userLocation);

    addTestData(objectAdapter);

    ((ListView) findViewById(R.id.listView)).setAdapter(objectAdapter);
  }

  private void addTestData(ObjectAdapter objectAdapter)
  {
    final Location testObject1 = getTestObject();
    testObject1.setLongitude(testObject1.getLongitude() - DELTA);
    testObject1.setLatitude(testObject1.getLatitude() - DELTA);
    objectAdapter.add(testObject1);
  }

  private Location getTestObject()
  {
    final Location objectLocation = new Location("");
    objectLocation.setLatitude(originObjectLocation.getLatitude());
    objectLocation.setLongitude(originObjectLocation.getLongitude());
    return objectLocation;
  }

}
