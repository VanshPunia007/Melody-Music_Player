package com.vanshpunia.melody

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.vanshpunia.melody.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = Color.BLACK


        Handler(Looper.getMainLooper()).postDelayed({
            if(Firebase.auth.currentUser == null){
                finish()
                startActivity(Intent(this, SignUpActivity::class.java))
            }else{
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }, 3000)
    }
}