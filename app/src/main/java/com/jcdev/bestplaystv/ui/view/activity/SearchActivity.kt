package com.jcdev.bestplaystv.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.model.User
import com.jcdev.bestplaystv.ui.view.adapter.SearchAdapter
import com.jcdev.bestplaystv.ui.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : PlaysActivity() {

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val viewModel = ViewModelProviders.of(this)
            .get(SearchViewModel::class.java)

        searchAdapter =
            SearchAdapter(ArrayList(0)) { item: Any ->
                onItemClicked(item)
            }
        resultsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        resultsView.adapter = searchAdapter
        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(textChanged: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.gameAndUserSearch(textChanged.toString())
            }

        })

        viewModel.gameList.observe(this, Observer {
            searchAdapter.loadItems(it)
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }

    private fun onItemClicked(item: Any) {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        if (item is Game) {
            bundle.putSerializable("game", item)
        } else {
            bundle.putSerializable("user", item as User)
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }

}
