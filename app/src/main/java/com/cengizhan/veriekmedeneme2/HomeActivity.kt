package com.cengizhan.veriekmedeneme2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        //---------GİRİŞ YAPMIŞ VE LOGOUT YAPMAYAN KULLANICI---------
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Tekrar Hoşgeldin: ${currentUser.email.toString()}",Toast.LENGTH_LONG).show()
            finish()
        }

        //Label ile register ekranına gitme
        txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //---------GİRİŞ İŞLEMLERİ---------
    fun loginFun(view: View){
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){

                val loggedUser = auth.currentUser?.email.toString()
                Toast.makeText(this, "Hoşgeldin: ${loggedUser}",Toast.LENGTH_LONG).show()
                progressBar.visibility = View.VISIBLE
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception ->
            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }
}