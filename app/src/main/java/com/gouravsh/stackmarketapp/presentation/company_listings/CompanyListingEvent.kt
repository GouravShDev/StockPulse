package com.gouravsh.stackmarketapp.presentation.company_listings

sealed class CompanyListingEvent {
    object Refresh: CompanyListingEvent()
    data class OnSearchQueryChange(val query: String) : CompanyListingEvent()
}