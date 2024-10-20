package com.gouravsh.stackmarketapp.data.mapper

import com.gouravsh.stackmarketapp.data.remote.dto.IntradayInfoDto
import com.gouravsh.stackmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntradayInfo() : IntradayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
    val date = LocalDateTime.parse(this.timestamp, formatter);
   return IntradayInfo(
        date,
        this.close
    );
}