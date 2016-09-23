# FlowLayout
A custom Layout able to display content, empty view, progress bar and network connectivity status.

#Why this layout
A very common flow of an android view is :
- show a progress bar while fetching data from a remote service.
- if the resut is empty : show an empty view with a custom message and hide the progressbar.
- If not : show the content and hide the progressbar.
- showing a notification when phone is not connected or when action requires internet connection.

FlowLayout do all of this for you with a very few code in a customized way.

#How

FlowLayout extends a RelativeLayout. So add it to your view as a regular RelativeLayout :

in your ```activity_layout.xml``` :

```xml
<com.beltaief.flowlayout.FlowLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/flow_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    // content goes here

</com.beltaief.flowlayout.FlowLayout>
```

then in your Activity/Fragment :
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout);
    
    // lookup for the layout
    FlowLayout flowLayout = (FlowLayout)findViewById(R.id.flow_layout);
    
    //set mode progress
    flowLayout.setMode(FlowLayout.MODE.PROGRESS);
    
    // fetch data
    ...
    
    // assign result to the view
    if (data.isEmpty()) {
      flowLayout.setMode(FlowLayout.MODE.EMPTY);
    } else {
      flowLayout.setMode(FlowLayout.MODE.CONTENT);
    }
}
```
[content]: https://github.com/WassimBenltaief/FlowLayout/blob/master/images/content_loading.gif

[empty]: https://github.com/WassimBenltaief/FlowLayout/blob/master/images/empty_example.gif

|               | progress then content       | progress then empty         |   
| ------------- |:-------------:|:-------------:|
| Screenshot    | ![][content]  | ![][empty]    |


#Connectivity awareness
If you would like the view to notify when connectivity status changes, then add this attribute to the XML declaration of FlowLayout :

```xml 
app:isConnectivityAware="true"
```

or programmatically

```java
flowLayout.setConnectivityAware(true);
```

And the view will be able to notify whenever the status of the internet connection changes :

![](https://github.com/WassimBenltaief/FlowLayout/blob/master/images/connectivity.gif)

# Customize connectivity

FlowLayout comes with several attributes to help customizing the connectivity view.
Add attributes to your xml to apply customization :

```xml
app:isConnectivityAware="true" // true, false
app:connectedText="@string/connectedTextStringResource" // text to show when connected
app:connectedTextColor="@color/connectedTextColorResource" // text color
app:connectedBackground="@color/connectedBackgroundColorResource" // background color
app:disconnectedText="@string/disconnectedTextStringResource" // text to show when disconnected
app:disconnectedTextColor="@color/disconnectedTextColorResource" // text color
app:disconnectedBackground="@color/disconnectedBackgroundColorResource" // background color
app:emptyLayout="@layout/emptyLayoutReference" // custom empty view layout
```

### Example :

```xml
<com.beltaief.flowlayout.FlowLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/reveLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:isConnectivityAware="true"
    app:connectedText="@string/connected_message"
    app:connectedTextColor="@color/white"
    app:connectedBackground="@color/blue_light"
    app:disconnectedBackground="@color/colorAccent">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</com.beltaief.flowlayout.FlowLayout>

```

or programmatically :

```java
flowLayout.setConnectivityAware(true);

flowLayout.setConnectedText(R.string.connected_message);
flowLayout.setConnectedTextColor(R.color.connected_color);

flowLayout.setDisconnectedText(R.string.text_disconnected);
flowLayout.setDisconnectedTextColor(R.color.disconnected_color);

```

### Screenshot


![](https://github.com/WassimBenltaief/FlowLayout/blob/master/images/custom_connectivity.gif)

# Custom Empty View
There's two ways to customize the empty view :

1. custom text and color :
To override the default text and colors, add a reference to your custom text/color in xml

```xml
app:emptyLayout="@layout/custom_empty"
app:emptyText="@string/text_empty"
app:emptyTextColor="@color/text_empty_color"
```
or programmatically :

```java
flowLayout.setEmptyLayout(R.layout.layout_empty);
flowLayout.setEmptyText(R.string.text_empty); // Do not combine with .setEmptyLayout()
flowLayout.setEmptyTextColor(R.color.text_empty_color); // Do not combine with .setEmptyLayout()
```

2. Custom layout :
Override the empty layout by providing a reference in xml :

```xml
app:emptyLayout="@layout/custom_empty"
```
or programmatically :

```java
flowLayout.setEmptyLayout(R.layout.layout_empty);
```

### Screenshot
![](https://github.com/WassimBenltaief/FlowLayout/blob/master/images/custom_empty.gif)


# PR
Open.

# TODOS

- add customization to progress-bar
- add error view handling
- any other idea is welcome

#Licence
The MIT License (MIT)

Copyright (c) 2014 Neil Stoker

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
