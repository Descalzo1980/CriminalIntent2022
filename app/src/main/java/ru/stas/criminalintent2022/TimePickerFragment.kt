package ru.stas.criminalintent2022

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.icu.util.TimeZone
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs

class TimePickerFragment: DialogFragment() {

    private val args: TimePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dateListener = TimePickerDialog.OnTimeSetListener { _, _, _ ->
            val resultDate = GregorianCalendar(TimeZone.getTimeZone("America/Los_Angeles")).time
                setFragmentResult(REQUEST_KEY_DATE,
                    bundleOf(BUNDLE_KEY_DATE to resultDate))
        }
        val calendar = Calendar.getInstance()
        calendar.time = args.crimeHours
        val initialHours = calendar.get(Calendar.HOUR_OF_DAY)
        val initialMinutes = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireContext(),
            dateListener,
            initialHours,
            initialMinutes,
            true
        )
    }
    companion object {
        const val REQUEST_KEY_DATE = "REQUEST_KEY_DATE"
        const val BUNDLE_KEY_DATE = "BUNDLE_KEY_DATE"

    }
}