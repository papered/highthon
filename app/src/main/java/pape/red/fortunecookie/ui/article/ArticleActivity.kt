package pape.red.fortunecookie.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_article.*
import pape.red.fortunecookie.R
import pape.red.fortunecookie.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this)[ArticleViewModel::class.java] }

    val id by lazy { intent.getIntExtra("id", -1) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityArticleBinding>(this, R.layout.activity_article)
        binding.viewModel = viewModel
        viewModel.getArticle(id)

        viewModel.content.observe(this, Observer {
            binding.articleList.adapter = ArticleListAdapter(viewModel.content.value ?: arrayListOf())
            binding.articleList.adapter?.notifyDataSetChanged()
        })

        binding.commentSubmitButton.setOnClickListener {
            viewModel.submitComment(id)
        }
    }
}
