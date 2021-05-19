package com.vikasmane.launcher

import android.content.pm.PackageManager
import com.vikasmane.appdatasdk.AppDataSingleton

object AppListRepository {
    /**
     * Fetches the list of apps from the system
     */
    fun fetchAppListData(packageManager: PackageManager) {
        AppDataSingleton.fetchAppList(packageManager)
    }

    /**
     * Exposes liveData object of type MutableList<AppData>
     */
    fun getAppList() = AppDataSingleton.appsList
}