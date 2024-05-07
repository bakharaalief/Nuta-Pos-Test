package com.bakhdev.nutaposttest.ui.moneyInInput

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bakhdev.nutaposttest.R
import com.bakhdev.nutaposttest.databinding.FragmentInputMoneyInBinding
import com.bakhdev.nutaposttest.domain.model.Money
import com.bakhdev.nutaposttest.domain.model.Type
import com.bakhdev.nutaposttest.ui.dialog.ImageDialogFragment
import com.bakhdev.nutaposttest.ui.dialog.InfoBottomDialogFragment
import com.bakhdev.nutaposttest.ui.dialog.ListTypeDialogFragment
import com.bakhdev.nutaposttest.ui.viewModel.InputMoneyInViewModel
import com.bakhdev.nutaposttest.ui.viewModel.ViewModelFactory
import com.bakhdev.nutaposttest.util.toDecimal
import com.bakhdev.nutaposttest.util.toRupiahForm
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.util.Date

class InputMoneyInFragment : Fragment() {
    //binding variable
    private var _binding: FragmentInputMoneyInBinding? = null
    private val binding get() = _binding!!

    //listType
    private val listType = ArrayList<Type>()
    private var selectedType = Type()

    //image variable
    private var uriImage: Uri? = null
    private var uriFilePath: String = ""
    private val cropImage =
        registerForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                uriImage = result.uriContent
                uriFilePath =
                    if (result.getUriFilePath(requireContext()) == null) "" else result.getUriFilePath(
                        requireContext(), true
                    ).toString()
                binding.imgEvidence.setImageURI(uriImage)
                binding.imgText.visibility = View.GONE
                binding.imgChange.visibility = View.VISIBLE
                binding.imgDelete.visibility = View.VISIBLE
            } else {
                // An error occurred.
            }
        }

    //viewmodel
    private val viewModel by viewModels<InputMoneyInViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputMoneyInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load list data
        listType.apply {
            add(Type(1, getString(R.string.other_income)))
            add(Type(2, getString(R.string.non_income)))
        }

        //setup appbar
        setupAppbar()

        //handle form
        setUpForm()

        //handle tfType
        binding.tfType.apply {
            editText?.setOnClickListener {
                showListTypeDialog()
            }
            setEndIconOnClickListener {
                showInfoBottomDialog()
            }
        }

        //handle image photo
        binding.mcEvidence.setOnClickListener {
            showImageDialog()
        }

        //handle delete photo
        binding.imgDelete.setOnClickListener {
            binding.imgDelete.visibility = View.INVISIBLE
            binding.imgChange.visibility = View.INVISIBLE
            binding.imgText.text = getText(R.string.photo)
            binding.imgText.visibility = View.VISIBLE
            uriImage = null
            uriFilePath = ""
            binding.imgEvidence.setImageURI(uriImage)
        }

        //handle change photo
        binding.imgChange.setOnClickListener {
            showImageDialog()
        }

        //handle button save
        binding.btnSave?.setOnClickListener {
            saveData()
        }
    }

    private fun setupAppbar() = binding.topAppBar?.apply {
        setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    saveData()
                    true
                }

                else -> false
            }
        }
    }

    private fun setUpForm() = binding.apply {
        tfTo.editText?.addTextChangedListener(textWatcher(binding.tfTo))
        tfFrom.editText?.addTextChangedListener(textWatcher(binding.tfFrom))
        tfAmount.editText?.addTextChangedListener(textWatcherMoney(binding.tfAmount))
        tfNotes.editText?.addTextChangedListener(textWatcher(binding.tfNotes))
        tfType.editText?.addTextChangedListener(textWatcher(binding.tfType))
    }

    private fun textWatcher(textInputLayout: TextInputLayout): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val length = p0?.length ?: 0
            if (length > 0) {
                textInputLayout.isErrorEnabled = false
            } else {
                textInputLayout.error = getString(R.string.please_fill_this)
                textInputLayout.isErrorEnabled = true
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun textWatcherMoney(textInputLayout: TextInputLayout): TextWatcher =
        object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val length = p0?.length ?: 0
                if (length > 0) {
                    textInputLayout.isErrorEnabled = false
                    textInputLayout.editText?.removeTextChangedListener(this)
                    textInputLayout.editText?.setText(p0.toString().toRupiahForm())
                    textInputLayout.editText?.setSelection(length)
                    textInputLayout.editText?.addTextChangedListener(this)
                } else {
                    textInputLayout.error = getString(R.string.please_fill_this)
                    textInputLayout.isErrorEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

    private fun showListTypeDialog() {
        val listTypeDialogFragment = ListTypeDialogFragment(::handleDataWhenCloseFrag, listType)
        listTypeDialogFragment.show(parentFragmentManager, ListTypeDialogFragment.FRAG_TAG)
    }

    private fun handleDataWhenCloseFrag(type: Type) {
        selectedType = listType.find { it.id == type.id } ?: Type()
        binding.tfType.editText?.setText(selectedType.type)
    }

    private fun showInfoBottomDialog() {
        val infoBottomDialogFragment = InfoBottomDialogFragment()
        infoBottomDialogFragment.show(parentFragmentManager, InfoBottomDialogFragment.FRAG_TAG)
    }

    private fun showImageDialog() {
        val imageDialogFragment = ImageDialogFragment(::handlePressImage)
        imageDialogFragment.show(parentFragmentManager, ImageDialogFragment.FRAG_TAG)
    }

    private fun handlePressImage(input: Int) {
        if (input == ImageDialogFragment.CLICK_CAMERA) {
            if (checkCameraPermission()) {
                requestCameraPermission()
            } else {
                pickFromCamera()
            }
        } else {
            pickFromGallery()
        }
    }

    //handle check and request camera permission
    private fun checkCameraPermission(): Boolean = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) != PackageManager.PERMISSION_GRANTED

    private fun requestCameraPermission() = requestPermissions(
        arrayOf(Manifest.permission.CAMERA),
        PERMISSION_CAMERA_REQUEST_CODE
    )

    private fun pickFromCamera() {
        val options = CropImageOptions().apply {
            imageSourceIncludeCamera = true
            imageSourceIncludeGallery = false
            fixAspectRatio = true
        }
        cropImage.launch(
            CropImageContractOptions(null, options),
        )
    }

    private fun pickFromGallery() {
        val options = CropImageOptions().apply {
            imageSourceIncludeCamera = false
            imageSourceIncludeGallery = true
            fixAspectRatio = true
        }
        cropImage.launch(
            CropImageContractOptions(null, options),
        )
    }

    private fun saveData() {
        if (binding.tfTo.editText?.text?.isEmpty() == true) {
            binding.tfTo.error = getString(R.string.please_fill_this)
        }

        if (binding.tfFrom.editText?.text?.isEmpty() == true) {
            binding.tfFrom.error = getString(R.string.please_fill_this)
        }

        if (binding.tfAmount.editText?.text?.isEmpty() == true) {
            binding.tfAmount.error = getString(R.string.please_fill_this)
        }

        if (binding.tfNotes.editText?.text?.isEmpty() == true) {
            binding.tfNotes.error = getString(R.string.please_fill_this)
        }

        if (binding.tfType.editText?.text?.isEmpty() == true) {
            binding.tfType.error = getString(R.string.please_fill_this)
        } else {
            val money = Money(
                0,
                Date(),
                binding.tfTo.editText?.text.toString(),
                binding.tfFrom.editText?.text.toString(),
                BigDecimal(binding.tfAmount.editText?.text.toString().toDecimal()),
                binding.tfNotes.editText?.text.toString(),
                selectedType.id,
                uriFilePath
            )
            viewModel.insertMoney(money)
            activity?.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //handle request camera result
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                if (permissions[0] == Manifest.permission.CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromCamera()
                } else {
                    Toast.makeText(
                        context,
                        "Harap Update Permission di Settings",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        const val FRAG_TAG = "INPUT_MONEY_IN_FRAG"

        private const val PERMISSION_CAMERA_REQUEST_CODE = 100
    }
}