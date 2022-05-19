package uz.gita.task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.task.R
import uz.gita.task.data.model.response.ProductResponse
import uz.gita.task.databinding.ItemProductBinding

class ProductAdapter : ListAdapter<ProductResponse, ProductAdapter.Holder>(object :
    DiffUtil.ItemCallback<ProductResponse>() {
    override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.price == newItem.price
    }
}) {
    private var onClickItemListener: ((Int) -> Unit)? = null

    fun setOnclickItemListener(listener: ((Int) -> Unit)) {
        onClickItemListener = listener
    }

    inner class Holder(private val itemProductBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemProductBinding.root) {
        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            Glide.with(itemProductBinding.imgvProduct)
                .load(item.photoUrl)
                .placeholder(R.drawable.phone)
                .into(itemProductBinding.imgvProduct)
            itemProductBinding.tvNameProduct.text = item.name
            itemProductBinding.tvPriceProduct.text = (item.price.toInt()).toString()
            itemProductBinding.container.setOnClickListener {
                onClickItemListener?.invoke(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }
}