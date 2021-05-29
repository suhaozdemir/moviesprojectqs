package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,FirstActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Welcome ${currentUser.email.toString()}",Toast.LENGTH_LONG).show()
            finish()
        }

        btnLogin.setOnClickListener {
        if (txtEmail.text.isEmpty() || txtPassword.text.isEmpty())
            Toast.makeText(this, "Please fill al empty fields", Toast.LENGTH_SHORT).show()
        else
            loginFun()
        }


        btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }


    fun loginFun(){
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){

                val loggedUser = auth.currentUser?.email.toString()
                Toast.makeText(this, "Welcome ${loggedUser}",Toast.LENGTH_LONG).show()
                val intent = Intent(this,FirstActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception ->
            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }
    fun onClickHelp(view: View){
        val intent = Intent(applicationContext,HelpActivity::class.java)
        startActivity(intent)
    }
}
