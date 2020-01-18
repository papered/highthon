package pape.red.fortunecookie.retrofit

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Api {

    @Multipart
    @POST("/article/image")
    suspend fun createArticle(@Part file: MultipartBody.Part): Response<Unit>
}