package com.gamebit.flickrbrowserapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(),
    GetRawData.OnDownloadComplete,
    GetFlickerJsonData.OnDataAvailable,
    RecyclerItemClickListener.OnRecyclerClickListener {

    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolbar(false)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this, recycler_view, this))
        recycler_view.adapter = flickrRecyclerViewAdapter

        val url = createUri ("https://api.flickr.com/services/feeds/photos_public.gne", "android,oreo","en-us", true)
        val getRawData = GetRawData(this)
        getRawData.execute(url)
    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, ".onItemClick")
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, ".onItemLongClick")
        val photo = flickrRecyclerViewAdapter.getPhoto(position)
        if (photo != null) {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }

    private fun createUri(baseURL: String, searchCriteria: String, lang: String, matchAll: Boolean): String {
        Log.d(TAG, ".createUri starts")

        return Uri.parse(baseURL)
                .buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
               startActivity(Intent(this, SearchActivity::class.java))
               true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete called")

            val getFlickrJsonData = GetFlickerJsonData(this)
            getFlickrJsonData.execute(data)
        } else {
            Log.d(TAG, "onDownloadComplete failed with $status. Error Message is $data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, ".onDataAvailable called")
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d(TAG, ".onDataAvailable ends")
    }

    override fun onError(exception: Exception) {
        Log.d(TAG, ".onError called with ${exception.message}")
    }
}














