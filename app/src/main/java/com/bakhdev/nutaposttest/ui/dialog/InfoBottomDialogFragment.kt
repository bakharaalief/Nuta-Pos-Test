package com.bakhdev.nutaposttest.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bakhdev.nutaposttest.databinding.FragmentInfoBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoBottomDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentInfoBottomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBottomDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val FRAG_TAG = "INFO_BOTTOM_DIALOG_FRAG"
    }
}