package com.jcdev.bestplaystv.view.activity

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.viewmodel.MainViewModel
import com.jcdev.bestplaystv.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : PlaysActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val viewModel = ViewModelProviders.of(this)
            .get(SearchViewModel::class.java)

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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0,0)
    }
}
