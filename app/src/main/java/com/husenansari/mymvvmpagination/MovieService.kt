package com.husenansari.mymvvmpagination

import com.husenansari.mymvvmpagination.models.TopRatedMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Pagination
 * Created by Suleiman19 on 10/27/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */
interface MovieService {
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") pageIndex: Int
    ): Call<TopRatedMovies?>
}