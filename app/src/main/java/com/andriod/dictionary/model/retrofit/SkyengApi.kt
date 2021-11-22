package com.andriod.dictionary.model.retrofit

import com.andriod.dictionary.entity.Word
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyengApi {
    @GET("api/public/v1/words/search")
    fun search(@Query("search") query: String): Observable<List<Word>>
}