package pape.red.fortunecookie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(val categoryModel: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CategoryAdapter.CategoryViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_category, viewGroup, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categoryModel.size

    override fun onBindViewHolder(p0: CategoryViewHolder, p1: Int) {
        p0.bind(categoryModel[p1])
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val category = view.findViewById<TextView>(R.id.category)
        fun bind(categoryModel:String) {
            category.text = categoryModel
        }
    }
}