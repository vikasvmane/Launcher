package com.vikasmane.appdatasdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AppEventListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // fetching package names from extras
        val packageName = intent?.data?.encodedSchemeSpecificPart
        when (intent?.action) {
            Intent.ACTION_PACKAGE_FULLY_REMOVED, Intent.ACTION_PACKAGE_REMOVED -> Toast.makeText(
                context,
                "${packageName.toString()} removed",
                Toast.LENGTH_SHORT
            ).show()
        }
        context?.packageManager?.let { AppDataSingleton.fetchAppList(it) }
    }
}