package com.bakhdev.nutaposttest.ui.dialog

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bakhdev.nutaposttest.databinding.FragmentShowImageBinding

class ShowImageFragment(private val path: String) : DialogFragment() {
    private var _binding: FragmentShowImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitmap = BitmapFactory.decodeFile(path)
        binding.imgEvidence.setImageBitmap(bitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val FRAG_TAG = "SHOW_IMAGE_FRAG"
    }
}