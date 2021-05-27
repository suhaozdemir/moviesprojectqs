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
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.nav_view

class arsivActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var archiveRecycler : RecyclerView
    private lateinit var movieArrayList : ArrayList<UserMovies>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arsiv)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser!!

        archiveRecycler = findViewById(R.id.archiveRecycler)
        archiveRecycler.layoutManager = LinearLayoutManager(this)
        archiveRecycler.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))



        movieArrayList = arrayListOf<UserMovies>()
        getMovieData()


        bottomBarsetup()
    }


   private fun getMovieData(){

       val reference = FirebaseDatabase.getInstance().getReference("Movies")
           .child(FirebaseAuth.getInstance().currentUser!!.uid)

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
                R.id.anasayfa -> {
                    val intent = Intent(this,MainActivity::class.java)
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
}