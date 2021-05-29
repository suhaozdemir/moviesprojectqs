package com.quadsoft.moviesprojectqs

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder : Notification.Builder
    private val channelID = "com.quadsoft.moviesprojectqs"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btnRegister.setOnClickListener {
        if(txtUsername.text.isEmpty() || txtEmail.text.isEmpty() || txtPassword.text.isEmpty())
            Toast.makeText(this, "Please fill all empty fields!", Toast.LENGTH_SHORT).show()
        else
            registerFun()
        }

        txtBack.setOnClickListener{
           startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    fun registerFun(){
        val username = txtUsername.text.toString()
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val user = User(username,email,password)
                FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .setValue(user)
                notification()
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }


    private fun notification(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,channelID)
                .setContentTitle("MyMovieList")
                .setContentText("Welcome to MyMovieList App!")
                .setSmallIcon(R.drawable.anasayfaikon)

        }else{
            builder = Notification.Builder(this)
                .setContentTitle("MyMovieList")
                .setContentText("Welcome to MyMovieList App!")
                .setSmallIcon(R.drawable.anasayfaikon)

        }
        notificationManager.notify(1234,builder.build())
    }

}