package com.galih.cookvegie.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.galih.cookvegie.R
import com.galih.cookvegie.databinding.ActivityResultBinding
import com.galih.cookvegie.detail.DetailActivity
import com.galih.cookvegie.favorite.FavoriteActivity
import com.galih.cookvegie.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILE_PATH = "file_path"
    }

    private lateinit var binding: ActivityResultBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        val extras = intent.extras
        extras?.let {
            val filePath = extras.getString(EXTRA_FILE_PATH)
            if (filePath != null) {
                val file = File(filePath)
                viewModel.uploadImage(file)
                viewModel.items.observe(this, Observer { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
                            hideErrorMessage()
                            recipeAdapter.differ.submitList(response.data?.recipes)
                            binding.tvLabel.text = response.data?.labels?.joinToString()
                        }
                        is Resource.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                Toast.makeText(binding.root.context, "An error occured: $message", Toast.LENGTH_LONG).show()
                                showErrorMessage(message)
                            }
                        }
                        is Resource.Loading -> {
                            showProgressBar()
                        }
                    }
                })
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
                startActivity(Intent(this@ResultActivity, FavoriteActivity::class.java))
                return true
            }
        }
        return false
    }

    private fun showErrorMessage(message: String) {
        binding.error.cvError.visibility = View.VISIBLE
        binding.error.tvErrorMessage.text = message
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.error.cvError.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter()
        with(binding.rvRecipe) {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeAdapter
        }

        recipeAdapter.setOnItemClickListener {
            val intent = Intent(this@ResultActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
    }
}