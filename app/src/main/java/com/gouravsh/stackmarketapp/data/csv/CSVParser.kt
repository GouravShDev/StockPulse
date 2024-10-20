package com.gouravsh.stackmarketapp.data.csv

import java.io.InputStream

interface CSVParser<T>{
    suspend fun parse(inputStream: InputStream): List<T>
}