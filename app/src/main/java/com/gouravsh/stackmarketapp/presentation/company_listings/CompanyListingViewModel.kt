package com.gouravsh.stackmarketapp.presentation.company_listings

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gouravsh.stackmarketapp.domain.model.CompanyListing
import com.gouravsh.stackmarketapp.domain.repository.StockRepository
import com.gouravsh.stackmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyListingsState())
    var searchJob: Job? = null

    init {
        getCompanyListings();
    }

    fun onEvent(event: CompanyListingEvent) {
        when (event) {
            is CompanyListingEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }

            is CompanyListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000)
                    getCompanyListings(query = event.query)
                 }

            }
        }
    }

   private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            searchJob = Job();
            repository.getCompanyListing(fetchFromRemote, query).collect { res ->
                when (res) {
                    is Resource.Success -> {
                        state = state.copy(
                            companies = res.data ?: listOf(),
                            isLoading = false,
                            searchQuery = query,
                            isRefreshing = false
                        );
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = res.isLoading)
                    }

                    is Resource.Error -> {
                        state = state.copy(error = res.message ?: "")
                    }
                }
            }
        }
    }

}