package pape.red.fortunecookie.retrofit

import io.reactivex.Single
import okhttp3.MultipartBody
import pape.red.fortunecookie.ui.article.response.ArticleResponse
import pape.red.fortunecookie.ui.write.response.CreateImageResponse
import pape.red.fortunecookie.ArticleModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @Multipart
    @POST("/article/image")
    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<CreateImageResponse>

    @POST("/article/create")
    suspend fun createArticle(@Body bodyMap: Any): Response<Unit>

    @GET("/article/list/{id}")
    suspend fun getArticle(@Path("id") id: Int): Response<ArticleResponse>

    @POST("/comment/list")
    suspend fun submitComment(@Body requestMap: Any): Response<Unit>

    @GET("/article/list")
    fun getArticle() : Call<ArrayList<ArticleModel>>

    @GET("/search/tags")
    fun getSearch(@Query("keys") keys:String) : Call<ArrayList<ArticleModel>>
}
