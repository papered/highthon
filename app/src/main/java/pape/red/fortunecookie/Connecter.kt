package pape.red.fortunecookie

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pape.red.fortunecookie.retrofit.Api
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Connecter {
    val retrofit: Retrofit
    val api: Api

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .build()

        retrofit = Retrofit
            .Builder()
            .baseUrl("http://15.164.234.89:1212/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(Api::class.java)
    }

}