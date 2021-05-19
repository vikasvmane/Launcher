package com.vikasmane.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.vikasmane.appdatasdk.AppData
import com.vikasmane.appdatasdk.AppDataSingleton
import java.util.*

class MainActivity : AppCompatActivity(), AppIconClickListener {
    private var appList: MutableList<AppData> = mutableListOf()
    private lateinit var adapter: AppListRecyclerViewAdapter
    private lateinit var viewModel: AppListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerViewAppList = findViewById<RecyclerView>(R.id.recyclerView)
        val searchView = findViewById<SearchView>(R.id.searchView)
        adapter =
            AppListRecyclerViewAdapter(appList = appList, appIconClickListener = this)
        recyclerViewAppList.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(AppListViewModel::class.java)
        setObservers()
        //Commands the viewModel to fetch the app list data
        viewModel.fetchAppListData()

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
    }

    private fun setObservers() {
        //App list data is exposed using liveData. Adds a observer to intercept the change
        viewModel.getAppList().observe(this, {
            appList = it
            adapter.updateList(appList)
            adapter.notifyDataSetChanged()
        })
    }

    /**
     * Propagates app list recyclerview item click and launches the app based on package name
     */
    override fun onAppIconClick(appData: AppData) {
        val launchIntent: Intent? = packageManager
            .getLaunchIntentForPackage(appData.packageName.toString())
        startActivity(launchIntent)
    }

    /**
     * [filter] is used to search elements within this [RecyclerView].
     */
    fun filter(text: String) {
        val appList: MutableList<AppData> = mutableListOf()
        //If text is empty show add the complete list
        if (text.isEmpty()) {
            appList.addAll(this.appList)
        } else {
            //Finds the apps matching name with the text
            for (item in this.appList) {
                if (item.label.toString().lowercase(Locale.ROOT)
                        .contains(text.lowercase(Locale.ROOT))
                )
                    appList.add(item)
            }
        }
        //Updates the recyclerView with search list options
        adapter.updateList(appList)
        adapter.notifyDataSetChanged()
    }
}
