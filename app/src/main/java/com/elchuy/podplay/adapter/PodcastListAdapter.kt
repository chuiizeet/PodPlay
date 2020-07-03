package com.elchuy.podplay.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elchuy.podplay.R
import com.elchuy.podplay.viewmodel.SearchViewModel

class PodcastListAdapter(
    private var podcasSummaryViewList: List<SearchViewModel.PodcastSummaryViewData>?,
    private val podcastListAdapterLisneter: PodcastListAdapterLisneter,
    private val parentActivity: Activity) : RecyclerView.Adapter<PodcastListAdapter.ViewHolder>() {

    interface PodcastListAdapterLisneter {
        fun onShowDetails(podcastSummaryViewData: SearchViewModel.PodcastSummaryViewData)
    }

    inner class ViewHolder(v: View, private val podcastListAdapterLisneter: PodcastListAdapterLisneter) : RecyclerView.ViewHolder(v) {
        var podcastSummaryViewData: SearchViewModel.PodcastSummaryViewData? = null
        val nameTextView: TextView = v.findViewById(R.id.podcastNameTextView)
        val lastUpdatedTextView: TextView = v.findViewById(R.id.podcastLastUpdatedTextView)
        val podcastImage: ImageView = v.findViewById(R.id.podcastImage)

        init {
            v.setOnClickListener {
                podcastSummaryViewData?.let {
                    podcastListAdapterLisneter.onShowDetails(it)
                }
            }
        }
    }

    fun setSearchData(podcastSummaryViewData: List<SearchViewModel.PodcastSummaryViewData>) {
        podcasSummaryViewList = podcastSummaryViewData
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PodcastListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item, parent, false),
            podcastListAdapterLisneter )
    }

    override fun getItemCount(): Int {
        return podcasSummaryViewList?.size?: 0
    }

    override fun onBindViewHolder(holder: PodcastListAdapter.ViewHolder, position: Int) {

        val searchViewList = podcasSummaryViewList ?: return
        val searchView = searchViewList[position]
        holder.podcastSummaryViewData = searchView
        holder.nameTextView.text = searchView.name
        holder.lastUpdatedTextView.text = searchView.lastUpdated

        Glide.with(parentActivity)
            .load(searchView.imageUrl)
            .into(holder.podcastImage)
    }


}