package com.camp.campingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.camp.campingapp.MyApplication.Companion.auth
import com.camp.campingapp.databinding.ActivityChatMainBinding
import com.camp.campingapp.model.User
import com.camp.campingapp.recycler.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatMainBinding
    lateinit var adapter: UserAdapter

    private lateinit var userList: ArrayList<User>
    private lateinit var auth: FirebaseAuth
    private lateinit var rdb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //초기화
        auth = Firebase.auth
        rdb = Firebase.database.reference
        userList = ArrayList()

        adapter = UserAdapter(this, userList)
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)

        // RecyclerView에 ItemAnimator 추가
        val itemAnimator = DefaultItemAnimator()
        binding.userRecyclerView.itemAnimator = itemAnimator

        binding.userRecyclerView.adapter = adapter

        //사용자 정보 가져오기
        rdb.child("user").addValueEventListener(object :ValueEventListener{
            @SuppressLint("RestrictedApi")
            override fun onDataChange(snapshot: DataSnapshot) {
                for(postSnapshot in snapshot.children){

                    //유저 정보
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if(auth.currentUser?.uid!=currentUser?.uid){
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //실패 시 실행
            }
        })
        // ActionBar에 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



    }//oncreate닫음

    // ActionBar의 뒤로가기 버튼 클릭 시 호출되는 메서드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.log_out){
            auth.signOut()
            val intent = Intent(this@ChatMainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        if (item.itemId == android.R.id.home) {
            onBackPressed() // 이전 화면으로 돌아가기
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.log_out){
//            auth.signOut()
//            val intent = Intent(this@ChatMainActivity, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//            return true
//        }
//        return true
//    }
}