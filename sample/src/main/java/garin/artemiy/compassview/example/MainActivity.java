package garin.artemiy.compassview.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import garin.artemiy.compassview.library.CompassSensorActivity;

public final class MainActivity
    extends CompassSensorActivity
    implements OnItemClickListener
{

  private final static String[] menuItems = new String[] { "CompassSensorActivity", "CompassSensorFragment" };

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list);

    final ListView listView = (ListView) findViewById(R.id.listView);
    listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItems));
    listView.setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id)
  {
    if (position == 0)
    {
      startActivity(new Intent(this, DemoActivity.class));
    }
    else if (position == 1)
    {
      startActivity(new Intent(this, DemoFragmentActivity.class));
    }
  }

}
