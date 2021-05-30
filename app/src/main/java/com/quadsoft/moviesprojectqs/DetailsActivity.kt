package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.check_internet_dialog.view.*

val reference = FirebaseDatabase.getInstance().getReference("Movies")
    .child(FirebaseAuth.getInstance().currentUser!!.uid)

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        checkConnection()

        btRemove.isEnabled = false

        getdetailData()
        checkMovie()

        btFavourite.setOnClickListener{
            addtoFavorites()
            btRemove.isEnabled = true
        }
        btRemove.setOnClickListener{
            deleteMovie()
            btFavourite.isEnabled = true
            btFavourite.setText("Add Favorıte")
            btRemove.isEnabled = false
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
        val id = name

            val movie = UserMovies(id as String,
                name as String,
                rating as String,
                detail as String,
                path as String)
            reference.child(id).setValue(movie)
    }

    fun deleteMovie(){
        val name   = txt_dName.text

        val id = name
        reference.child(id as String).removeValue()

        val intent = Intent(this, ArchiveActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getdetailData(){
        txt_dName.text = intent.getStringExtra("it_movieName")
        txt_dRating.text = intent.getStringExtra("it_movieRating")
        txt_dDetail.text = intent.getStringExtra("it_movieDetail")

        val rating = txt_dRating.text.toString()
        val a = rating.toFloat()

        d_ratingBar.rating = a/2

        val imagePath = intent.getStringExtra("it_movieImage")
        Picasso.get()
            .load(imagePath)
            .into(img_dImage)
    }

    fun checkMovie(){
        val name = txt_dName.text
        val ref = reference.child(name as String)
        var tempName : String? = null

        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for(item in snapshot.children){
                     tempName = snapshot.key

                if(name == tempName){
                    btFavourite.setText("Favorıte")
                    btFavourite.isEnabled=false
                    btRemove.isEnabled = true
                }
                }
            }

            override fun onCancelled(error: DatabaseError) {

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

