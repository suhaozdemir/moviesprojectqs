package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


class ArchiveActivity : AppCompatActivity() {

    private lateinit var archiveRecycler : RecyclerView
    private lateinit var movieArrayList : ArrayList<UserMovies>
    val reference = FirebaseDatabase.getInstance().getReference("Movies")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arsiv)

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
}