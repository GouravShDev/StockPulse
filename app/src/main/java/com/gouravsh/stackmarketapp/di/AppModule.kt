package com.gouravsh.stackmarketapp.di

import android.app.Application
import androidx.room.Room
import com.gouravsh.stackmarketapp.data.local.StockDatabase
import com.gouravsh.stackmarketapp.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        val logging = HttpLoggingInterceptor()


        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient:  okhttp3.OkHttpClient =  okhttp3.OkHttpClient().newBuilder().addInterceptor(
            logging
        ).build();
        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create();
    }

    @Provides
    @Singleton
    fun providesStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(app.applicationContext, StockDatabase::class.java, "Stock.db")
            .build();
    }
}