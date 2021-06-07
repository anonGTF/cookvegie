package com.galih.cookvegie.util

import com.galih.cookvegie.data.source.local.RecipeEntity
import com.galih.cookvegie.domain.model.Recipe

object DataMapper {

    fun mapEntitiesToModel(input: List<RecipeEntity>): List<Recipe> = input.map {
        Recipe(
            id = it.id,
            judul = it.judul,
            bahan = it.bahan,
            kuantitas_bahan = it.kuantitas_bahan,
            thumbnail = it.thumbnail,
            kalori = it.kalori,
            prosedur = it.prosedur
        )
    }

    fun mapModelToEntity(input: Recipe): RecipeEntity = RecipeEntity(
        id = input.id,
        judul = input.judul,
        bahan = input.bahan,
        kuantitas_bahan = input.kuantitas_bahan,
        thumbnail = input.thumbnail,
        kalori = input.kalori,
        prosedur = input.prosedur
    )

}