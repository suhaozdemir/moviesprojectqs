package com.quadsoft.moviesprojectqs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

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

    }
}