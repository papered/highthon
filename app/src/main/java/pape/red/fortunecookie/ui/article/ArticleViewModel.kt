package pape.red.fortunecookie.ui.article

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pape.red.fortunecookie.retrofit.Api
import pape.red.fortunecookie.ui.write.Content
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleViewModel : ViewModel() {

    val title = ObservableField("")
    val tags = ObservableField("")
    val content = MutableLiveData<ArrayList<Article>>()
    val isQnA = ObservableBoolean()
    val comment = ObservableField("")


    val retrofit = Retrofit.Builder().baseUrl("http://15.164.234.89:1212")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(Api::class.java)

    fun getArticle(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = api.getArticle(id)

            Log.d("안되냐? ㅋㅋ", "${res.isSuccessful}, ${res.body()}, ${res.code()}")

            res.body()?.let {
                title.set(it.title)
                tags.set("#${it.tags.reduce { acc, s -> "$acc   #$s" }}")

                isQnA.set(it.question)

                val article = arrayListOf<Article>()
                article.addAll(it.content
                    .map { str ->
                        if (str.startsWith("http"))
                            Article(1, str)
                        else
                            Article(0, str)
                    })
                article.add(Article(3, ""))
                article.addAll(it.comments.map {
                    Article(2, it.content)
                })
                content.postValue(article)
            }
        }
    }

    fun submitComment(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = api.submitComment(
                hashMapOf(
                    "content" to comment.get(),
                    "author" to "ikmyoung",
                    "article_id" to id
                )
            )

            Log.d("아 끝나간다 ㅋㅋ", "${res.isSuccessful}, ${res.body()}, ${res.code()}")

            if (res.isSuccessful) {
                getArticle(id)
                comment.set("")
            }
        }
    }
}