package com.galih.cookvegie.data.source.remote

import com.galih.cookvegie.domain.model.Recipe
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RecipeApi {

    @Multipart
    @POST("detect")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<RecipeResponse>

}