package com.vanshpunia.melody

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
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
            finish()
            startActivity(Intent(this, SignUpActivity::class.java))
        }, 3000)
    }
}