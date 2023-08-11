package com.camp.campingapp.recycler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.camp.campingapp.BoardDetail
import com.camp.campingapp.databinding.BoardItemBinding
import com.camp.campingapp.model.BoardData

class BoardViewHolder(val binding: BoardItemBinding) : RecyclerView.ViewHolder(binding.root) {

}

class BoardAdapter(val context: Context, val itemList: MutableList<BoardData>): RecyclerView.Adapter<BoardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BoardViewHolder(BoardItemBinding.inflate(layoutInflater))

    }
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            itemTitleView.text=data.title
            itemContentView.text=data.content
            itemDateView.text=data.date
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BoardDetail::class.java)
            intent.putExtra("DocId", data.docId)
            intent.putExtra("BoardTitle", data.title)
            intent.putExtra("BoardContent", data.content)
            intent.putExtra("BoardDate", data.date)
            intent.putExtra("Comment", data.comment)
            context.startActivity(intent)
        }
    }
}