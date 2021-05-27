package com.quadsoft.moviesprojectqs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quadsoft.moviesprojectqs.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.android.synthetic.main.nowplayingitem_view.view.*

class verticalAdapter (private val dataList2: MutableList<Result>) : RecyclerView.Adapter<verticalAdapter.MyHolder2>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder2 {
        context=parent.context
        return MyHolder2(LayoutInflater.from(context).inflate(R.layout.nowplayingitem_view,parent,false))
    }

    override fun getItemCount(): Int =dataList2.size

    override fun onBindViewHolder(holder: MyHolder2, position: Int) {
        val data = dataList2[position]

        val imgVw = holder.itemView.imgNow
        val movienametxt = holder.itemView.movieNameNow
        val movieratetxt = holder.itemView.voteNow

        val name = "${data.title} "
        val vote = "${data.voteAverage} "
        val img = "https://image.tmdb.org/t/p/w500" + data.posterPath

        movienametxt.text = name
        movieratetxt.text = vote

        Picasso.get()
            .load(img)
            .into(imgVw)

        holder.itemView.setOnClickListener() {
            val data2=dataList2[position]



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
            //(context as Activity).finish()

        }

    }

    class MyHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}