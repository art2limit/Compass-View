# Android Compass View

A simple compass view 3D you can integrate into your Android app.

This version of the library has one dependency : `com.android.support:appcompat-v7:23.4.0`.

This version of the library requires Android v9+ to work.

![Sample app screenshot](http://i57.tinypic.com/osa61g.png)


## Usage

For a full example see the `sample` app in the repository.

### As Library Project

Check out this repository and add it as a library project.

Import the project into your favorite IDE and add `android.library.reference.1=/path/to/Compass-View/library` to your `project.properties`.

### Generate an aar file

Library releases are not available on Maven Central or JCenter but you can generate an aar file by your owned :

```console
gradle clean library:assembleRelease
```

or

```console
gradle -b library/build.gradle clean assembleRelease
```

### Generate a jar file

If you prefer a jar file instead of an aar file, you can also generate an it by your owned :

```console
gradle -b library/build.gradle clean assembleRelease makeJar
```

### Layout

You need to declare the `CompassView` directly into your layout.

```java
<garin.artemiy.compassview.library.widget.CompassView
  android:id="@+id/compassView"
  android:layout_height="wrap_content"
  android:layout_width="wrap_content"
/>
```

### The `CompassSensorManager`

In order to work correctly, the `CompassView` needs a `CompassSensorManager`.

Your activities or fragments can automatically have one by extending the `CompassSensorActivity` class or the `CompassSensorFragment` class.

For an `Activity` :

```java
public final class MyActivity
    extends CompassSensorActivity
{
  //...
}
```

For a `Fragment` :

```java
public final class MyFragment
    extends CompassSensorFragment
{
  //...
}
```

Alternatively, you can add one to your activities or fragments. **Warning :** you need to respect the `CompassSensorManager` lifecycle :

For an `Activity` :

```java
public final class MyActivity
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
```

For a `Fragment` :

```java
public final class MyFragment
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
```

### Initialize the `CompassView`

In your adapter, or ordinary view you need initialize the view :

```java
CompassView compassView = (CompassView) view.findViewById(R.id.compassView);
compassView.initializeCompass(compassSensorManager, userLocation, objectLocation, R.drawable.arrow)
```

## License

```
Copyright (C) 2013 Artemiy Garin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
