package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val reference = FirebaseDatabase.getInstance().getReference("Users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()


        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val username = snapshot.child("username").getValue().toString()
                val email = snapshot.child("email").getValue().toString()
                val password = snapshot.child("password").getValue().toString()
                edtxt_Email.setText(email)
                edtxt_Username.setText(username)
                txtShowName.setText(username)
                edtxt_Password.setText(password)
            }


            override fun onCancelled(error: DatabaseError) {

            }

        })
        bt_Update.setOnClickListener {
            changePass()
        }
    }


    private fun changePass() {
        val currentUser = auth.currentUser!!
        val credential = EmailAuthProvider
            .getCredential(currentUser.email!!, edtxt_Password.text.toString())

        val newPass = newPass.text.toString()
        val newMail = edtxt_Email.text.toString()
        val newName = edtxt_Username.text.toString()

        currentUser.reauthenticate(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Auth Ok", Toast.LENGTH_SHORT).show()
                    currentUser!!.updatePassword(newPass)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                currentUser!!.updateEmail(newMail)
                                reference.child("email").setValue(newMail)
                                reference.child("username").setValue(newName)
                                reference.child("password").setValue(newPass)
                                reference.child("id").child("deneme").setValue("merhaba")
                                Toast.makeText(this, "Your password has been changed successfully.", Toast.LENGTH_SHORT).show()
                                auth.signOut()
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                }


            }
    }
}
