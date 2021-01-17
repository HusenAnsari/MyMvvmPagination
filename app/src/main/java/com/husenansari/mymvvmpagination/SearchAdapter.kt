package com.husenansari.mymvvmpagination

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.husenansari.mymvvmpagination.models.ResultX

class SearchAdapter(
    private val context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dealInformationBeans = ArrayList<ResultX>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val searchViewHolder = viewHolder as SearchViewHolder
        searchViewHolder.setValues(dealInformationBeans[position])
    }

    override fun getItemCount(): Int {
        return dealInformationBeans.size
    }

    fun setSearchList(dealInformationBeanArrayList: ArrayList<ResultX>, ) {
        for (result in dealInformationBeanArrayList) {
            //add(result)
            dealInformationBeans.add(result)
            notifyItemInserted(dealInformationBeans.size - 1)
        }
    }

  /*  fun add(result: ResultX) {
        dealInformationBeans.add(result)
        notifyItemInserted(dealInformationBeans.size - 1)
    }*/


    private inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movie_title: TextView = view.findViewById(R.id.movie_title)
        private val movie_desc: TextView = view.findViewById(R.id.movie_desc)
        private val movie_poster: ImageView = view.findViewById(R.id.movie_poster)

        @SuppressLint("SetTextI18n")
        fun setValues(dealInformationBean: ResultX) {

            movie_title.setText(dealInformationBean.title)
            movie_desc.setText(dealInformationBean.overview)

            Glide.with(context)
                .load(dealInformationBean.poster_path)
                .dontAnimate()
                .into(movie_poster)

        }

    }



}