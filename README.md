![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg) [![](https://jitpack.io/v/airtriangle/Compass-View.svg)](https://jitpack.io/#airtriangle/Compass-View)

## Screen-gifs
![](static/arrows-preview.gif)

## Important
For correct library working arrow should be see straight up like this ⬆

## Import
In your activity or fragment init `CompassSensorManager` and create `CompassView` like this:
```java
    private CompassSensorManager compassSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        compassSensorManager = new CompassSensorManager(this);

        CompassView compassView = new CompassView(this);
        compassView.init(compassSensorManager, location1, location2, R.drawable.icon_arrow);
    }

    @Override
    protected void onResume() {
        ...
        compassSensorManager.onResume();
    }

    @Override
    protected void onPause() {
        ...
        compassSensorManager.onPause();
    }
```
Don't forget call onResume and onPause, if you don't battery will be cry.
