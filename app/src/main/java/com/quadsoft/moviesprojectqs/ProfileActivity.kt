package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.check_internet_dialog.view.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val reference = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        checkConnection()
        auth = FirebaseAuth.getInstance()

            bottomBarsetup()
            getuserData()

        bt_EditProfile.setOnClickListener{
            editProfile()
        }

        bt_Logout.setOnClickListener {
            logout()
        }
    }


    private fun getuserData(){

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val username = snapshot.child("username").getValue().toString()
                val email = snapshot.child("email").getValue().toString()
                val password = snapshot.child("password").getValue().toString()
                txtv_Name.setText(username)
                txt_Email.setText(email)
                txt_Password.setText(password)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun editProfile(){
        val it_uData = Intent(this,EditProfileActivity::class.java)
        it_uData.putExtra("username",txtv_Name.text)
        it_uData.putExtra("email",txt_Email.text)
        it_uData.putExtra("password",txt_Password.text)
        startActivity(it_uData)
    }

    private fun logout(){
        auth.signOut()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
        Toast.makeText(this,"Come back again soon!",Toast.LENGTH_LONG).show()
    }



    private fun bottomBarsetup() {
        nav_view.setOnNavigationItemSelectedListener{

            when(it.itemId){
                R.id.navArchive -> {
                    val intent = Intent(this,ArchiveActivity::class.java)
                    startActivity(intent)

                }
                R.id.navHome -> {
                    val intent = Intent(this,FirstActivity::class.java)
                    startActivity(intent)

                }
            }
            true

        }
    }

    private fun checkConnection(){
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