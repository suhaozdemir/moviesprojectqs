package com.quadsoft.moviesprojectqs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val reference = FirebaseDatabase.getInstance().getReference("Users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
            bottomBarsetup()
            getuserData()

        bt_EditProfile.setOnClickListener{
            editProfile()
        }
    }


    private fun getuserData(){

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val username = snapshot.child("username").getValue().toString()
                val email = snapshot.child("email").getValue().toString()
                val password = snapshot.child("password").getValue().toString()
                txt_Name.setText(username)
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
        it_uData.putExtra("username",txt_Name.text)
        it_uData.putExtra("email",txt_Email.text)
        it_uData.putExtra("password",txt_Password.text)
        startActivity(it_uData)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            auth.signOut()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this,"Başarıyla Çıkış Yapıldı",Toast.LENGTH_LONG).show()
        }else if(item.itemId == R.id.editProfile){
            val intent = Intent(this,EditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
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
}