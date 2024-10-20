package com.gouravsh.stackmarketapp.data.csv

import com.gouravsh.stackmarketapp.domain.model.CompanyListing
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListParser @Inject constructor() : CSVParser<CompanyListing> {
    override suspend fun parse(inputStream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(inputStream))
        return csvReader.readAll().drop(1).mapNotNull {
            CompanyListing(
                name = it.getOrNull(1) ?: return@mapNotNull null,
                symbol = it.firstOrNull() ?: return@mapNotNull null,
                exchange = it.getOrNull(2) ?: return@mapNotNull null,
            ).also {
                csvReader.close()
            }
        }

    }

}