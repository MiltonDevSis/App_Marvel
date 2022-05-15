package com.mpfcoding.app_marvel.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mpfcoding.app_marvel.databinding.ItemParentDetailBinding
import com.mpfcoding.app_marvel.framework.imageloader.ImageLoader


class DetailParentAdapter(
    private val detailParentList: List<DetailParentVE>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<DetailParentAdapter.DetailParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailParentViewHolder {
        return DetailParentViewHolder.create(parent, imageLoader)
    }

    override fun onBindViewHolder(holder: DetailParentViewHolder, position: Int) {
        holder.bind(detailParentList[position])
    }

    override fun getItemCount() = detailParentList.size

    class DetailParentViewHolder(
        itemBinding: ItemParentDetailBinding,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val textItemCategory: TextView = itemBinding.textItemCategory
        private val recyclerChielDetail: RecyclerView = itemBinding.recyclerChildDetail

        fun bind(detailParentVE: DetailParentVE) {
            textItemCategory.text = itemView.context.getString(detailParentVE.categoryStringResId)
            recyclerChielDetail.run {
                setHasFixedSize(true)
                adapter = DetailChildAdapter(detailParentVE.detailChildList, imageLoader)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                imageLoader: ImageLoader
            ): DetailParentViewHolder {
                val itemBinding = ItemParentDetailBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return DetailParentViewHolder(itemBinding, imageLoader)
            }
        }
    }
}