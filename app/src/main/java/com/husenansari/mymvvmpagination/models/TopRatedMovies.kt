package com.husenansari.mymvvmpagination.models

data class TopRatedMovies(
    val page: Int,
    val results: ArrayList<ResultX>,
    val total_pages: Int,
    val total_results: Int
)