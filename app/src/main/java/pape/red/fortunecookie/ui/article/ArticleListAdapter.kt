package pape.red.fortunecookie.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pape.red.fortunecookie.R
import pape.red.fortunecookie.databinding.ItemWriteListTextBinding
import pape.red.fortunecookie.ui.article.response.ArticleResponse
import pape.red.fortunecookie.ui.write.Content

class ArticleListAdapter(val items: ArrayList<Article>) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return when (viewType) {
            0 -> ArticleTextViewHolder(ItemWriteListTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            1 -> ArticleImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_write_list_image, parent, false))
            2 -> ArticleCommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
            else -> ArticleHeartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_heart, parent, false))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.bind()

    override fun getItemViewType(position: Int): Int = items[position].type

    abstract class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind()
    }

    inner class ArticleTextViewHolder(val binding: ItemWriteListTextBinding) : ArticleViewHolder(binding.root) {

        override fun bind() {
            binding.content = Content("", false, ObservableField(items[adapterPosition].content ?: ""))
        }
    }

    inner class ArticleImageViewHolder(val view: View) : ArticleViewHolder(view) {
        val content: ImageView = view.findViewById(R.id.itemWriteListImage)

        override fun bind() {
            Glide.with(view)
                .load(items[adapterPosition].content)
                .into(content)
        }
    }

    inner class ArticleCommentViewHolder(val view: View) : ArticleViewHolder(view) {
        val content: TextView = view.findViewById(R.id.comment)

        override fun bind() {
            content.text = items[adapterPosition].content
        }
    }

    inner class ArticleHeartViewHolder(val view: View) : ArticleViewHolder(view) {
        override fun bind() {

        }

    }

}