package pape.red.fortunecookie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter(val adoptModel: ArrayList<ArticleModel>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ArticleAdapter.ArticleViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_article, viewGroup, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int = adoptModel.size

    override fun onBindViewHolder(p0: ArticleAdapter.ArticleViewHolder, p1: Int) {
        p0.bind(adoptModel[p1])
    }

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle = view.findViewById<TextView>(R.id.text_title)
        val textContent = view.findViewById<TextView>(R.id.text_content)
        val articleType = view.findViewById<ImageView>(R.id.ic_type)
        var textComment = view.findViewById<TextView>(R.id.text_comment)
        val textLike =view.findViewById<TextView>(R.id.text_like)
        fun bind(articleModel: ArticleModel) {
            textTitle.text = articleModel.title
            var content = ""
                articleModel.content?.forEach {
                    content = content + it
            }
            textContent.text = content

            articleType.setImageResource(
                if (articleModel.question) R.drawable.ic_question
                else R.drawable.ic_article
            )

            textLike.text = articleModel.like.toString()
            val comment = articleModel.comments?.size ?: 0
            textComment.text = comment.toString()
        }
    }
}