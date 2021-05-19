package com.vikasmane.launcher

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vikasmane.appdatasdk.AppData

class AppListRecyclerViewAdapter(
    private var appList: MutableList<AppData>,
    private val appIconClickListener: AppIconClickListener
) : RecyclerView.Adapter<AppListRecyclerViewAdapter.AppViewHolder>() {
    private lateinit var context: Context

    inner class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appTitle: TextView = view.findViewById(R.id.titleTextView)
        val packageName: TextView = view.findViewById(R.id.packageNameTextView)
        val activityName: TextView = view.findViewById(R.id.activityNameTextView)
        val versionCode: TextView = view.findViewById(R.id.VersionCodeTextView)
        val versionName: TextView = view.findViewById(R.id.versionNameTextView)
        val appIcon: ImageView = view.findViewById(R.id.appIconImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_list_item, parent, false)
        context = parent.context
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val item = appList[position]
        holder.appTitle.text = item.label.toString()
        holder.appIcon.setImageDrawable(item.icon)
        holder.packageName.text = getHtmlText(context.getString(R.string.package_name, item.packageName))
        holder.activityName.text = getHtmlText(context.getString(R.string.activity_name, item.launcherActivity))
        holder.versionName.text = item.versionName
        holder.versionCode.text = item.versionCode
        holder.itemView.setOnClickListener {
            appIconClickListener.onAppIconClick(item)
        }
    }

    override fun getItemCount() = appList.size

    fun updateList(appList: MutableList<AppData>) {
        this.appList = appList
    }

    /**
     * To render html formatting in the text
     */
    private fun getHtmlText(data : String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(data, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(data)
        }
    }
}