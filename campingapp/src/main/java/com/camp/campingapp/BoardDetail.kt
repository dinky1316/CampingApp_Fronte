package com.camp.campingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.camp.campingapp.databinding.ActivityBoardDetailBinding
import com.camp.campingapp.model.BoardData
import com.camp.campingapp.recycler.CommentAdapter
import com.google.firebase.firestore.FieldValue
import java.text.SimpleDateFormat

class BoardDetail : AppCompatActivity() {
    lateinit var binding: ActivityBoardDetailBinding
    data class comment(val comment : String, val time : String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val docId = intent.getStringExtra("DocId")
        val title = intent.getStringExtra("BoardTitle")
        val content = intent.getStringExtra("BoardContent")
        val date = intent.getStringExtra("BoardDate")

        binding.BoardTitle.text = title
        binding.BoardDate.text = date
        binding.BoardContent.text = content

        var commentlist = mutableListOf<comment>()
        var count = 0
        if (docId != null) {
            MyApplication.db.collection("Boards").document(docId).collection("Comments")
                .get()
                .addOnSuccessListener { result ->
                    val itemList = mutableListOf<BoardData>()
                    for (document in result) {
                        //val item = document.toObject(BoardData::class.java)
                        //item.comment=document.id
                        //itemList.add(item)
                        commentlist.add(comment(document.data.get("comment").toString(), document.data.get("timestamp").toString()))
                        count++
                        if(result.size() == count) {
                            Log.d("test", "$commentlist")
                            binding.commentRecyclerView.layoutManager = LinearLayoutManager(this)
                            binding.commentRecyclerView.adapter = CommentAdapter(this, commentlist)
                        }
                    }

                }
        }



        // 수정
        binding.BoardModify.setOnClickListener {
            val intent = Intent(this, BoardUpdate::class.java)
            intent.putExtra("DocId", docId)
            intent.putExtra("BoardTitle", title)
            intent.putExtra("BoardContent", content)
            intent.putExtra("BoardDate", date)
            overridePendingTransition(0, 0) //인텐트 효과 없애기
            startActivity(intent) //액티비티 열기
            overridePendingTransition(0, 0) //인텐트 효과 없애기
            finish()
        }

        // 삭제
        binding.BoardDelete.setOnClickListener {
            if (docId != null) {
                MyApplication.db.collection("Boards").document(docId)
                    .delete()
                val intent = intent //인텐트
                startActivity(intent) //액티비티 열기
                overridePendingTransition(0, 0) //인텐트 효과 없애기
                finish() //인텐트 효과 없애기
            }
        }

        // 등록한 이미지 가져 오기
        val imgRef = MyApplication.storage.reference.child("images/${docId}.jpg")
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(binding.ImageView)
            }
        }

        val datenow = SimpleDateFormat("yyyy-MM-dd HH:mm")
        // 댓글 등록
        binding.CommentWrite.setOnClickListener {
            val commentData = mapOf(
                "comment" to binding.BoardComment.text.toString(),
                "timestamp" to datenow.format(System.currentTimeMillis()).toString()
            )
            if (docId != null) {
                MyApplication.db.collection("Boards").document(docId)
                    .collection("Comments").add(commentData)
            }
            finish()
            overridePendingTransition(0, 0) //인텐트 효과 없애기
            val intent = intent //인텐트
            startActivity(intent) //액티비티 열기
            overridePendingTransition(0, 0) //인텐트 효과 없애기
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
}