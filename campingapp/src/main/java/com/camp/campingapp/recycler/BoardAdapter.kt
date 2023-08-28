package com.camp.campingapp.recycler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.camp.campingapp.BoardDetail
import com.camp.campingapp.databinding.BoardItemBinding
import com.camp.campingapp.model.BoardData

class BoardViewHolder(val binding: BoardItemBinding) : RecyclerView.ViewHolder(binding.root)

class BoardAdapter(private val context: Context, private val itemList: List<BoardData>) :
    RecyclerView.Adapter<BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BoardItemBinding.inflate(layoutInflater, parent, false)
        return BoardViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val data = itemList[position]

        with(holder.binding) {
            itemTitleView.text = data.title
            itemContentView.text = data.content
            itemDateView.text = data.date
            itemUsernameView.text = data.username // Add this line for username

            val imageUrl = data.imageUrl
            if (imageUrl != null) {
                // imageUrl을 사용하여 Glide를 통해 이미지를 로드하고 해당 아이템의 이미지뷰에 설정합니다.
                Glide.with(context)
                    .load(imageUrl)
                    .into(itemImageView) // itemImageView는 BoardItemBinding에서 선언한 ImageView ID입니다.
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BoardDetail::class.java).apply {
                putExtra("DocId", data.docId)
                putExtra("BoardTitle", data.title)
                putExtra("BoardContent", data.content)
                putExtra("BoardDate", data.date)
                putExtra("Comment", data.comment)
            }
            context.startActivity(intent)
        }
    }
}
