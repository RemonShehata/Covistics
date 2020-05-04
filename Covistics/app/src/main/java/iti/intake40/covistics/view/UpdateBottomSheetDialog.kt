package iti.intake40.covistics.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import iti.intake40.covistics.R
import iti.intake40.covistics.core.CovidSharedPreferences
import kotlinx.android.synthetic.main.update_bottom_sheet_layout.*
import kotlinx.android.synthetic.main.update_bottom_sheet_layout.view.*
import kotlinx.android.synthetic.main.update_bottom_sheet_layout.view.rg_one

class UpdateBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var listener: BottomSheetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.update_bottom_sheet_layout, container, false)

        when( CovidSharedPreferences.periodicTime){
            60L -> {v.rg_one.setChecked(true)}
            120L -> {v.rg_two.setChecked(true)}
            300L -> {v.rg_five.setChecked(true)}
            1440L -> {v.rg_day.setChecked(true)}
            else -> {v.rg_two.setChecked(true)}
        }

        v.rg_bottom_sheet.setOnCheckedChangeListener { rg_bottom_sheet, checkid ->
            when (checkid) {
                R.id.rg_one -> {
                    listener.onButtonClicked(60)
                }
                R.id.rg_two -> {
                    listener.onButtonClicked(2 * 60)
                }
                R.id.rg_five -> {
                    listener.onButtonClicked(5 * 60)
                }
                R.id.rg_day -> {
                    listener.onButtonClicked(24 * 60)
                }
                else -> {
                    listener.onButtonClicked(-1)
                }
            }
            dismiss()
        }
        return v
    }

    interface BottomSheetListener {
        fun onButtonClicked(interval: Long)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as BottomSheetListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " Must Implement BottomSheetListener")
        }

    }
}