package com.example.vkr

import android.app.Instrumentation.ActivityResult
import android.graphics.drawable.Drawable
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.example.vkr.ml.MobilenetV110224Quant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class AddProduct : AppCompatActivity() {
    private lateinit var permissionLauncher:ActivityResultLauncher<String>
    private lateinit var cameraOpenId:Button
    lateinit var clickImageid:ImageView
    lateinit var res:TextView
    lateinit var product:EditText
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)
        registerPremission()
        check()
        res=findViewById(R.id.res)
        product=findViewById(R.id.FindProduct)

        cameraOpenId=findViewById(R.id.button4)
        clickImageid=findViewById(R.id.click_image)
        cameraOpenId.setOnClickListener{
            val cameraInetnt=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraInetnt, pic_id)

        }

    }

    fun toProductInfo(view: View)
    {
        val intent = Intent(this,ProductInformation::class.java)
        intent.putExtra("ProductName",product.text.toString())
        startActivity(intent)
    }

    private fun check() {
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED -> {

            }

            else -> {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }

    }
    private fun registerPremission(){
        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                Toast.makeText(this,"Camera on",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this,"Camera off",Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== pic_id){
            val photo=data!!.extras!!["data"] as Bitmap
            clickImageid.setImageBitmap(photo)
        }
    }
    companion object{
        private const val pic_id=123
    }
    fun predict(view: View.OnClickListener){

        val imageView: ImageView = findViewById(R.id.click_image)

        val drawable: Drawable? = imageView.drawable

        if (drawable != null) {
            val bitmap: Bitmap = when (drawable) {
                is BitmapDrawable -> {
                    drawable.bitmap
                }

                else -> {
                    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    bitmap
                }
            }
            val model = MobilenetV110224Quant.newInstance(this)

// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)


            inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer())

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            res.setText(getMax(outputFeature0.floatArray))
// Releases model resources if no longer used.
            model.close()
        }

    }
    fun getMax(arr: FloatArray): Int {
        var max = 0
        for (i in arr.indices) {
            if (arr[i] > arr[max]) max = i
        }
        return max
    }
}