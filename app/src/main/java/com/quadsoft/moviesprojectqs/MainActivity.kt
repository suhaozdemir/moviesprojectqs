package com.quadsoft.moviesprojectqs

import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.quadsoft.moviesprojectqs.model.Req
import com.quadsoft.moviesprojectqs.model.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.check_internet_dialog.view.*
import kotlinx.android.synthetic.main.item_view.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val dataList: MutableList<Result> = mutableListOf()

    lateinit var myAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkConnection()
        myAdapter = MyAdapter(dataList)

        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        my_recycler_view.adapter = myAdapter

        val urlLink = intent.getStringExtra("Key")

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
