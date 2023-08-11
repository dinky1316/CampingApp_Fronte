package com.camp.campingapp.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.camp.campingapp.TourActivity
import com.camp.campingapp.TourDetailActivity
import com.camp.campingapp.model.TourList
import com.camp.campingapp.databinding.ItemRetrofitBinding



class TourViewHolder(val binding: ItemRetrofitBinding): RecyclerView.ViewHolder(binding.root)

class TourAdapter(val context: TourActivity, val datas:List<TourList>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = TourViewHolder(ItemRetrofitBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as TourViewHolder).binding

        //add......................................
        val model = datas!![position]
        binding.name.text = model.name
        binding.addr1.text = model.addr1
        binding.tel.text = model.tel
//        val urlImg = model.firstImageUrl
////        binding.itemTime.text = "${model.author} At ${model.publishedAt}"
//        Glide.with(context)
//            .asBitmap()
//            .load(urlImg)
//            .into(object : CustomTarget<Bitmap>(200, 200) {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap>?
//                ) {
//                    binding.avatarView.setImageBitmap(resource)
////                    Log.d("lsy", "width : ${resource.width}, height: ${resource.height}")
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    TODO("Not yet implemented")
//                }
//            })

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.binding.root?.context, TourDetailActivity::class.java)
            intent.putExtra("lat", model.lat)
            intent.putExtra("lnt", model.lnt)
            ContextCompat.startActivity(holder.binding.root.context, intent, null)
        }

    }

}