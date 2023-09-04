package com.chow.customview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chow.customview.databinding.ItemColorBinding

class ColorAdapter(
    private val colors: List<Int>,
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    class ColorViewHolder(val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ColorViewHolder(
        ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.binding.ivColor.apply {
            setBackgroundColor(colors[position])
            setOnClickListener {
                onColorSelected.invoke(colors[position])
            }
        }
    }

    override fun getItemCount() = colors.size
}