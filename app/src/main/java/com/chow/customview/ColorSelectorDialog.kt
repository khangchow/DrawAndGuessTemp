package com.chow.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.chow.customview.databinding.DialogColorSelectorBinding

class ColorSelectorDialog(
    private val onColorSelected: (Int) -> Unit
) : DialogFragment() {
    private var binding: DialogColorSelectorBinding? = null

    override fun onStart() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogColorSelectorBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            ivClose.setOnClickListener {
                dismiss()
            }
            rvColors.apply {
                adapter = ColorAdapter(getColors()) {
                    onColorSelected.invoke(it)
                }
                layoutManager = GridLayoutManager(context, 4)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getColors() = mutableListOf(
        ContextCompat.getColor(requireContext(), R.color.black),
        ContextCompat.getColor(requireContext(), R.color.green),
        ContextCompat.getColor(requireContext(), R.color.grey),
        ContextCompat.getColor(requireContext(), R.color.red),
        ContextCompat.getColor(requireContext(), R.color.yellow),
        ContextCompat.getColor(requireContext(), R.color.orange),
        ContextCompat.getColor(requireContext(), R.color.blue),
        ContextCompat.getColor(requireContext(), R.color.purple),
        ContextCompat.getColor(requireContext(), R.color.pink),
        ContextCompat.getColor(requireContext(), R.color.brown),
    )
}