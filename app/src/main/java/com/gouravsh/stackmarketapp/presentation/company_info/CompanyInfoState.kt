package com.gouravsh.stackmarketapp.presentation.company_info

import com.gouravsh.stackmarketapp.domain.model.CompanyInfo
import com.gouravsh.stackmarketapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo : List<IntradayInfo> = emptyList(),
    val company : CompanyInfo? = null,
    val isLoading : Boolean = false,
    val error: String? = null,
    )
