package com.example.vkr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class ProductInformation : AppCompatActivity() {

    lateinit var name: String
    lateinit var calories_res:TextView
    lateinit var protein_res:TextView
    lateinit var fat_res:TextView
    lateinit var carbohydrates_res:TextView
    lateinit var _weight:EditText
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_information)

        calories_res=findViewById(R.id.calories)
        protein_res=findViewById(R.id.protein)
        fat_res=findViewById(R.id.fat)
        carbohydrates_res=findViewById(R.id.carbohydrates)
        _weight=findViewById(R.id.weight)
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("ProductName", name)
            name = value
        }
        dbHelper = DBHelper(this)
        Calc(name)
    }
    fun Calc(name:String)
    {
        var temp = _weight.text.toString().toDouble()/100
        var product = dbHelper.getProductByName(name)
        if(product!=null)
        {
            var cal=product.calories*temp
            var fat = product.fats*temp
            var car = product.carbohydrates*temp
            var prot = product.proteins*temp
            calories_res.setText(cal.toString())
            fat_res.setText(fat.toString())
            protein_res.setText(prot.toString())
            carbohydrates_res.setText(car.toString())
        }
    }

}