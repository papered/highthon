package pape.red.fortunecookie

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone


class MainActivity : AppCompatActivity() {

    var articleModel = ArrayList<ArticleModel>()
    val categoryModel = arrayListOf(
        "게임",
        "영화"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        Connecter.api.getArticle().enqueue(object : Callback<ArrayList<ArticleModel>> {
            override fun onFailure(call: Call<ArrayList<ArticleModel>>, t: Throwable) {
                Log.e("test", t.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<ArticleModel>>,
                response: Response<ArrayList<ArticleModel>>
            ) {
                Log.e("test", "들어옴")
                articleModel.addAll(response.body()!!)
                val adapter = ArticleAdapter(response.body()!!)
                recyclerView.adapter = adapter
            }

        })

        recycler_category.adapter = CategoryAdapter(categoryModel)
        view.setOnClickListener {
            if(add_category_view.isVisible || search_view.isVisible) {
                add_category_view.visibility = View.GONE
                search_view.visibility= View.GONE
                imm.hideSoftInputFromWindow(edit_tag.windowToken,0)
            }
        }

        ic_search.setOnClickListener {
            search_view.visibility= View.VISIBLE
        }

        imageView3.setOnClickListener {
            if(add_category_view.isVisible) {
                add_category_view.visibility = View.GONE
            }else
                add_category_view.visibility = View.VISIBLE
        }

        imageView4.setOnClickListener {
            imm.hideSoftInputFromWindow(edit_tag.windowToken,0)
            Connecter.api.getSearch(edit_tag.text.toString()).enqueue(object  : Callback<ArrayList<ArticleModel>>{
                override fun onFailure(call: Call<ArrayList<ArticleModel>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<ArrayList<ArticleModel>>,
                    response: Response<ArrayList<ArticleModel>>
                ) {
                    if(response.body()?.size==0){
                        search_recycler.visibility = View.GONE
                        search_no.visibility = View.VISIBLE
                    }else{
                        search_recycler.visibility = View.VISIBLE
                        search_no.visibility = View.GONE
                        search_recycler.adapter=ArticleAdapter(response.body()!!)
                    }
                }

            })
        }

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                applicationContext,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View, position: Int) {
                        Log.e("test",articleModel.get(position).id.toString())
                    }
                })
        )

        add_category.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(add_category.text.toString().contains(" ")){
                    add_category_view.visibility = View.GONE
                    categoryModel.add(add_category.text.toString())
                    recyclerView.removeAllViews()
                    recycler_category.adapter = CategoryAdapter(categoryModel)
                    imm.hideSoftInputFromWindow(add_category.windowToken,0)
                }
            }
        })
    }
}
