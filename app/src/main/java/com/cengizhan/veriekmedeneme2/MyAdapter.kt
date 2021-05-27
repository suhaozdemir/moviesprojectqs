package com.cengizhan.veriekmedeneme2

import com.cengizhan.veriekmedeneme2.model.Result

import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*

class MyAdapter(private val dataList: MutableList<Result>) : RecyclerView.Adapter<MyHolder>() {

    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        return  MyHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data =dataList[position]
        val userFullnameTextView = holder.itemView.user_fullname
        val userAvatarİmgView = holder.itemView.user_avatar
        val vote = holder.itemView.vote_rate

        val fulName = "${data.title} "
        val v = "${data.voteAverage} "


       /* val arrayList = ArrayList<String>()
        arrayList.add(fulName)

        val intent = Intent(this.context.applicationContext,MainActivity::class.java)
        intent.putExtra("Key", arrayList)*/



        vote.text=v
        userFullnameTextView.text=fulName
        val resimyolu="https://image.tmdb.org/t/p/w500"+data.posterPath

        Picasso.get()
                .load(resimyolu)
                .into(userAvatarİmgView)

        holder.itemView.setOnClickListener {
            Toast.makeText(context,fulName,Toast.LENGTH_SHORT).show()
        }



    }
    override fun getItemCount(): Int =dataList.size
}