package com.elchuy.podplay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.elchuy.podplay.R
import com.elchuy.podplay.repository.ItunesRepo
import com.elchuy.podplay.service.ItunesService

class PodcastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TAG = javaClass.simpleName
        val itunesService = ItunesService.instance
        val itunesRepo = ItunesRepo(itunesService)

        itunesRepo.searchByTerm("Android Developer") {
            Log.i(TAG, "Results = $it")
        }
    }
}