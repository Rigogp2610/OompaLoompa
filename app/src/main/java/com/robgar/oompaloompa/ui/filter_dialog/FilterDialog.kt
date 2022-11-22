package com.robgar.oompaloompa.ui.filter_dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.databinding.DialogFilterBinding


enum class FilterType {
    GENDER,
    PROFESSION
}

private typealias Listener = (List<Pair<FilterType, String>>) -> Unit


class FilterDialog(context: Context, val listener: Listener) : Dialog(context) {

    private lateinit var binding: DialogFilterBinding

    private var genderPosition: Int = 0
    private var professionPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setLayout(context.resources.getDimension(R.dimen.dialog_width).toInt(), ConstraintLayout.LayoutParams.WRAP_CONTENT)

        fillDropdownGender()
        fillDropdownProfession()

        setInitValueDropdownGender()
        setInitValueDropdownProfession()

        binding.exposedDropdownGender.setOnItemClickListener { _, _, position, _ ->
            genderPosition = position
        }

        binding.exposedDropdownProfession.setOnItemClickListener { _, _, position, _ ->
            professionPosition = position
        }

        binding.btnFilter.setOnClickListener(filterListener)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun fillDropdownGender() {
        val genderTypes = context.resources.getStringArray(R.array.genderTypes)

        val genderAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            genderTypes
        )

        val dropdownGender = binding.exposedDropdownGender
        dropdownGender.setAdapter(genderAdapter)
    }

    private fun fillDropdownProfession() {
        val professionTypes = context.resources.getStringArray(R.array.professionTypes)

        val professionAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            professionTypes
        )

        val dropdownProfession = binding.exposedDropdownProfession
        dropdownProfession.setAdapter(professionAdapter)
    }

    private fun setInitValueDropdownGender() {
        binding.exposedDropdownGender.setSelection(genderPosition)
        binding.exposedDropdownGender.setText(binding.exposedDropdownGender.adapter.getItem(genderPosition) as String, false)
    }

    private fun setInitValueDropdownProfession() {
        binding.exposedDropdownProfession.setSelection(professionPosition)
        binding.exposedDropdownProfession.setText(binding.exposedDropdownProfession.adapter.getItem(professionPosition) as String, false)
    }

    private var filterListener = View.OnClickListener {
        val genderSelected = binding.exposedDropdownGender.adapter.getItem(genderPosition) as String
        val professionSelected = binding.exposedDropdownProfession.adapter.getItem(professionPosition) as String

        val gender = Pair(FilterType.GENDER, genderSelected)
        val profession = Pair(FilterType.PROFESSION, professionSelected)

        val filterList = mutableListOf<Pair<FilterType, String>>()

        if (genderPosition != 0) filterList.add(gender)
        if (professionPosition != 0) filterList.add(profession)

        listener(filterList)

        dismiss()
    }
}