package com.lazday.news.source.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideOkHttpClient() }
    single { provideRetrofit( get() ) }
    factory { provideNewsApi(get()) }
}

fun provideOkHttpClient() : OkHttpClient {
    return  OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client( okHttpClient )
        .addConverterFactory (
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
        )
        .build()
}

fun provideNewsApi(retrofit: Retrofit): ApiClient = retrofit.create(ApiClient::class.java)
