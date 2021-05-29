package com.quadsoft.moviesprojectqs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class ArchiveAdapter(private val movieList: ArrayList<UserMovies>) : RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>(){

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        context = parent.context
        return ArchiveViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        val currentMovie =movieList[position]
        holder.movieName.text = currentMovie.name
       // holder.movieDetails.text = currentMovie.details
        holder.movieRating.text = currentMovie.rating

       val movieImgView = holder.itemView.iv_movieImage
        val imgPath = movieList.get(position).imgpath
        val ratingBar = holder.itemView.ratingBar
        val movieName = currentMovie.name
        val movieRating = currentMovie.rating
        val movieDetails = currentMovie.details
        val movieImage = currentMovie.imgpath

        val a = movieRating!!.toFloat()
        ratingBar.rating = a / 2

        Picasso.get()
            .load(imgPath)
            .into(movieImgView)


        holder.itemView.setOnClickListener() {


            val it_mDetails= Intent(context,DetailsActivity::class.java)
            it_mDetails.putExtra("it_movieName",movieName)
            it_mDetails.putExtra("it_movieRating",movieRating.toString())
            it_mDetails.putExtra("it_movieDetail",movieDetails)
            it_mDetails.putExtra("it_movieImage",movieImage)
            context.startActivity(it_mDetails)

        }
    }



    override fun getItemCount(): Int {
        return movieList.size
    }

    class ArchiveViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val movieName : TextView = itemView.findViewById(R.id.iv_movieName)
        val movieRating : TextView = itemView.findViewById(R.id.iv_movieRating)
        //val movieDetails : TextView = itemView.findViewById(R.id.iv_movieDetail)

    }

}