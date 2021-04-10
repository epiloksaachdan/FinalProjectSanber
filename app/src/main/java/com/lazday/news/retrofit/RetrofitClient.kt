package com.lazday.news.retrofit

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
//    factory { AuthInterceptor() }
//    factory { provideOkHttpClient(get()) }
    factory { provideNewsApi(get()) }
//    single { provideRetrofit(get()) }
    single { provideRetrofit() }
}

//fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//    return Retrofit.Builder().baseUrl("https://newsapi.org/v2/").client(okHttpClient)
fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)
