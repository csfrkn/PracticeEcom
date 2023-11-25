package com.fg.practiceecom.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.fg.practiceecom.data.Product
import com.fg.practiceecom.databinding.StealsDealsRvItemBinding

class StealsDealsAdapter : RecyclerView.Adapter<StealsDealsAdapter.StealsDealsViewHolder>() {

    inner class StealsDealsViewHolder(private val binding: StealsDealsRvItemBinding) :
        ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(imgBestDeal)
                product.offerPercentage?.let {
                    val remaningPricePercentage = 1f - it
                    val priceAfterOffer = remaningPricePercentage * product.price
                    tvNewPrice.text = "₺ ${String.format("%.2f", priceAfterOffer)}"
                }
                tvOldPrice.text = "₺ ${product.price}"
                tvDealProductName.text = product.name
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StealsDealsViewHolder {
        return StealsDealsViewHolder(
            StealsDealsRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: StealsDealsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    var onClick: ((Product) -> Unit)? = null

}