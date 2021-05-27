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


            val filmisim = data2.name
            val filmpuan = data2.rating
            val filmaciklama = data2.details
            val filmresim = data2.imgpath
            val intent3= Intent(context,DetailsActivity::class.java)
            intent3.putExtra("KeyFilmAdi",filmisim)
            intent3.putExtra("KeyPuan",filmpuan.toString())
            intent3.putExtra("KeyAciklama",filmaciklama)
            intent3.putExtra("KeyResim",filmresim)
            context.startActivity(intent3)

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