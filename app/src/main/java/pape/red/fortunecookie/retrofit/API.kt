package pape.red.fortunecookie.retrofit

import io.reactivex.Single
import okhttp3.MultipartBody
import pape.red.fortunecookie.ArticleModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @Multipart
    @POST("/article/image")
    suspend fun createArticle(@Part file: MultipartBody.Part): Response<Unit>

    @GET("/article/list")
    fun getArticle() : Call<ArrayList<ArticleModel>>

    @GET("/search/tags")
    fun getSearch(@Query("keys") keys:String) : Call<ArrayList<ArticleModel>>
}