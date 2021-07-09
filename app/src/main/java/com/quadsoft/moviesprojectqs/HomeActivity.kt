package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.check_internet_dialog.view.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        checkConnection()

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this,FirstActivity::class.java))
            Toast.makeText(this, "Welcome ${currentUser.email.toString()}",Toast.LENGTH_LONG).show()
            finish()
        }

        btnLogin.setOnClickListener {
        if (txtEmail.text.isEmpty() || txtPassword.text.isEmpty())
            Toast.makeText(this, "Please fill all empty fields!", Toast.LENGTH_SHORT).show()
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
                startActivity(Intent(this,FirstActivity::class.java))
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
