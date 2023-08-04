package com.camp.campingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.camp.campingapp.databinding.ActivityMainBinding
import com.camp.campingapp.model.ItemData
import com.camp.campingapp.recycler.MyAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wrtie.setOnClickListener {
            startActivity(Intent(this, WriteActivity::class.java))
        }

    }

    private fun makeRecyclerView() {
        MyApplication.db.collection("news").get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = MyAdapter(this, itemList)
            }
            .addOnFailureListener{exception ->
                Log.d("kkang", "error.. getting document..", exception)
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()


            }
    }
}