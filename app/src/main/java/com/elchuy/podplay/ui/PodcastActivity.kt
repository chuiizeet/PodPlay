package com.elchuy.podplay.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.elchuy.podplay.R
import com.elchuy.podplay.adapter.PodcastListAdapter
import com.elchuy.podplay.repository.ItunesRepo
import com.elchuy.podplay.service.ItunesService
import com.elchuy.podplay.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_podcast.*

class PodcastActivity : AppCompatActivity(), PodcastListAdapter.PodcastListAdapterLisneter {

    private val tag = javaClass.simpleName
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var podcastListAdapter: PodcastListAdapter

    override fun onShowDetails(podcastSummaryViewData: SearchViewModel.PodcastSummaryViewData) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podcast)

        setupToolbar()
        setupViewModels()
        updateControls()

        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchMenu = menu?.findItem(R.id.search_item)
        val searchView = searchMenu?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        return  true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent!!)
    }

    private fun performSearch(term: String) {
        showProgressBar()
        searchViewModel.searchPodcast(term) { results ->
            hideProgressBar()
            toolbar.title = getString(R.string.search_resutls)
            podcastListAdapter.setSearchData(results)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            performSearch(query!!)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupViewModels() {
        val service = ItunesService.instance
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory(application)
        searchViewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        searchViewModel.itunesRepo = ItunesRepo(service)
    }

    private fun updateControls() {
        podcastRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        podcastRecyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            podcastRecyclerView.context,
            layoutManager.orientation
        )
        podcastRecyclerView.addItemDecoration(dividerItemDecoration)
        podcastListAdapter = PodcastListAdapter(null, this, this)
        podcastRecyclerView.adapter = podcastListAdapter
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}