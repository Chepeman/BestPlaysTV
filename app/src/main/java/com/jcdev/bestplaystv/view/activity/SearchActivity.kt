package com.jcdev.bestplaystv.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.view.adapter.SearchAdapter
import com.jcdev.bestplaystv.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : PlaysActivity() {

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val viewModel = ViewModelProviders.of(this)
            .get(SearchViewModel::class.java)

        searchAdapter = SearchAdapter(ArrayList(0))
        resultsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        resultsView.adapter = searchAdapter
        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(textChanged: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.gameAndUserSearch(textChanged.toString())
            }

        })

        viewModel.gameList.observe(this, Observer {
            searchAdapter.loadItems(it!!.toList())
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}
