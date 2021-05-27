package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

private lateinit var auth: FirebaseAuth

val arrayList = ArrayList<String>()
val reference = FirebaseDatabase.getInstance().getReference("Movies")
    .child(FirebaseAuth.getInstance().currentUser!!.uid)

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        getdetailData()

        val name  = txt_dName.text

        auth = FirebaseAuth.getInstance()


        val found: Boolean = arrayList.contains(name)
        if(found == false)
        {
            addtoFavorites()
        }
        else{
            Toast.makeText(this, "HATA", Toast.LENGTH_SHORT).show()
            btFavourite.isEnabled == false
            btFavourite.setText("Favorilerde")
        }

        btRemove.setOnClickListener{
            deleteMovie()
        }


    }

    fun addtoFavorites(){

        val name   = txt_dName.text
        val rating = txt_dRating.text
        val detail = txt_dDetail.text

        val imagePath = intent.getStringExtra("it_movieImage")
        Picasso.get()
            .load(imagePath)
            .into(img_dImage)
        val path = imagePath


        btFavourite.setOnClickListener {
            val reference = FirebaseDatabase.getInstance().getReference("Movies")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)

            val id = name

            val movie = UserMovies(id as String,
                name as String,
                rating as String,
                detail as String,
                path as String)
            reference.child(id).setValue(movie)
            arrayList.add(name)
        }
    }

    fun deleteMovie(){
        val name   = txt_dName.text

        val id = name
        reference.child(id as String).removeValue()

        val intent = Intent(this, arsivActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getdetailData(){
        txt_dName.text = intent.getStringExtra("it_movieName")
        txt_dRating.text = intent.getStringExtra("it_movieRating")
        txt_dDetail.text = intent.getStringExtra("it_movieDetail")

        val imagePath = intent.getStringExtra("it_movieImage")
        Picasso.get()
            .load(imagePath)
            .into(img_dImage)

    }

    fun checkMovie(){
        /*val name   = txt_dName.text
        val ref = reference.child(name as String)
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              val deneme = snapshot.getValue().toString()
                if(name == deneme)
                {
                    Toast.makeText(this@DetailsActivity, "sgdsg", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })*/
    }
}

