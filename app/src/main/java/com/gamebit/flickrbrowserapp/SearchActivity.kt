package com.gamebit.flickrbrowserapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView

class SearchActivity : BaseActivity() {
    private val TAG = "SearchActivity"
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activateToolbar(true)
        Log.d(TAG, ".onCreate ends")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }
}