package com.gouravsh.stackmarketapp.data.mapper

import com.gouravsh.stackmarketapp.data.remote.dto.CompanyInfoDto
import com.gouravsh.stackmarketapp.domain.model.CompanyInfo

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol ?: "",
        name?: "",
        description?: "",
        country?: "",
        industry?: "",
    )
}