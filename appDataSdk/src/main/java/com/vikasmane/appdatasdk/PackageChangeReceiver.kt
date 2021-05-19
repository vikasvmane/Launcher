package com.vikasmane.appdatasdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.widget.Toast

/**
 * Receives App install and uninstall events
 * Notifies device using toast and refreshes appdata list
 */
class PackageChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // fetching package names from extras
        val packageName = intent?.data?.encodedSchemeSpecificPart
        when (intent?.action) {
            Intent.ACTION_PACKAGE_FULLY_REMOVED -> Toast.makeText(
                context,
                context?.getString(
                    R.string.app_removed,
                    getAppName(context, packageName.toString())
                ),
                Toast.LENGTH_SHORT
            ).show()
            Intent.ACTION_PACKAGE_CHANGED -> Toast.makeText(
                context,
                context?.getString(
                    R.string.app_added,
                    getAppName(context, packageName.toString())
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
        context?.packageManager?.let { AppDataProvider.instance.fetchAppList(it) }
    }

    /**
     * Returns app name based on packageName. It returns packageName in case name not found
     */
    private fun getAppName(context: Context?, packageName: String): String {
        val packageManager: PackageManager? =
            context?.packageManager
        val applicationInfo: ApplicationInfo? = try {
            packageManager?.getApplicationInfo(packageName, 0)
        } catch (exception: PackageManager.NameNotFoundException) {
            null
        }
        return (if (applicationInfo != null) packageManager?.getApplicationLabel(applicationInfo) else packageName) as String
    }
}