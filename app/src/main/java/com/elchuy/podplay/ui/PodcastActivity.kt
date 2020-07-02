package com.elchuy.podplay.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.elchuy.podplay.R
import com.elchuy.podplay.repository.ItunesRepo
import com.elchuy.podplay.service.ItunesService
import kotlinx.android.synthetic.main.activity_podcast.*

class PodcastActivity : AppCompatActivity() {

    private val tag = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podcast)

        setupToolbar()
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
        val itunesService = ItunesService.instance
        val itunesRepo = ItunesRepo(itunesService)

        itunesRepo.searchByTerm(term) {
            Log.i(tag, "Results: $it")
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
}