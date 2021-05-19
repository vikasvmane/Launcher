package com.vikasmane.launcher

import android.content.pm.PackageManager
import com.vikasmane.appdatasdk.AppDataProvider

object AppListRepository {
    /**
     * Fetches the list of apps from the system
     */
    fun fetchAppListData(packageManager: PackageManager) {
        AppDataProvider.instance.fetchAppList(packageManager)
    }

    /**
     * Exposes liveData object of type MutableList<AppData>
     */
    fun getAppList() = AppDataProvider.instance.appsList
}