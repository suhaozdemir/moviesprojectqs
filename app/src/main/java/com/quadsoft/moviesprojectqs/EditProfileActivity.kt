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
        getuserData()

        bt_Update.setOnClickListener {
            if (txtNewPass.text.isEmpty()) {
                Toast.makeText(this, "Please fill all empty", Toast.LENGTH_SHORT).show()
            } else {
                changePass()
            }
        }
    }

    private fun changePass() {
        val currentUser = auth.currentUser!!

        val credential = EmailAuthProvider
            .getCredential(currentUser.email!!, edtxt_Password.text.toString())

        val newPass = txtNewPass.text.toString()
        val newMail = edtxt_Email.text
        val newName = edtxt_Username.text

        if(newPass.length < 6)
            Toast.makeText(this, "Password must be at least six characters", Toast.LENGTH_SHORT).show()
        if(newPass == edtxt_Password.text.toString())
            Toast.makeText(this, "New password must be different from old password", Toast.LENGTH_SHORT).show()
        else{
            currentUser.reauthenticate(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        currentUser!!.updatePassword(newPass.toString())
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    currentUser!!.updateEmail(newMail.toString())
                                    reference.child("email").setValue(newMail.toString())
                                    reference.child("username").setValue(newName.toString())
                                    reference.child("password").setValue(newPass.toString())
                                    Toast.makeText(this,
                                        "Your information has been changed successfully.",
                                        Toast.LENGTH_SHORT).show()
                                    auth.signOut()
                                    val intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                    }


                }}
    }

    fun getuserData(){
        edtxt_Username.setText(intent.getStringExtra("username"))
        edtxt_Email.setText(intent.getStringExtra("email"))
        edtxt_Password.setText(intent.getStringExtra("password"))
    }
}