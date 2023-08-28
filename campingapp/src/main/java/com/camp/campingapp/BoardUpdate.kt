package com.camp.campingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.camp.campingapp.databinding.ActivityBoardUpdateBinding
import com.camp.campingapp.util.dateToString
import java.util.Date

class BoardUpdate : AppCompatActivity() {
    lateinit var binding: ActivityBoardUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBoardUpdateBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val docId = intent.getStringExtra("DocId")
        val title = intent.getStringExtra("BoardTitle")
        val content = intent.getStringExtra("BoardContent")
        val date = intent.getStringExtra("BoardDate")

        binding.BoardDate.text = date
        binding.regTitle.setText(title)
        binding.regContent.setText(content)

        // 수정
        binding.BoardModify.setOnClickListener {
            val data = mapOf(
                "title" to binding.regTitle.text.toString(),
                "content" to binding.regTitle.text.toString(),
                "date" to dateToString(Date())
            )
            if (docId != null) {
                MyApplication.db.collection("Boards").document(docId).update(data)
            }

            // Board 화면으로 이동
            val boardIntent = Intent(this, Board::class.java)
            startActivity(boardIntent)
            finish() // 현재 화면 종료
        }

        // 수정 취소
        binding.BoardCancel.setOnClickListener {
            finish()
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