package com.vikasmane.appdatasdk

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData

object AppDataSingleton {
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
                versionCode = packageManager.getPackageInfo(
                    resolveInfo.activityInfo.packageName,
                    0
                ).versionCode.toString(),
                versionName = packageManager.getPackageInfo(
                    resolveInfo.activityInfo.packageName,
                    0
                ).versionName
            )
            loadList.add(app)
        }
        loadList.sortBy { it.label.toString() }
        appsList.value = loadList
    }
}
