package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class activityProfile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        bottomBarsetup()

    }


    private fun bottomBarsetup() {
        nav_view.setOnNavigationItemSelectedListener{

            when(it.itemId){
                R.id.arsiv -> {
                    val intent = Intent(this,arsivActivity::class.java)
                    startActivity(intent)

                }
                R.id.anasayfa -> {
                    val intent2 = Intent(this,MainActivity::class.java)
                    startActivity(intent2)

                }
            }
            true

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //---------ÇIKIŞ İŞLEMİ---------
        if (item.itemId == R.id.logout){
            auth.signOut()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this,"Başarıyla Çıkış Yapıldı",Toast.LENGTH_LONG).show()
        }else if(item.itemId == R.id.editProfile){
            val intent = Intent(this,EditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}