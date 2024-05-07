package com.bakhdev.nutaposttest.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bakhdev.nutaposttest.databinding.FragmentImageDialogBinding

class ImageDialogFragment(private val onPressHandler: (Int) -> Unit) : DialogFragment() {
    private var _binding: FragmentImageDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgCamera.setOnClickListener {
            onPressHandler(CLICK_CAMERA)
            dismiss()
        }

        binding.imgGallery.setOnClickListener {
            onPressHandler(CLICK_GALLERY)
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val CLICK_CAMERA = 0
        const val CLICK_GALLERY = 1
        const val FRAG_TAG = "IMAGE_DIALOG_FRAG"
    }
}