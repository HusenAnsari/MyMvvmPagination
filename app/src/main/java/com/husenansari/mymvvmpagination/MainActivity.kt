package com.husenansari.mymvvmpagination

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.husenansari.mymvvmpagination.models.ResultX
import com.husenansari.mymvvmpagination.models.TopRatedMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    var adapter: SearchAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    var rv: RecyclerView? = null
    var progressBar: ProgressBar? = null

    private val PAGE_START = 1

    private var isLoading = false
    private var isLastPage = false

    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private var TOTAL_PAGES = 1
    private var currentPage = PAGE_START

    private var movieService: MovieService? = null
    private var topRatedMovies: TopRatedMovies? = null
    val recyclerViewState = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = SearchAdapter(this)

        rv = findViewById(R.id.main_recycler)
        progressBar = findViewById(R.id.main_progress)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv!!.layoutManager = linearLayoutManager
        //rv!!.itemAnimator = DefaultItemAnimator()

        rv!!.adapter = adapter

        //init service and load data
        movieService = RetrofitInstance.api

        loadPageData(currentPage)

        rv!!.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager!!) {
            override fun loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                loadPageData(currentPage)
            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })


    }


    private fun loadPageData(currentPage: Int) {
        progressBar!!.visibility = View.VISIBLE
        movieService!!.getTopRatedMovies(getString(R.string.my_api_key), "en_US", currentPage)
            .enqueue(
                object : Callback<TopRatedMovies?> {
                    override fun onResponse(
                        call: Call<TopRatedMovies?>,
                        response: Response<TopRatedMovies?>
                    ) {
                        progressBar!!.visibility = View.GONE
                        isLoading = false
                        val mainResponse: TopRatedMovies = response.body()!!
                        val results: ArrayList<ResultX> = mainResponse.results
                        TOTAL_PAGES = mainResponse.total_pages
                        topRatedMovies = mainResponse
                        if (currentPage <= TOTAL_PAGES) {
                            adapter!!.setSearchList(results)
                            //isLastPage = false
                        } else {
                            isLastPage = true
                        }
                    }

                    override fun onFailure(call: Call<TopRatedMovies?>, t: Throwable) {
                        t.printStackTrace()
                        Log.e("error", t.toString())
                        Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_LONG).show()
                    }

                })
    }

}