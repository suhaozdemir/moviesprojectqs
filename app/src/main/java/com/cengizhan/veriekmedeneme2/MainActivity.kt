package com.cengizhan.veriekmedeneme2

import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.cengizhan.veriekmedeneme2.model.Req
import com.cengizhan.veriekmedeneme2.model.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val dataList: MutableList<Result> = mutableListOf()
    val filmList = ArrayList<String>()


    lateinit var myAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myAdapter = MyAdapter(dataList)



        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        my_recycler_view.adapter = myAdapter
        for (i in 1..400) {
            val urlLink="https://api.themoviedb.org/3/movie/top_rated?api_key=9cb322b1006fcfd1800a689018e6a7d4&page="+i

            AndroidNetworking.initialize(this)
            AndroidNetworking.get(urlLink)
                .build()
                .getAsObject(Req::class.java, object : ParsedRequestListener<Req> {
                    override fun onResponse(response: Req?) {
                        if (response != null) {
                            dataList.addAll(response.results)

                        }
                        myAdapter.notifyDataSetChanged()
                    }

                    override fun onError(anError: ANError?) {
                    }


                })

        }

        bottomBarsetup()


    }
    /*fun detayclick(view: View){
        val filmisim = user_fullname.text
        val filmpuan = vote_rateDetay.text
        val filmaciklama = aciklama.text
        val intent3 = Intent(this,detayActivity::class.java)
        intent3.putExtra("KeyFilmAdi",filmisim)
        intent3.putExtra("KeyPuan",filmpuan)
        intent3.putExtra("KeyAciklama",filmaciklama)
        startActivity(intent3)
    }*/


    private fun bottomBarsetup() {
        nav_view.setOnNavigationItemSelectedListener{

            when(it.itemId){
                R.id.arsiv -> {
                    val intent = Intent(this,arsivActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.profil -> {
                    val intent2 = Intent(this,activityProfile::class.java)
                    startActivity(intent2)
                    finish()
                }
            }
            true

        }
    }



}