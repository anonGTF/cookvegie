package com.galih.cookvegie.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.galih.cookvegie.R
import com.galih.cookvegie.databinding.ActivityFavoriteBinding
import com.galih.cookvegie.detail.DetailActivity
import com.galih.cookvegie.result.RecipeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        viewModel.getAllFavRecipes().observe(this, Observer {
            recipeAdapter.differ.submitList(it)
            binding.tvFavMessage.text = if (it.isEmpty()) {
                "There is no favorite recipe"
            } else {
                "Swipe left/right to remove"
            }
        })

        val itemTouchHelperCallback =
            ItemTouchHelperCallback(recipeAdapter, binding.root, viewModel)

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter()
        with(binding.rvFavorite) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = recipeAdapter
        }

        recipeAdapter.setOnItemClickListener {
            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
    }
}