package com.vanshpunia.melody

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.vanshpunia.melody.databinding.ActivityProfileBinding
import com.vanshpunia.melody.models.User
import com.vanshpunia.melody.utils.USER_NODE

class ProfileActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = Color.BLACK
        Firebase.firestore.collection(USER_NODE)
            .document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                val user = it.toObject<User>()!!
                binding.userName.text = user.name
                Glide.with(this@ProfileActivity)
                    .load(user.image)
                    .circleCrop()
                    .into(binding.userImage)
            }

        Glide.with(this@ProfileActivity).load(R.drawable.profile_gif)
            .circleCrop()
            .into(binding.gif)

        binding.backBtn.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            finish()
        }

        binding.logoutBtn.setOnClickListener{
            MyExoPlayer.releasePlayer() //Call the releasePlayer function to stop music
            Firebase.auth.signOut()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }
}