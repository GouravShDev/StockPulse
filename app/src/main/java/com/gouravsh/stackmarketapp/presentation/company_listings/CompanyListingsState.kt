package com.gouravsh.stackmarketapp.presentation.company_listings

import com.gouravsh.stackmarketapp.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val searchQuery : String = "",
    val error: String? = null,
)
