package com.vikasmane.appdatasdk

import android.graphics.drawable.Drawable

/**
 * AppData is the used to showcase data in the recyclerview
 */
data class AppData(
    val label: CharSequence,
    val packageName: CharSequence,
    val icon: Drawable,
    val launcherActivity: String,
    val versionCode: String,
    val versionName: String
)
