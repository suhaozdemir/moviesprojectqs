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
import kotlinx.android.synthetic.main.activity_register.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val reference = FirebaseDatabase.getInstance().getReference("Users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()


        getuserData()

        bt_Update.setOnClickListener {
            if (txtNewPass.text.isEmpty() || edtxt_Username.text.isEmpty() || edtxt_Password.text.isEmpty()) {
                Toast.makeText(this, "Please fill all empty fields!", Toast.LENGTH_SHORT).show()
            } else {
                changePass()
            }
        }
    }

    private fun changePass() {
        val currentUser = auth.currentUser!!
        val credential = EmailAuthProvider
            .getCredential(currentUser.email!!, edtxt_Password.text.toString())

        val newPass = txtNewPass.text
        val newMail = edtxt_Email.text
        val newName = edtxt_Username.text

        if(newPass.length < 6)
            Toast.makeText(this, "Password must be at least six characters", Toast.LENGTH_SHORT).show()

        currentUser.reauthenticate(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    currentUser!!.updatePassword(newPass.toString())
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                currentUser!!.updateEmail(newMail.toString())
                                reference.child("username").setValue(newName)
                                reference.child("password").setValue(newPass)
                                Toast.makeText(this,
                                    "Your settings has been changed successfully.",
                                    Toast.LENGTH_SHORT).show()
                                auth.signOut()
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                }


            }
    }

    fun getuserData(){
        edtxt_Username.setText(intent.getStringExtra("username"))
        edtxt_Email.setText(intent.getStringExtra("email"))
        edtxt_Password.setText(intent.getStringExtra("password"))
    }
}

