package com.elchuy.podplay.repository

import com.elchuy.podplay.model.Podcast
import retrofit2.Callback

class PodcastRepo {
    fun getPodcast(feedUrl: String, callback: (Podcast?) -> Unit) {
        callback(
            Podcast(feedUrl, "No name", "No description", "No image to load")
        )
    }
}