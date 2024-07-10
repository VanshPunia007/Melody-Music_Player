package com.vanshpunia.melody

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vanshpunia.melody.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = Color.BLACK
        setContentView(binding.root)

        binding.login.text = Html.fromHtml(
            "<font color=${Color.WHITE}>Already have an account? </font>" +
                    "<font color=#1E88E5> Login</font>"
        )
    }
}