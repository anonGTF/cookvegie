package com.galih.cookvegie.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.galih.cookvegie.R
import com.galih.cookvegie.data.source.remote.RecipeResponse
import com.galih.cookvegie.data.source.remote.RetrofitInstance.Companion.api
import com.galih.cookvegie.databinding.ActivityHomeBinding
import com.galih.cookvegie.domain.model.Recipe
import com.galih.cookvegie.favorite.FavoriteActivity
import com.galih.cookvegie.result.ResultActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCTA.setOnClickListener {
            openImageChooser()
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
                startActivity(Intent(this@HomeActivity, FavoriteActivity::class.java))
                return true
            }
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this@HomeActivity,
                            Options.init().setRequestCode(100).setMode(Options.Mode.Picture))
                } else {
                    Toast.makeText(this@HomeActivity,
                            "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }


    private fun openImageChooser() {
        Pix.start(this@HomeActivity,
                Options.init().setRequestCode(100).setMode(Options.Mode.Picture))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val returnValue: ArrayList<String> = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS) as ArrayList<String>
            Log.d("coba", "onActivityResult: ${returnValue[0]}")
            intent = Intent(this@HomeActivity, ResultActivity::class.java)
            intent.putExtra(ResultActivity.EXTRA_FILE_PATH, returnValue[0])
            startActivity(intent)
        }
    }
}
