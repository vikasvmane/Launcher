package com.vikasmane.launcher

import com.vikasmane.appdatasdk.AppData

/**
 * Callback for recyclerview item click
 */
interface AppIconClickListener {
    fun onAppIconClick(appData: AppData)
}