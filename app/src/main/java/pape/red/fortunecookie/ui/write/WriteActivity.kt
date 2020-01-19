package pape.red.fortunecookie.ui.write

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pape.red.fortunecookie.R
import pape.red.fortunecookie.databinding.ActivityWriteBinding
import pape.red.fortunecookie.retrofit.Api
import retrofit2.Retrofit
import java.io.File
import java.io.FileNotFoundException
import java.net.URI


class WriteActivity : AppCompatActivity() {

    private final val RESULT_REQUEST_IMAGE = 8888

    val viewModel by lazy { ViewModelProviders.of(this)[WriteViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityWriteBinding>(this, R.layout.activity_write)
        binding.viewModel = viewModel

        viewModel.finish = {
            finish()
        }

        write_submitImage.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK).setType("image/*"), RESULT_REQUEST_IMAGE)
        }

        write_submitButton.setOnClickListener {
            viewModel.submit()
        }

        write_question.setOnClickListener {
            viewModel.isQuestion.set(true)
        }

        write_hugi.setOnClickListener {
            viewModel.isQuestion.set(false)
        }
        writeList.adapter = WriteListAdapter(viewModel.writeListLiveData.value ?: arrayListOf())

        viewModel.writeListLiveData.observe(this, Observer {
            writeList.adapter?.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                val photoUri = data!!.data
                var cursor: Cursor? = null
                try {
                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    assert(photoUri != null)
                    cursor = contentResolver.query(photoUri!!, proj, null, null, null)
                    assert(cursor != null)
                    val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()

                    val file = File(cursor.getString(column_index))

                    viewModel.sendImage(file)

                } finally {
                    cursor?.close()
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        } else {
        }
    }
}
