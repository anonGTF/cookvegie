package com.galih.cookvegie.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.galih.cookvegie.databinding.ItemRecipeBinding
import com.galih.cookvegie.domain.model.Recipe

class RecipeAdapter: RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldRecipe: Recipe, newRecipe: Recipe) =  oldRecipe == newRecipe
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Recipe) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = differ.currentList[position]

        holder.binding.tvRecipeTitle.text = truncateString(recipe.judul, 30)
        holder.binding.tvBahan.text = truncateString(recipe.bahan.joinToString(), 50)
        holder.binding.tvCaloryPerServe.text = recipe.kalori
        Glide.with(holder.itemView.context)
            .load(recipe.thumbnail)
            .apply(RequestOptions().override(100))
            .into(holder.binding.imgRecipe)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(recipe) }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (Recipe) -> Unit) {
        onItemClickListener = listener
    }

    private fun truncateString(string: String?, maxSize: Int): String? {
        if (string != null) {
            if (string.length < maxSize) return string
            return string.take(maxSize) + "..."
        } else {
            return null
        }
    }

}