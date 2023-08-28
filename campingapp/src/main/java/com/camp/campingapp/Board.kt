package com.camp.campingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.camp.campingapp.databinding.ActivityBoardBinding
import com.camp.campingapp.model.BoardData
import com.camp.campingapp.recycler.BoardAdapter
import com.camp.campingapp.util.myCheckPermission

class Board : AppCompatActivity() {
    private lateinit var binding: ActivityBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myCheckPermission(this)
        makeRecyclerView()

        binding.add.setOnClickListener {
            startActivity(Intent(this, BoardWrite::class.java))
        }
        // ActionBar에 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



    }//oncreate닫음

    // ActionBar의 뒤로가기 버튼 클릭 시 호출되는 메서드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // 이전 화면으로 돌아가기
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeRecyclerView() {
        MyApplication.db.collection("Boards")
            .get()
            .addOnSuccessListener { result ->
                val itemList = result.map { document ->
                    val item = document.toObject(BoardData::class.java)
                    item.docId = document.id
                    item
                }
                binding.boardRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.boardRecyclerView.adapter = BoardAdapter(this, itemList)

                // 각 아이템의 imageUrl을 가져와서 이미지를 로드합니다.
//                for (item in itemList) {
//                    val imageUrl = item.imageUrl
//                    if (imageUrl != null) {
//                        // imageUrl을 사용하여 Glide를 통해 이미지를 로드합니다.
//                        Glide.with(this)
//                            .load(imageUrl)
//                            .into(binding.imageView) // 여기서 imageView는 단일 이미지를 표시할 ImageView입니다.
//                    }
//                }
            }
            .addOnFailureListener { exception ->
                Log.d("kkang", "error.. getting document..", exception)
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }
}