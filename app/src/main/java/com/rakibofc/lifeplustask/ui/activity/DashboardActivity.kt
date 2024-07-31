package com.rakibofc.lifeplustask.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.data.remote.SearchResult
import com.rakibofc.lifeplustask.data.remote.Show
import com.rakibofc.lifeplustask.databinding.ActivityDashboardBinding
import com.rakibofc.lifeplustask.ui.adapter.SearchResultAdapter
import com.rakibofc.lifeplustask.util.AppPreference
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class DashboardActivity : BaseActivity(), SearchResultAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityDashboardBinding

    private var userID: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get user id from intent
        userID = intent.getLongExtra(UserEntity.USER_ID_KEY, 0)

        // Setup observer
        setupObserver()

        // Load user data
        loadViewModelData()

        // Setup listeners
        setupListener()
    }

    private fun setupListener() {

        binding.rlName.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ProfileActivity::class.java).apply {
                putExtra(UserEntity.USER_ID_KEY, userID)
            })
        }

        binding.ivLogout.setOnClickListener {
            AppPreference.with(applicationContext).remove(UserEntity.IS_LOGGED_IN_KEY)
            AppPreference.with(applicationContext).remove(UserEntity.USER_ID_KEY)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.searchViewText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                // Load word from database
                query?.let {
                    loadSearchText(it)
                }

                // Hide keyboard
                binding.searchViewText.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.mToolbar.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.actionSearch -> {

                    // Hide keyboard
                    hideKeyboard()

                    val searchText = binding.searchViewText.query.toString()
                    loadSearchText(searchText)
                }
            }
            true
        }
    }

    private fun setupObserver() {

        viewModel.user.observe(this) {
            handleUserData(it)
        }

        viewModel.searchResult.observe(this) {
            handleSearchResult(it)
        }
    }

    private fun handleUserData(user: UiState<UserEntity>) {
        when (user) {
            is UiState.Loading -> {
                // Nothing todo..
            }

            is UiState.Error -> {
                showToast(user.message)
            }

            is UiState.Success -> {
                binding.userName.text = user.data.name
            }
        }
    }

    private fun handleSearchResult(searchResult: UiState<List<SearchResult>>) {
        when (searchResult) {
            is UiState.Loading -> {
                binding.rvSearchResult.visibility = View.GONE
                binding.tvStatus.visibility = View.GONE
                binding.loadingEffect.visibility = View.VISIBLE
            }

            is UiState.Error -> {
                binding.tvStatus.text = searchResult.message

                binding.rvSearchResult.visibility = View.GONE
                binding.loadingEffect.visibility = View.GONE
                binding.tvStatus.visibility = View.VISIBLE
            }

            is UiState.Success -> {
                binding.loadingEffect.visibility = View.GONE

                if (searchResult.data.isNotEmpty()) {

                    binding.rvSearchResult.adapter =
                        SearchResultAdapter(
                            applicationContext,
                            searchResult.data,
                            this@DashboardActivity
                        )

                    binding.tvStatus.visibility = View.GONE
                    binding.rvSearchResult.visibility = View.VISIBLE

                } else {
                    binding.tvStatus.text = getString(R.string.no_result_text)

                    binding.rvSearchResult.visibility = View.GONE
                    binding.tvStatus.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadSearchText(searchText: String) {

        if (searchText.isNotEmpty()) {

            binding.tvStatus.visibility = View.GONE
            binding.rvSearchResult.visibility = View.GONE
            binding.loadingEffect.visibility = View.VISIBLE

            lifecycleScope.launch {
                viewModel.getSearchResult(searchText)
            }
        }
    }

    private fun loadViewModelData() {

        val showKeyWords = arrayOf("iron", "man", "tiger", "wolf")
        val randomKeyword = showKeyWords[Random.nextInt(0, showKeyWords.size)]

        // Set keyword in the SearchBox
        binding.searchViewText.setQuery(randomKeyword, true)
        loadSearchText(randomKeyword)

        lifecycleScope.launch {
            viewModel.loadUser(userID)
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onItemClick(position: Int, searchResult: SearchResult) {
        startActivity(Intent(this@DashboardActivity, DetailsActivity::class.java).apply {
            putExtra(Show.SHOW_KEY, searchResult.show)
        })
    }
}