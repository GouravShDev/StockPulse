package com.gouravsh.stackmarketapp.domain.model

import com.squareup.moshi.Json

data class CompanyInfo(
    val symbol: String, val name: String,
    val description: String,
    val country: String,
    val industry: String,
) {
}