package com.quadsoft.moviesprojectqs


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quadsoft.moviesprojectqs.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*


class MyAdapter(private val dataList: MutableList<Result>) : RecyclerView.Adapter<MyHolder>() {
    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data =dataList[position]
        val userFullnameTextView = holder.itemView.user_fullname
        val userAvatarİmgView = holder.itemView.user_avatar
        val vote = holder.itemView.vote_rateDetay
        val overview = holder.itemView.aciklama

        val fulName = "${data.title} "
        val v = "${data.voteAverage} "
        val o = "${data.overview} "


        vote.text=v
        overview.text=o
        userFullnameTextView.text=fulName
        val resimyolu="https://image.tmdb.org/t/p/w500"+data.posterPath

        Picasso.get()
            .load(resimyolu)
            .into(userAvatarİmgView)

        holder.itemView.setOnClickListener() {
            val data2=dataList[position]



            val movieName = data2.title
            val movieRating = data2.voteAverage
            val movieDetails = data2.overview
            val movieImage = "https://image.tmdb.org/t/p/w500" + data2.posterPath
            val it_mDetails= Intent(context,DetailsActivity::class.java)
            it_mDetails.putExtra("it_movieName",movieName)
            it_mDetails.putExtra("it_movieRating",movieRating.toString())
            it_mDetails.putExtra("it_movieDetail",movieDetails)
            it_mDetails.putExtra("it_movieImage",movieImage)
            context.startActivity(it_mDetails)
            //(context as Activity).finish()

        }



    }

    override fun getItemCount(): Int =dataList.size
}
