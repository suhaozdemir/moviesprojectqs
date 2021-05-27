package com.quadsoft.moviesprojectqs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quadsoft.moviesprojectqs.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class ArchiveAdapter(private val movieList: ArrayList<UserMovies>) : RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>(){

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        context = parent.context
        return ArchiveViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        val currentmovie =movieList[position]
        holder.movieName.text = currentmovie.name
        holder.movieDetails.text = currentmovie.details
        holder.movieRating.text = currentmovie.rating

       val movieImgView = holder.itemView.user_avatar

        val imgPath = movieList.get(position).imgpath

        Picasso.get()
            .load(imgPath)
            .into(movieImgView)



        holder.itemView.setOnClickListener() {
            val data2=movieList[position]


            val movieName = data2.name
            val movieRating = data2.rating
            val movieDetails = data2.details
            val movieImage = data2.imgpath
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
        val movieName : TextView = itemView.findViewById(R.id.user_fullname)
        val movieRating : TextView = itemView.findViewById(R.id.vote_rateDetay)
        val movieDetails : TextView = itemView.findViewById(R.id.aciklama)
        val movieImage : ImageView = itemView.findViewById(R.id.user_avatar)
    }

}