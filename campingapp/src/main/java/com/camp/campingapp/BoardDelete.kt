package com.camp.campingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.camp.campingapp.databinding.ActivityBoardDeleteBinding

class BoardDelete : AppCompatActivity() {
    lateinit var binding: ActivityBoardDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBoardDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val DocId = intent.getStringExtra("DocId")

        if (DocId != null) {
            deleteDatabase(DocId)
        } else {
            Log.e("BoardDelete", "Error")
        }
    }

}