package pape.red.fortunecookie.ui.write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pape.red.fortunecookie.R
import pape.red.fortunecookie.databinding.ItemWriteListTextBinding

class WriteListAdapter(val itemList: ArrayList<Content>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1)
            WriteListImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_write_list_image, parent, false))
        else
            WriteListContentViewHolder(ItemWriteListTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int) = if (itemList[position].isImage) 1 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return if (getItemViewType(position) == 1)
            (holder as WriteListImageViewHolder).bind()
        else
            (holder as WriteListContentViewHolder).bind()
    }

    inner class WriteListContentViewHolder(val binding: ItemWriteListTextBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.content = itemList[adapterPosition]
        }
    }

    inner class WriteListImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val content: ImageView = view.findViewById(R.id.itemWriteListImage)

        fun bind() {
            Glide.with(view)
                .load(itemList[adapterPosition].imageUrl)
                .into(content)
        }
    }
}