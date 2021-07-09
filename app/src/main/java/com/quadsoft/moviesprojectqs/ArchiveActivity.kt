package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_arsiv.*
import kotlinx.android.synthetic.main.check_internet_dialog.view.*


class ArchiveActivity : AppCompatActivity() {

    private lateinit var archiveRecycler : RecyclerView
    private lateinit var movieArrayList : ArrayList<UserMovies>
    val reference = FirebaseDatabase.getInstance().getReference("Movies")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arsiv)

        checkConnection()

        archiveRecycler = findViewById(R.id.archiveRecycler)
        archiveRecycler.layoutManager = LinearLayoutManager(this)
        archiveRecycler.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))

        movieArrayList = arrayListOf<UserMovies>()

        getMovieData()
        bottomBarsetup()
    }


   private fun getMovieData(){

        reference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (movieSnapshot in snapshot.children){

                        val movie = movieSnapshot.getValue(UserMovies::class.java)
                        movieArrayList.add(movie!!)

                    }
                    archiveRecycler.adapter = ArchiveAdapter(movieArrayList)
                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
   }
    private fun bottomBarsetup() {
        nav_view.setOnNavigationItemSelectedListener{

            when(it.itemId){
                R.id.navHome -> {
                    val intent = Intent(this,FirstActivity::class.java)
                    startActivity(intent)
                }
                R.id.navProfile -> {
                    val intent = Intent(this,ProfileActivity::class.java)
                    startActivity(intent)
                }
            }
            true
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