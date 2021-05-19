package com.vikasmane.launcher

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AppListViewModel(application: Application) : AndroidViewModel(application) {

    fun fetchAppListData() {
        AppListRepository.fetchAppListData(getApplication<Application>().packageManager)
    }

    fun getAppList() = AppListRepository.getAppList()
}