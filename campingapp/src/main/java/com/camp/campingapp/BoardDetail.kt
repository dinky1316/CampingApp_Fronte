package com.camp.campingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.camp.campingapp.databinding.ActivityBoardDetailBinding
import com.camp.campingapp.model.BoardData
import com.google.firebase.firestore.FieldValue

class BoardDetail : AppCompatActivity() {
    lateinit var binding: ActivityBoardDetailBinding

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

//        if (docId != null) {
//            MyApplication.db.collection("Boards").document(docId).collection("Comments")
//                .get()
//                .addOnSuccessListener { result ->
//                    val itemList = mutableListOf<BoardData>()
//                    for (document in result) {
//                        val item = document.toObject(BoardData::class.java)
//                        item.comment=document.id
//                        itemList.add(item)
//                    }
//                    binding.commentRecyclerView.layoutManager = LinearLayoutManager(this)
//                    binding.commentRecyclerView.adapter = CommentAdapter(this, itemList)
//                }
//        }



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
            }
            finish()
            overridePendingTransition(0, 0) //인텐트 효과 없애기
            val intent = intent //인텐트
            startActivity(intent) //액티비티 열기
            overridePendingTransition(0, 0) //인텐트 효과 없애기
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

        // 댓글 등록
        binding.CommentWrite.setOnClickListener {
            val commentData = mapOf(
                "comment" to binding.BoardComment.text.toString(),
                "timestamp" to FieldValue.serverTimestamp()
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
    }
//    private fun commentRecycelerView() {
//        val docId = intent.getStringExtra("DocId")
//        if (docId != null) {
//            MyApplication.db.collection("Boards").document(docId).collection("Comments")
//                .get()
//                .addOnSuccessListener { result ->
//                    val itemList = mutableListOf<BoardData>()
//                    for (document in result) {
//                        val item = document.toObject(BoardData::class.java)
//                        item.docId = document.id
//                        itemList.add(item)
//                    }
//                    binding.commentRecyclerView.layoutManager = LinearLayoutManager(this)
//                    binding.commentRecyclerView.adapter = CommentAdapter(this, itemList)
//                }
//                .addOnFailureListener { exception ->
//                    // 파이어베이스 콘솔에 해당 서비스의 권한(규칙을 설정 안했을 때 나오는 문구.)
//                    Log.d("kkang", "error.. getting document..", exception)
//                    Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }



}