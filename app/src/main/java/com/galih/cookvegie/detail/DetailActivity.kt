package com.galih.cookvegie.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.arch.core.executor.DefaultTaskExecutor
import com.bumptech.glide.Glide
import com.galih.cookvegie.R
import com.galih.cookvegie.databinding.ActivityDetailBinding
import com.galih.cookvegie.domain.model.Recipe
import com.galih.cookvegie.favorite.FavoriteActivity
import com.galih.cookvegie.util.OrderedListSpan
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val WIDTH = 70
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        extras?.let {
            val recipe = extras.getParcelable<Recipe>(EXTRA_DATA)
            if (recipe != null) {
                populateItem(recipe)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.open_favorite -> {
                startActivity(Intent(this@DetailActivity, FavoriteActivity::class.java))
                return true
            }
        }
        return false
    }

    private fun populateItem(recipe: Recipe) {
        val bahanBuilder = SpannableStringBuilder()
        recipe.kuantitas_bahan.forEachIndexed { index, item ->
            bahanBuilder.append(
                "$item ${recipe.bahan[index]}\n\n",
                OrderedListSpan(WIDTH, "${index + 1}."),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        val prosedurBuilder = SpannableStringBuilder()
        recipe.prosedur.forEachIndexed { index, item ->
            prosedurBuilder.append(
                "$item\n\n",
                OrderedListSpan(WIDTH, "${index + 1}."),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        with (binding) {
            tvDetailTitleRecipe.text = recipe.judul
            tvDetailAlatBahan.text = bahanBuilder
            tvDetailProsedur.text = prosedurBuilder
            tvDetailKalori.text = recipe.kalori
            Glide.with(this@DetailActivity)
                .load(recipe.thumbnail)
                .into(imgDetailRecipe)
            fab.setOnClickListener {
                viewModel.saveRecipe(recipe)
                Toast.makeText(this@DetailActivity,
                    "Successfully add recipe to favorite", Toast.LENGTH_LONG).show()
            }
        }
    }
}