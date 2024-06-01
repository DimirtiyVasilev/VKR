package com.example.vkr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class main_page : AppCompatActivity() {
    lateinit var Text:TextView
    var cal:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)
        Text=findViewById(R.id.textView2)
        val sharedPreferences = getSharedPreferences("CalculatorPrefs", Context.MODE_PRIVATE)
        cal = sharedPreferences.getFloat("cal", 0.0f).toDouble()
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getDouble("cal", cal)
            cal = value
        }

        Text.text = "0/$cal"
    }
    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("CalculatorPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("cal", cal.toFloat())
        editor.apply()
    }
    fun toAdd(view: View)
    {
        val intent = Intent(this, AddProduct::class.java)
        startActivity(intent)
    }
    fun toDish(view: View)
    {
        val intent = Intent(this,ProductInformation::class.java)
        startActivity(intent)
    }
}