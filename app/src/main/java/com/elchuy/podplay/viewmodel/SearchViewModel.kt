package com.elchuy.podplay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.elchuy.podplay.repository.ItunesRepo
import com.elchuy.podplay.service.PodcastResponse
import com.elchuy.podplay.util.DateUtils

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    var itunesRepo: ItunesRepo? = null

    data class PodcastSummaryViewData(
        var name: String? = "",
        var lastUpdated: String? = "",
        var imageUrl: String? = "",
        var feedUrl: String? = ""
    )

    private fun itunesPodcastToPodcastSummaryView(itunesPodcast: PodcastResponse.ItunesPodcast) : PodcastSummaryViewData {
        return PodcastSummaryViewData(
            itunesPodcast.collectionCensoredName,
            DateUtils.jsonDateToShortDate(itunesPodcast.releaseDate),
            itunesPodcast.artworkUrl30,
            itunesPodcast.feedUrl
        )
    }

    fun searchPodcast(term: String, callback: (List<PodcastSummaryViewData>) -> Unit) {
        itunesRepo?.searchByTerm(term) { resutls ->
            if (resutls == null) {
                callback(emptyList())
            } else {
                val searchViews = resutls.map { podcast ->
                    itunesPodcastToPodcastSummaryView(podcast)
                }
                callback(searchViews)
            }
        }
    }
}