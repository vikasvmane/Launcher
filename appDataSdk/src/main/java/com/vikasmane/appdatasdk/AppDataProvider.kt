package com.vikasmane.appdatasdk

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import androidx.lifecycle.MutableLiveData

class AppDataProvider {

    companion object {
        val instance = AppDataProvider()
    }
    //Exposes app list data to the observer. Gets updated from fetchAppList
    var appsList = MutableLiveData<MutableList<AppData>>()

    /**
     * Fetches list of apps and adds to liveData object
     */
    fun fetchAppList(packageManager: PackageManager) {
        val loadList = mutableListOf<AppData>()

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in allApps) {
            val app = AppData(
                label = resolveInfo.loadLabel(packageManager),
                packageName = resolveInfo.activityInfo.packageName,
                icon = resolveInfo.activityInfo.loadIcon(packageManager),
                launcherActivity = resolveInfo.activityInfo.name,
                versionCode = getVersionCode(packageManager, resolveInfo),
                versionName = getVersionName(packageManager, resolveInfo)
            )
            loadList.add(app)
        }
        loadList.sortBy { it.label.toString() }
        appsList.value = loadList
    }

    /**
     * Returns versionCode. As packageInfo.versionCode is deprecated.So packageInfo.longVersionCode
     * to be used(requires API 28)
     */
    private fun getVersionCode(packageManager: PackageManager, resolveInfo: ResolveInfo): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageManager.getPackageInfo(
                resolveInfo.activityInfo.packageName,
                0
            ).longVersionCode.toString()
        } else {
            packageManager.getPackageInfo(
                resolveInfo.activityInfo.packageName,
                0
            ).versionCode.toString()
        }
    }

    private fun getVersionName(packageManager: PackageManager, resolveInfo: ResolveInfo): String {
        return packageManager.getPackageInfo(
            resolveInfo.activityInfo.packageName,
            0
        ).versionName
    }
}
