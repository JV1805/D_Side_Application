package com.example.actualenvironment

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import android.content.ContentValues.TAG
//import androidx.navigation.ui.AppBarConfiguration
import android.widget.Toast
import com.example.d_side_authentication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        // Write a message to the database
        auth = Firebase.auth
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User")

        myRef.setValue("Victor is testing further")


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("Sent", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Failed", "Failed to read value.", error.toException())
            }
        })
        createAccount("XX_testUSER@gmail.com", "fortnite12")  // make your own account here?
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //signIn("barackobama42@gmail.com", "fortnite")       // THE IMPORTANT BITS

    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun reload() {          /// Probably not that important

    }

    /// THE IMPORTANT BITS
    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:                    success")
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
        // [END create_user_with_email]
    }

    /// THE IMPORTANT BITS
    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
                        val name = user.displayName
                        val email = user.email
                        val photoUrl = user.photoUrl

                        // Check if user's email is verified
                        val emailVerified = user.isEmailVerified

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        val uid = user.uid
                    }
                    Log.w("IDK ", user?.email.toString())

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
}