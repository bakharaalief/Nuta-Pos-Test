package com.bakhdev.nutaposttest.ui.moneyIn

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakhdev.nutaposttest.R
import com.bakhdev.nutaposttest.databinding.FragmentMoneyInBinding
import com.bakhdev.nutaposttest.domain.model.Money
import com.bakhdev.nutaposttest.ui.adapter.ListMoneyInAdapter
import com.bakhdev.nutaposttest.ui.dialog.ShowImageFragment
import com.bakhdev.nutaposttest.ui.moneyInInput.InputMoneyInFragment
import com.bakhdev.nutaposttest.ui.viewModel.MoneyInViewModel
import com.bakhdev.nutaposttest.ui.viewModel.ViewModelFactory
import com.bakhdev.nutaposttest.util.toFormattedDate
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Date


class MoneyInFragment : Fragment() {
    //binding
    private var _binding: FragmentMoneyInBinding? = null
    private val binding get() = _binding!!

    //Date
    private var startDateLong: Long = System.currentTimeMillis()
    private var endDateLong: Long = startDateLong

    //viewmodel
    private val viewModel by viewModels<MoneyInViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoneyInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //tv create money in
        binding.tvCreateMoneyIn.setOnClickListener {
            toInputMoneyInFragment()
        }

        //set up date
        setUpDate()

        //setup data
        setupMoneyInAdapter()

        //calendar
        binding.imgCalendar.setOnClickListener {
            showCalendar()
        }

        //handle screen rotation
        viewModel.getStartDate().observe(viewLifecycleOwner) {
            startDateLong = it[0]
            endDateLong = it[1]
            setUpDate()
            setupMoneyInAdapter()
        }
    }

    //handle onPressHandler
    private fun toInputMoneyInFragment() = parentFragmentManager.commit {
        add<InputMoneyInFragment>(R.id.fragment_container_view, InputMoneyInFragment.FRAG_TAG)
        setReorderingAllowed(true)
        addToBackStack(FRAG_TAG)
    }

    private fun setUpDate() {
        val startDate = Date(startDateLong)
        val endDate = Date(endDateLong)
        binding.tvCalendar.text =
            startDate.toFormattedDate().plus(" - ").plus(endDate.toFormattedDate())
    }

    private fun showCalendar() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(getString(R.string.select_date))
            .build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            viewModel.saveStartDate(selection.first, selection.second)
        }

        dateRangePicker.show(parentFragmentManager, CALENDAR_TAG)
    }

    private fun setupMoneyInAdapter() {
        val orientation = resources.configuration.orientation
        viewModel.listMoneyReport(startDateLong, endDateLong, orientation)
            .observe(viewLifecycleOwner) {
                binding.layoutEmpty.visibility = if (it.isNotEmpty()) {
                    View.GONE
                } else View.VISIBLE

                val adapter =
                    ListMoneyInAdapter(it, ::onEditItem, ::onDeleteItem, ::onPhotoItem)
                binding.rvMoneyIn.adapter = adapter
                binding.rvMoneyIn.layoutManager = LinearLayoutManager(context)
            }
    }

    private fun onEditItem() {
        Toast.makeText(context, "Still On Proses :)", Toast.LENGTH_SHORT).show()
    }

    private fun onDeleteItem(money: Money) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder
            .setMessage(getString(R.string.are_you_sure_to_delete_this))
            .setTitle(getString(R.string.delete_data))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.delete(money)
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun onPhotoItem(path: String) {
        val showImageFragment = ShowImageFragment(path)
        showImageFragment.show(parentFragmentManager, ShowImageFragment.FRAG_TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val FRAG_TAG = "MONEY_IN_FRAG"
        const val CALENDAR_TAG = "CALENDAR_TAG"
    }
}