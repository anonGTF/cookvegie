package com.galih.cookvegie.favorite

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.galih.cookvegie.result.RecipeAdapter
import com.google.android.material.snackbar.Snackbar

class ItemTouchHelperCallback(
    private val adapter: RecipeAdapter,
    private val view: View,
    private val viewModel: FavoriteViewModel
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val recipe = adapter.differ.currentList[position]
        viewModel.deleteFav(recipe)
        Snackbar.make(view, "Successfully deleted recipe", Snackbar.LENGTH_LONG).apply {
            setAction("Undo") {
                viewModel.saveFav(recipe)
            }
            show()
        }
    }
}