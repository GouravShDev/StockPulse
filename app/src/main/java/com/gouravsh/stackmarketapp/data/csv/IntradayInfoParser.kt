package com.gouravsh.stackmarketapp.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.gouravsh.stackmarketapp.data.mapper.toIntradayInfo
import com.gouravsh.stackmarketapp.data.remote.dto.IntradayInfoDto
import com.gouravsh.stackmarketapp.domain.model.CompanyListing
import com.gouravsh.stackmarketapp.domain.model.IntradayInfo
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo> {

    override suspend fun parse(inputStream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(inputStream))
        return csvReader.readAll().drop(1).mapNotNull {
            IntradayInfoDto(
                timestamp = it.getOrNull(0) ?: return@mapNotNull null,
                close = it.getOrNull(4)?.toDouble() ?: return@mapNotNull null,
            )

                .toIntradayInfo()
        }
            .filter {
                val today = LocalDateTime.now()
                it.date.isAfter(today.minusDays(1));
            }.sortedBy {
                it.date.hour
            }
            .also {
                csvReader.close()
            }

    }

}