package com.fg.practiceecom.presentation.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.fg.practiceecom.R
import com.fg.practiceecom.data.Address
import com.fg.practiceecom.data.CartProduct
import com.fg.practiceecom.databinding.AddressRvItemBinding
import com.fg.practiceecom.databinding.BillingProductsRvItemBinding
import com.fg.practiceecom.helper.getProductPrice

class BillingProductAdapter :
    RecyclerView.Adapter<BillingProductAdapter.BillingProductsViewHolder>() {

    inner class BillingProductsViewHolder(val binding: BillingProductsRvItemBinding) :
        ViewHolder(binding.root) {

        fun bind(billingProduct: CartProduct) {
            binding.apply {
                Glide.with(itemView).load(billingProduct.product.images[0]).into(imageCartProduct)
                tvProductCartName.text = billingProduct.product.name
                tvBillingProductQuantity.text = billingProduct.quantity.toString()

                val priceAfterPercentage = billingProduct.product.offerPercentage.getProductPrice(billingProduct.product.price)
                tvProductCartPrice.text = "₺ ${String.format("%.2f", priceAfterPercentage)}"

                tvProductCartSize.text = billingProduct.selectedSize ?: "".also { imageCartProductSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT)) }
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<CartProduct>() {

        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product == newItem.product
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingProductsViewHolder {
        return BillingProductsViewHolder(
            BillingProductsRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(
        holder: BillingProductAdapter.BillingProductsViewHolder,
        position: Int
    ) {
        val billingProduct = differ.currentList[position]
        holder.bind(billingProduct)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}