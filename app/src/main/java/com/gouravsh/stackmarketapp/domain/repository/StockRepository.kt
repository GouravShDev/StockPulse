package com.gouravsh.stackmarketapp.domain.repository

import com.gouravsh.stackmarketapp.domain.model.CompanyInfo
import com.gouravsh.stackmarketapp.domain.model.CompanyListing
import com.gouravsh.stackmarketapp.domain.model.IntradayInfo
import com.gouravsh.stackmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol : String,
    ) : Flow<Resource<List<IntradayInfo>>>

    suspend fun getCompanyInfo(
        symbol: String,
    ): Flow<Resource<CompanyInfo>>
}