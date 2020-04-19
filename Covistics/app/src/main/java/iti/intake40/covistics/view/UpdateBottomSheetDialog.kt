package iti.intake40.covistics.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import iti.intake40.covistics.R
import kotlinx.android.synthetic.main.update_bottom_sheet_layout.view.*

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

        v.rg_bottom_sheet.setOnCheckedChangeListener { rg_bottom_sheet, checkid ->
            when (checkid) {
                R.id.rg_one -> {
                    listener.onButtonClicked(60)
                    dismiss()
                }
                R.id.rg_two -> {
                    listener.onButtonClicked(2 * 60)
                    dismiss()
                }
                R.id.rg_five -> {
                    listener.onButtonClicked(5 * 60)
                    dismiss()
                }
                R.id.rg_day -> {
                    listener.onButtonClicked(24 * 60)
                    dismiss()
                }
                R.id.rg_manual -> {
                    listener.onButtonClicked(-1)
                    dismiss()
                }
                else -> {
                    listener.onButtonClicked(-1)
                }
            }

        }
        return v
    }

    interface BottomSheetListener {
        fun onButtonClicked(interval: Int)
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