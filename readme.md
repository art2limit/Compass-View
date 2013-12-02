<h3>Version — 0.9</h3>
<h3>Minimum SDK — 2.2+</h3>

<h2>Install</h2>
You may import src from project (need delete example folder) or <a href="https://github.com/kvirair/Compass-View-Library/releases">download jar</a> (recommended)
<br><br>

<h2>Quick start</h2>
— Extend your activity by CompassSensorsActivity, for example:

```
public class YourActivity extends CompassSensorsActivity {
    ...
}
```

— Write your view in xml:
```
<garin.artemiy.compassview.library.CompassView
    android:id="@+id/compassView"
    android:layout_height="wrap_content"
    android:layout_width="wrap_content"/>
```

— In your adapter, or ordinary view you need initialize:
```
CompassView compassView = (CompassView) view.findViewById(R.id.compassView);
compassView.initializeCompass(userLocation, objectLocation, R.drawable.arrow);
```

**That's all!**

<h2>License</h2>
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