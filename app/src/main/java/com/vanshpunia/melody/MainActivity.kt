package com.vanshpunia.melody

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.vanshpunia.melody.adapters.CategoryAdaptor
import com.vanshpunia.melody.databinding.ActivityMainBinding
import com.vanshpunia.melody.models.Category
import com.vanshpunia.melody.utils.CATEGORY

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var categoryList = ArrayList<Category>()
    lateinit var categoryAdaptor: CategoryAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(binding.root)

        setCategories()

    }

    private fun setCategories() {
        Firebase.firestore.collection(CATEGORY).get().addOnSuccessListener {
            categoryList.clear()
            var tempList = ArrayList<Category>()
            for (i in it.documents) {
                var category: Category = i.toObject<Category>()!!
                tempList.add(category)
            }
            categoryList.addAll(tempList)
            categoryAdaptor = CategoryAdaptor(this@MainActivity, categoryList)
            binding.categoryRv.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.categoryRv.adapter = categoryAdaptor

            categoryAdaptor.notifyDataSetChanged()
        }
    }
}