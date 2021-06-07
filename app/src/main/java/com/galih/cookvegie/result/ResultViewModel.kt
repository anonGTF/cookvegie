package com.galih.cookvegie.result

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galih.cookvegie.data.source.remote.RecipeResponse
import com.galih.cookvegie.data.source.remote.RetrofitInstance
import com.galih.cookvegie.domain.model.Recipe
import com.galih.cookvegie.domain.usecase.RecipeUseCase
import com.galih.cookvegie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
): ViewModel() {

    val items: MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()

    fun uploadImage(file: File) = viewModelScope.launch {
        val requestFile: RequestBody? = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = requestFile?.let { MultipartBody.Part.createFormData("image", file.name, it) }

        if (body != null) {
            items.postValue(Resource.Loading())
            recipeUseCase.uploadImage(body)
                .enqueue(object : Callback<RecipeResponse> {
                    override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                        Log.d("coba", "onFailure: ${t.localizedMessage}")
                        items.postValue(Resource.Error(t.localizedMessage!!))
                    }

                    override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                        for (recipe in response.body()?.recipes!!) {
                            Log.d("coba", "onResponse: $recipe")
                        }
                        Log.d("coba", "onResponse: ${response.body()!!.labels}")
                        Log.d("coba", "onResponse: ${response.body()!!.rawLabels}")
                        items.postValue(Resource.Success(response.body()!!))
                    }
                })
        }
    }

}