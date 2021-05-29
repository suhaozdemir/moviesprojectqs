package com.quadsoft.moviesprojectqs


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quadsoft.moviesprojectqs.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.view.*
import kotlinx.android.synthetic.main.item_view.view.*


class MyAdapter(private val dataList: MutableList<Result>) : RecyclerView.Adapter<MyAdapter.MyHolder>() {
    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentMovie =dataList[position]
        holder.movieName.text = currentMovie.title
       // holder.movieDetails.text = currentMovie.overview
        holder.movieRating.text = currentMovie.voteAverage.toString()

        val movieImgView = holder.itemView.iv_movieImage
        val imgPath = dataList.get(position).posterPath
        val ratingBar = holder.itemView.ratingBar

        val image="https://image.tmdb.org/t/p/w500"+imgPath

        val movieName = currentMovie.title
        val movieRating = currentMovie.voteAverage
        val movieDetails = currentMovie.overview
        val movieImage = "https://image.tmdb.org/t/p/w500" + currentMovie.posterPath

        val a = movieRating.toFloat()
        ratingBar.rating = a / 2

        Picasso.get()
            .load(image)
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



    override fun getItemCount(): Int =dataList.size

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName : TextView = itemView.findViewById(R.id.iv_movieName)
        val movieRating : TextView = itemView.findViewById(R.id.iv_movieRating)
        //val movieDetails : TextView = itemView.findViewById(R.id.iv_movieDetail)
    }

}
