package com.bakhdev.nutaposttest.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakhdev.nutaposttest.databinding.FragmentListTypeDialogBinding
import com.bakhdev.nutaposttest.domain.model.Type
import com.bakhdev.nutaposttest.ui.adapter.ListTypeAdapter

class ListTypeDialogFragment(
    private val handleDataWhenCloseFrag: (type: Type) -> Unit,
    private val listType: ArrayList<Type>
) :
    DialogFragment() {
    private var _binding: FragmentListTypeDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListTypeDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup rv list type
        setUpListType()
    }

    private fun setUpListType() {
        //set data to adapter
        val listTypeAdapter = ListTypeAdapter(::onItemClick)
        listTypeAdapter.submitList(listType)

        binding.rvType.apply {
            adapter = listTypeAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun onItemClick(type: Type) {
        handleDataWhenCloseFrag(type)
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val FRAG_TAG = "LIST_TYPE_DIALOG_FRAG"
    }
}