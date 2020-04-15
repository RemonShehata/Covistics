package iti.intake40.covistics.view.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import iti.intake40.covistics.R
import kotlinx.android.synthetic.main.fragment_prevention.*

/**
 * A simple [Fragment] subclass.
 */
class PreventionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prevention, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preventionOneString = resources.getString(R.string.stay)
        prevention_one.text = Html.fromHtml(preventionOneString)

        val preventionTwoString = resources.getString(R.string.keep)
        prevention_two.text = Html.fromHtml(preventionTwoString)

        val preventionThreeString = resources.getString(R.string.wash)
        prevention_three.text = Html.fromHtml(preventionThreeString)

        val preventionFourString = resources.getString(R.string.cover)
        prevention_four.text = Html.fromHtml(preventionFourString)

        val preventionFiveString = resources.getString(R.string.sick)
        prevention_five.text = Html.fromHtml(preventionFiveString)
    }

}
