package com.gouravsh.stackmarketapp.data.repository


import android.util.Log
import coil.network.HttpException
import com.gouravsh.stackmarketapp.data.csv.CSVParser
import com.gouravsh.stackmarketapp.data.local.StockDatabase
import com.gouravsh.stackmarketapp.data.mapper.toCompanyInfo
import com.gouravsh.stackmarketapp.data.mapper.toCompanyListing
import com.gouravsh.stackmarketapp.data.mapper.toCompanyListingEntity
import com.gouravsh.stackmarketapp.data.remote.StockApi
import com.gouravsh.stackmarketapp.data.remote.dto.IntradayInfoDto
import com.gouravsh.stackmarketapp.domain.model.CompanyInfo
import com.gouravsh.stackmarketapp.domain.model.CompanyListing
import com.gouravsh.stackmarketapp.domain.model.IntradayInfo
import com.gouravsh.stackmarketapp.domain.repository.StockRepository
import com.gouravsh.stackmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayParser: CSVParser<IntradayInfo>,
) : StockRepository {
    private val dao = db.dao;

    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localList = dao.searchCompanyListing(query);
            emit(Resource.Success(data = localList.map { it.toCompanyListing() }
            ))
            val isDbEmpty = localList.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false, ))
                return@flow
            }
            val remoteListing = try {
                val response = api.getListings()
                val res = response.byteStream();
                Log.d("companyList", res.toString())
                val companyList = companyListingParser.parse(response.byteStream())
                Log.d("response",response.toString())
                companyList
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "There was some error in IO"))
                null
            } catch (e: retrofit2.HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "There was some error in http request"))
                null
            }
            remoteListing?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(dao.searchCompanyListing("").map {
                    it.toCompanyListing()
                }))
                emit(Resource.Loading(false))


            }

        }
    }

    override suspend fun getIntradayInfo(symbol: String): Flow<Resource<List<IntradayInfo>>> {
        return flow {
            emit(Resource.Loading(true))
            try{
            val response = api.getIntradayInfo(symbol = symbol)
            val intradayInfo = intradayParser.parse(response.byteStream())
            emit(Resource.Success(intradayInfo));

            }catch (e : IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't parse csv"))
            }catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error("Failed to fetch from network"));
            }

        }
    }

    override suspend fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>> {
       return flow{
            try{

               emit(Resource.Loading(true))
                val response = api.getCompanyInfo( symbol)
                if(response.errorInformation != null){

                      emit(Resource.Error(response.errorInformation));
                    return@flow;

                }
                emit(Resource.Success(response.toCompanyInfo()))
            }catch (e : IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't parse csv"))
            }catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error("Failed to fetch from network"));
            }
        }
    }
}