package com.quadsoft.moviesprojectqs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

private lateinit var auth: FirebaseAuth

val arrayList = ArrayList<String>()

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        detayİsim.text = intent.getStringExtra("KeyFilmAdi")
        voteDetay.text = intent.getStringExtra("KeyPuan")
        detayAciklama.text = intent.getStringExtra("KeyAciklama")

        val imagePath = intent.getStringExtra("KeyResim")
        Picasso.get()
            .load(imagePath)
            .into(avatarDetay)


        val name   = detayİsim.text
        val rating = voteDetay.text
        val detail = detayAciklama.text
        val path = imagePath


        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!
        val uid = currentUser!!.uid

        val found: Boolean = arrayList.contains(name)
        if(found == false )
        {
            addtoFavorites()
        }
        else{
            Toast.makeText(this, "HATA", Toast.LENGTH_SHORT).show()
            btLike.isEnabled == false
            btLike.setText("Favorilerde")
        }


    }

    fun addtoFavorites(){

        val name   = detayİsim.text
        val rating = voteDetay.text
        val detail = detayAciklama.text

        val imagePath = intent.getStringExtra("KeyResim")
        Picasso.get()
            .load(imagePath)
            .into(avatarDetay)
        val path = imagePath


        btLike.setOnClickListener {
            val reference = FirebaseDatabase.getInstance().getReference("Movies")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)

            val id = reference.push().key

            val movie = UserMovies(id as String,
                name as String,
                rating as String,
                detail as String,
                path as String)
            reference.child(id).setValue(movie)
            arrayList.add(name)
        }
    }
}

