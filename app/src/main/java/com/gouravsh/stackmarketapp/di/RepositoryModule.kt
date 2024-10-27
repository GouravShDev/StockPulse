package com.gouravsh.stackmarketapp.di

import com.gouravsh.stackmarketapp.data.csv.CSVParser
import com.gouravsh.stackmarketapp.data.csv.CompanyListParser
import com.gouravsh.stackmarketapp.data.csv.IntradayInfoParser
import com.gouravsh.stackmarketapp.data.repository.StockRepositoryImpl
import com.gouravsh.stackmarketapp.domain.model.CompanyListing
import com.gouravsh.stackmarketapp.domain.model.IntradayInfo
import com.gouravsh.stackmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListParser: CompanyListParser
    ): CSVParser<CompanyListing>
    @Binds
    @Singleton
    abstract fun bindIntradayParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepository: StockRepositoryImpl
    ): StockRepository
}