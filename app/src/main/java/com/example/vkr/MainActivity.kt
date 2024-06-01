package com.example.vkr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    lateinit var btn: Button
    lateinit var weight: EditText
    lateinit var height: EditText
    lateinit var age: EditText
    lateinit var gender: Spinner
    lateinit var physicactiv: Spinner
    var result: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun toMain(view: View) {
        btn = findViewById(R.id.button)
        btn.setOnClickListener {
            Calculate()
            val intent = Intent(this, main_page::class.java)
            intent.putExtra("cal",result)
            startActivity(intent)
        }
    }

    fun Calculate() {
        weight = findViewById(R.id.editTextNumber)
        height = findViewById(R.id.editTextNumberSigned)
        age = findViewById(R.id.editTextNumber2)
        gender = findViewById(R.id.spinner)
        physicactiv = findViewById(R.id.spinner2)

        result += (weight.text.toString().toInt()) * 10 + (height.text.toString().toInt()) * 6.25
        val languages = resources.getStringArray(R.array.gender)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, languages
        )
        gender.adapter = adapter
        val phys = resources.getStringArray(R.array.PhysycalLoads)
        val adapters = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,languages
        )
        physicactiv.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val results = performCalculation(position, result)
                result*=results
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ничего не делать
            }

            private fun performCalculation(position: Int, results: Double): Double {
                return when (position) {
                    0 ->  1.2
                    1 -> 1.38
                    2 -> 1.46
                    3 ->  1.55
                    4 -> 1.64
                    5 ->  1.73
                    6 -> 1.9
                    else -> results
                }
            }
        }

        gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val results = performCalculation(position, result)
                result+=results
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ничего не делать
            }

            private fun performCalculation(position: Int, results: Double): Double {
                return when (position) {
                    0 -> result + 5
                    1 -> result - 161
                    else -> results

                }
            }
        }
    }
}