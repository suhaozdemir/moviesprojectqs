package com.cengizhan.veriekmedeneme2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class activityProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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
}