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
import kotlinx.android.synthetic.main.item_view.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerViewAdapter
    val dataList: MutableList<Result> = mutableListOf()
    val displayList = ArrayList<Result>()
    abstract val holder2 : MyHolder
    val names = holder2.itemView.user_fullname
    val arrayList = ArrayList<String>()


    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myAdapter = MyAdapter(dataList)

        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        my_recycler_view.adapter = myAdapter


        AndroidNetworking.initialize(this)
        for (i in 0..400) {
        val urlLink="https://api.themoviedb.org/3/movie/top_rated?api_key=9cb322b1006fcfd1800a689018e6a7d4&page="+i


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
        country_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

          // bottomBarsetup()


    })
    }}

   /* private fun bottomBarsetup() {
        nav_view.setOnNavigationItemSelectedListener{

            when(it.itemId){
                R.id.arsiv -> {
                    val intent = Intent(this,arsivActivity::class.java)
                    startActivity(intent)

                }
                R.id.profil -> {
                    val intent2 = Intent(this,activityProfile::class.java)
                    startActivity(intent2)

                }
            }
            true

        }
    }
    /*intent = Intent(applicationContext,arsivActivity::class.java)
                    startActivity(intent)*/
    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)
        val menuItem = menu!!.findItem(R.id.action_search)


        if(menuItem != null){
            val searchView =  menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty()){
                        displayList.clear()
                        val search=newText.toLowerCase(Locale.getDefault())
                        dataList.forEach{
                            if (it.title.toLowerCase(Locale.getDefault()).contains(search)){
                                displayList.add(it)
                            }
                        }
                        my_recycler_view.adapter!!.notifyDataSetChanged()
                    }
                    else {
                        displayList.clear()
                        displayList.addAll(dataList)
                        my_recycler_view.adapter!!.notifyDataSetChanged()
                    }

                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }*/
}*/