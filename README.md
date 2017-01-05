
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FlowLayout-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4407)

# FlowLayout
A custom Layout able to display content, empty view, progress bar and network connectivity status.

1. [Why this layout](https://github.com/WassimBenltaief/FlowLayout#why-this-layout)
2. [Download](https://github.com/WassimBenltaief/FlowLayout#download)
3. [How](https://github.com/WassimBenltaief/FlowLayout#how)
4. [Connectivity Awareness](https://github.com/WassimBenltaief/FlowLayout#connectivity-awareness)
5. [Customize connectivity view (colors / text / layout)](https://github.com/WassimBenltaief/FlowLayout#customize-connectivity)
6. [Customize empty view](https://github.com/WassimBenltaief/FlowLayout#customize-empty-view)
7. [Customize progress view](https://github.com/WassimBenltaief/FlowLayout#customize-progress-bar-)
8. [Customize error view](https://github.com/WassimBenltaief/FlowLayout#customize-error-view-)
9. [PR](https://github.com/WassimBenltaief/FlowLayout#pr)
10. [TODOS](https://github.com/WassimBenltaief/FlowLayout#todos)

#Why this layout
A very common flow of an android view is :
- show a progress bar while fetching data from a remote service.
- if the resut is empty : show an empty view with a custom message and hide the progressbar.
- If not : show the content and hide the progressbar.
- showing a notification when phone is not connected or when action requires internet connection.

FlowLayout do all of this for you with a very few code in a customized way.

#Download

```groovy
compile 'com.beltaief.flowlayout:flowlayout:0.4.0'
```

#How

FlowLayout extends a FrameLayout. So add it to your view as a regular View and compose inside it :

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
    flowLayout.setMode(ViewMode.PROGRESS);
    
    // fetch data
    ...
    
    // assign result to the view
    if (data.isEmpty()) {
      flowLayout.setMode(ViewMode.EMPTY);
    } else {
      flowLayout.setMode(ViewMode.CONTENT);
      // show the data in the view
      ...
    }
    
    ... 
    // or set error view
    flowLayout.setMode(ViewMode.ERROR);
}
```
[content]: https://github.com/WassimBenltaief/FlowLayout/blob/master/images/content_loading.gif

[empty]: https://github.com/WassimBenltaief/FlowLayout/blob/master/images/empty_example.gif

| content mode  | empty mode    |   
|:-------------:|:-------------:|
| ![][content]  | ![][empty]    |


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

to customize color and/or  text in the default connectivity view
```xml
app:isConnectivityAware="true" // true, false
app:connectedText="@string/connectedTextStringResource" // text to show when connected
app:connectedTextColor="@color/connectedTextColorResource" // text color
app:connectedBackground="@color/connectedBackgroundColorResource" // background color
app:disconnectedText="@string/disconnectedTextStringResource" // text to show when disconnected
app:disconnectedTextColor="@color/disconnectedTextColorResource" // text color
app:disconnectedBackground="@color/disconnectedBackgroundColorResource" // background color
```

or i you want to provide your own layout content :
```xml
app:connectedLayout="@layout/custom_connected_layout"
app:disconnectedLayout="@layout/custom_disconnected_layout"
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
flowLayout.setConnectedBackground(R.color.connected_color);

flowLayout.setDisconnectedText(R.string.text_disconnected);
flowLayout.setDisconnectedTextColor(R.color.disconnected_color);
flowLayout.setDisconnectedBackground(R.color.disconnected_color);

```

### Screenshot


![](https://github.com/WassimBenltaief/FlowLayout/blob/master/images/custom_connectivity.gif)

# Customize Empty View
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

# Customize Progress bar :

if you want to provide your own progress view :

```xml
app:progressLayout="@layout/custom_progress"
```


# Customize Error View :

if you want to provide your own error view :

```xml
app:errorLayout="@layout/custom_error"
```

# PR
Open.

# TODOS

- <del>add customization to progress-bar</del> Done
- <del>add error view</del> Done
- <del>use @IntDef for setting view modes</del> Done
- Use ViewStub
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
