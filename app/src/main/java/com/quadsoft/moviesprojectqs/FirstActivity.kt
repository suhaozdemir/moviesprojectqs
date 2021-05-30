package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.quadsoft.moviesprojectqs.model.Req
import com.quadsoft.moviesprojectqs.model.Result
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.check_internet_dialog.view.*

class FirstActivity : AppCompatActivity() {
    private val dataList2: MutableList<Result> = mutableListOf()

    private lateinit var myAdapter: verticalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        checkConnection()

        myAdapter = verticalAdapter(dataList2)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        recyclerView.adapter = myAdapter

        val urlLink = "https://api.themoviedb.org/3/movie/now_playing?api_key=9cb322b1006fcfd1800a689018e6a7d4&page=%22+i"

        AndroidNetworking.initialize(this)
        AndroidNetworking.get(urlLink)
            .build()
            .getAsObject(Req::class.java,object: ParsedRequestListener<Req> {
                override fun onResponse(response: Req?) {
                    if (response != null) {
                        dataList2.addAll(response.results)

                    }
                    myAdapter.notifyDataSetChanged()

                }

                override fun onError(anError: ANError?) {

                }

            })






        bottomBarsetup()

    }


    private fun bottomBarsetup() {
        nav_view.setOnNavigationItemSelectedListener{

            when(it.itemId){
                R.id.navArchive -> {
                    val intent = Intent(this,ArchiveActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.navProfile -> {
                    val intent2 = Intent(this,ProfileActivity::class.java)
                    startActivity(intent2)
                    finish()
                }
            }
            true

        }
    }
    fun btnClick(view: View){
        var btnSec = view as Button
        when(btnSec.id){
            btnTopRated.id->{
                val topratedLink="https://api.themoviedb.org/3/movie/top_rated?api_key=9cb322b1006fcfd1800a689018e6a7d4&page=1"
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("Key",topratedLink)
                startActivity(intent)
            }
            btnPopular.id->{
                val popularLink="https://api.themoviedb.org/3/movie/popular?api_key=9cb322b1006fcfd1800a689018e6a7d4&page=1"
                val intent2 = Intent(this,MainActivity::class.java)
                intent2.putExtra("Key",popularLink)
                startActivity(intent2)
            }
            btnUpComing.id->{
                val upcomingLink="https://api.themoviedb.org/3/movie/upcoming?api_key=9cb322b1006fcfd1800a689018e6a7d4&page=1"
                val intent3 = Intent(this,MainActivity::class.java)
                intent3.putExtra("Key",upcomingLink)
                startActivity(intent3)

            }
        }
    }

    fun checkConnection(){
        val wifiDialog = LayoutInflater.from(this).inflate(R.layout.check_internet_dialog,null)
        val builder = AlertDialog.Builder(this,R.style.DialogTheme)
            .setView(wifiDialog)
        val alert = builder.create()
        val networkConnection = NetworkConnection(applicationContext)

        networkConnection.observe(this, androidx.lifecycle.Observer  { isConnected ->
            if (!isConnected) {
                alert.show()
            }
            wifiDialog.btnRetry.setOnClickListener {
                if (isConnected){
                    alert.dismiss()
                }else
                    Toast.makeText(this, "Please check your Internet connection.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}