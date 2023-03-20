package com.example.firebasestorage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.firebasestorage.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val reqCode=100
    lateinit var imagePath : Uri
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storage=Firebase.storage
        val ref =storage.reference
        binding.ChooseGalary.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,reqCode)
        }
        binding.Upload.setOnClickListener {
            ref.child("Images/"+UUID.randomUUID().toString())
                .putFile(imagePath)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"Upload true",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext,"Please Upload Image",Toast.LENGTH_SHORT).show()

                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==reqCode){
     imagePath=data!!.data!!
            val bitMap=MediaStore.Images.Media.getBitmap(contentResolver,imagePath)
            binding.img.setImageBitmap(bitMap)
        }
    }
}