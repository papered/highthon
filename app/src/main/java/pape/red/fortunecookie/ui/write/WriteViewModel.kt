package pape.red.fortunecookie.ui.write

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pape.red.fortunecookie.retrofit.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class WriteViewModel : ViewModel() {

    val writeListLiveData = MutableLiveData(arrayListOf(Content()))
    val title = ObservableField("")
    val tags = ObservableField("")
    val isQuestion = ObservableBoolean(false)

    lateinit var finish : () -> Unit

    val retrofit = Retrofit.Builder().baseUrl("http://15.164.234.89:1212")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(Api::class.java)

    fun sendImage(file: File) {

        viewModelScope.launch(Dispatchers.IO) {

            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val fileName = MultipartBody.Part.createFormData("content", file.name, requestBody)

            val res = api.uploadImage(fileName)

            if (res.isSuccessful) {
                writeListLiveData.value?.let {
                    it.add(Content(res.body()?.content ?: "", true))
                    it.add(Content())
                    writeListLiveData.postValue(it)
                }
            }

            Log.d("되냐? ㅋㅋ", "응 될거같은데? ${res.isSuccessful}, ${res.body()} ${res.code()}")
        }
    }

    fun submit() {
        val map = hashMapOf(
            "title" to title.get(),
            "tags" to tags.get()?.trim()?.split(","),
            "content" to (writeListLiveData.value?.map { if (it.content.get()?.isEmpty() == false) it.content.get() ?: "" else it.imageUrl } ?: listOf()),
            "fb_uid" to "assd",
            "author" to "author",
            "question" to isQuestion.get()
        )

        viewModelScope.launch(Dispatchers.IO) {
            val res = api.createArticle(map)
            Log.d("되냐? ㅋㅋ", "응 되야만해 ${res.isSuccessful}, ${res.code()} ${res.body()}")
            finish()
        }


    }


}
