package com.quadsoft.moviesprojectqs


import com.quadsoft.moviesprojectqs.model.Result

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.android.synthetic.main.item_view.view.vote_rateDetay

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



            val filmisim = data2.title
            val filmpuan = data2.voteAverage
            val filmaciklama = data2.overview
            val filmresim = "https://image.tmdb.org/t/p/w500" + data2.posterPath
            val intent3= Intent(context,DetailsActivity::class.java)
            intent3.putExtra("KeyFilmAdi",filmisim)
            intent3.putExtra("KeyPuan",filmpuan.toString())
            intent3.putExtra("KeyAciklama",filmaciklama)
            intent3.putExtra("KeyResim",filmresim)
            context.startActivity(intent3)

        }



    }

    override fun getItemCount(): Int =dataList.size
}
