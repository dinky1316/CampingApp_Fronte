package com.camp.campingapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.camp.campingapp.databinding.ActivityChatBinding
import com.camp.campingapp.model.Message
import com.camp.campingapp.recycler.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String

    //바인딩 객체
    private lateinit var binding: ActivityChatBinding

    private lateinit var auth: FirebaseAuth //인증 객체
    private lateinit var rdb:DatabaseReference //DB 객체

    private lateinit var receiverRoom: String //받는 대화방
    private lateinit var senderRoom: String // 보낸 대화방

    private lateinit var messageList: ArrayList<Message>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        초기화
        messageList = ArrayList()
        val messageAdapter: MessageAdapter = MessageAdapter(this, messageList)

        //리사이클러뷰
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        //넘어온 데이터 변수에 담기
        receiverName=intent.getStringExtra("name").toString()
        receiverUid=intent.getStringExtra("uId").toString()

        auth = FirebaseAuth.getInstance()
        rdb = FirebaseDatabase.getInstance().reference

        //접속자 uid
        val senderUid = auth.currentUser?.uid

        //보낸이방
        senderRoom = receiverUid + senderUid

        //받는이방
        receiverRoom = senderUid + receiverUid

        //액션바에 상대방 이름 보여주기
        supportActionBar?.title = receiverName

        //메시지 전송 버튼 이벤트
        binding.sendBtn.setOnClickListener {

            val message = binding.messageEdit.text.toString()
            val messageObject = Message(message, senderUid)

            //데이터 저장
            rdb.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    //저장 성공하면
                    rdb.child("chats").child(receiverRoom).child("messages").push()
                        .setValue(messageObject)
                }

            //입력값 초기화
            binding.messageEdit.setText("")
        }

        //메시지 가져오기
        rdb.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (postSnapshot in snapshot.children){

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    //적용
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
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