package com.quadsoft.moviesprojectqs

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.check_internet_dialog.view.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val reference = FirebaseDatabase.getInstance().getReference("Users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)
    val reference2 = FirebaseDatabase.getInstance().getReference("Movies")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        checkConnection()

        auth = FirebaseAuth.getInstance()

        getuserData()

        bt_Update.setOnClickListener {
            if (txtNewPass.text.isEmpty()) {
                Toast.makeText(this, "Please fill all empty fields!", Toast.LENGTH_SHORT).show()
            } else {
                updateDialog()
            }
        }

        bt_Back.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        bt_Delete.setOnClickListener{
            deleteDialog()
        }

    }

    private fun changePass() {
        val currentUser = auth.currentUser!!
        val credential = EmailAuthProvider
            .getCredential(currentUser.email!!, edtxt_Password.text.toString())

        val newPass = txtNewPass.text.toString()
        val newMail = edtxt_Email.text.toString()
        val newName = edtxt_Username.text.toString()

        if(newPass.length < 6)
            Toast.makeText(this, "Password must be at least six characters", Toast.LENGTH_SHORT).show()
        if(newPass == edtxt_Password.text.toString())
            Toast.makeText(this, "New password must be different from old password", Toast.LENGTH_SHORT).show()
        else{
            currentUser.reauthenticate(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        currentUser!!.updatePassword(newPass)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    currentUser!!.updateEmail(newMail)
                                    reference.child("email").setValue(newMail)
                                    reference.child("username").setValue(newName)
                                    reference.child("password").setValue(newPass)
                                    Toast.makeText(this,
                                        "Your information has been changed successfully.",
                                        Toast.LENGTH_SHORT).show()
                                    auth.signOut()
                                    startActivity(Intent(this, HomeActivity::class.java))
                                    finish()
                                }else
                                    Toast.makeText(this, "Error! Please try again later.", Toast.LENGTH_SHORT).show()
                            }
                    }
                }}
    }

    fun getuserData(){
        edtxt_Username.setText(intent.getStringExtra("username"))
        edtxt_Email.setText(intent.getStringExtra("email"))
        edtxt_Password.setText(intent.getStringExtra("password"))
    }

    fun deleteAccount(){
        val currentUser = auth.currentUser!!
        val credential = EmailAuthProvider
            .getCredential(currentUser.email!!, edtxt_Password.text.toString())

        currentUser.reauthenticate(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    currentUser!!.delete()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                reference.removeValue()
                                reference2.removeValue()
                                Toast.makeText(this, "Your Account Has Been Deleted", Toast.LENGTH_SHORT).show()
                                auth.signOut()
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            }else{
                                Toast.makeText(this, "Error! Please try again later.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, ProfileActivity::class.java))
                            }
                        }
                }
            }
    }

    fun deleteDialog(){
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Warning")
        alert.setMessage("Are you sure you want to delete your account?")
        alert.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, id ->
                deleteAccount()
            })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->

                })

        alert.create().show()
    }

    fun updateDialog(){
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Warning")
        alert.setMessage("Are you sure you want to update your account information?")
        alert.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, id ->
                changePass()
            })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->

                })

        alert.create().show()
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

