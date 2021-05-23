package com.quadsoft.moviesprojectqs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    val reference = FirebaseDatabase.getInstance().getReference("Users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val username = snapshot.child("username").getValue().toString()
                val email = snapshot.child("email").getValue().toString()
                val password = snapshot.child("password").getValue().toString()
                edtxt_Email.setText(email)
                edtxt_Email.keyListener = null
                edtxt_Username.setText(username)
                txtShowName.setText(username)
                edtxt_Password.setText(password)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



    }


    fun updateFun(view: View){

    }
}