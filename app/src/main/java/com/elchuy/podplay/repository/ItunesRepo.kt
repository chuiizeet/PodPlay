package com.elchuy.podplay.repository

import android.util.Log
import com.elchuy.podplay.service.ItunesService
import com.elchuy.podplay.service.PodcastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItunesRepo(private val itunesService: ItunesService) {
    fun searchByTerm(term: String, callBack: (List<PodcastResponse.ItunesPodcast>?) -> Unit) {
        val podcastCall = itunesService.searchPodcastByTerm(term)

        podcastCall.enqueue(object : Callback<PodcastResponse>{
            override fun onFailure(call: Call<PodcastResponse>?, t: Throwable?) {
//                Log.i(123.toString(), "onResponse: ConfigurationListener::"+call?.request()?.url());
//                Log.i(12.toString(), "Error: " + t.toString())
                callBack(null)
            }
            override fun onResponse(call: Call<PodcastResponse>?, response: Response<PodcastResponse>?) {
                val body = response?.body()
                callBack(body?.results)
            }
        })
    }
}
