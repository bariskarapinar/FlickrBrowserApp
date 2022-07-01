package com.gamebit.flickrbrowserapp

import android.os.Bundle

class PhotoDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activateToolbar(true)

    }
}