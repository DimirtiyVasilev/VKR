package com.example.vkr

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Product(
    val name: String,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val calories: Double
)

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "products.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "products_info"
        const val COLUMN_NAME = "Наименование"
        const val COLUMN_CALORIES = "Калории"
        const val COLUMN_FATS = "Жиры"
        const val COLUMN_PROTEINS = "Белки"
        const val COLUMN_CARBOHYDRATES = "Углеводы"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Ничего не делать, так как база данных уже существует
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Ничего не делать, так как база данных уже существует
    }

    fun getProductByName(name: String): Product? {
        val db = this.readableDatabase
        val cursor = db.query(
            "products_info",
            arrayOf("Белки", "Жиры", "Углеводы", "Калории"),
            "Наименование = ?",
            arrayOf(name),
            null,
            null,
            null
        )
        var product: Product? = null
        if (cursor.moveToFirst()) {
            val proteins = cursor.getDouble(cursor.getColumnIndexOrThrow("Белки"))
            val fats = cursor.getDouble(cursor.getColumnIndexOrThrow("Жиры"))
            val carbohydrates = cursor.getDouble(cursor.getColumnIndexOrThrow("Углеводы"))
            val calories = cursor.getDouble(cursor.getColumnIndexOrThrow("Калории"))
            product = Product(name, proteins, fats, carbohydrates, calories)
        }
        cursor.close()
        return product
    }
}


