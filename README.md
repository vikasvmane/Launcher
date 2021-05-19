# Launcher
Android launcher app

- Contains appDataSdk module which exposes app list data to be shown in the sample app

## Usage
### addDataSdk
To get Apps from you device
```kotlin
class AppDataProvider //Main sdk class from appDataSdk module exposes two data points

fun fetchAppList(packageManager: PackageManager) //fetches applist
var appsList //Exposes app list data to the observer. Gets updated from fetchAppList
```
To get updates of install/uninstall app updates 
```kotlin
class PackageChangeReceiver : BroadcastReceiver() //Receives install/uninstall package update
```
To use PackageChangeReceiver add <receiver> it into your apps AndroidManifest.xml
```kotlin
<receiver android:name="com.vikasmane.appdatasdk.PackageChangeReceiver" android:exported="true">
            <intent-filter android:priority="999">
                <action android:name="android.intent.action.PACKAGE_FULLY_REMOVED"/>
                <action android:name="android.intent.action.PACKAGE_CHANGED" />
                <data android:scheme="package"/>
            </intent-filter>
        </receiver>
```
The receiver auto updates AppDataProvider's appsList

## Sample app screenshot
<img src="https://user-images.githubusercontent.com/7870133/118861222-e3042800-b8f9-11eb-95d2-14700ef333e7.png" width="400" height="790">
