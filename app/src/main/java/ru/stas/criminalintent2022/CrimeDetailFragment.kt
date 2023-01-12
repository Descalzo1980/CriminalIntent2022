package ru.stas.criminalintent2022

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import ru.stas.CrimeDetailViewModel
import ru.stas.CrimeDetailViewModelFactory
import ru.stas.criminalintent2022.databinding.FragmentCrimeDetailBinding
import java.util.Date

private const val DATE_FORMAT = "EEE, MMM, dd"

class CrimeDetailFragment : Fragment(){

    private var _binding: FragmentCrimeDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
           "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: CrimeDetailFragmentArgs by navArgs()

    private val crimeDetailViewModel: CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeId)
    }

    private val selectSuspect = registerForActivityResult(
        ActivityResultContracts.PickContact()
    ){uri: Uri? -> uri?.let { parseContactSelection(it) }}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeDetailBinding.inflate(inflater,container,false)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.crimeTitle.text.isEmpty()) {
                    Toast.makeText(context,"Заполните заголовок",Toast.LENGTH_SHORT).show()
                } else {
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(title = text.toString())
                }
            }
            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crimeDetailViewModel.updateCrime { oldCrime->
                    oldCrime.copy(isSolved = isChecked)
                }
            }
            setFragmentResultListener(
                DatePickerFragment.REQUEST_KEY_DATE
            ) { _, bundle ->
                val newDate =
                    bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
                crimeDetailViewModel.updateCrime { it.copy(date = newDate) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                crimeDetailViewModel.crime.collect{crime->
                    crime?.let { updateUi(it) }
                }
            }
        }
        binding.crimeSuspect.setOnClickListener {
            selectSuspect.launch(null)

        }
        val selectSuspectIntent = selectSuspect.contract.createIntent(
            requireContext(),
            null
        )
        binding.crimeSuspect.isEnabled = canResolveIntent(selectSuspectIntent)
    }

    private fun updateUi(crime: Crime) {
        binding.apply {
            if (crimeTitle.text.toString() != crime.title){
                crimeTitle.setText(crime.title)
            }
            crimeDate.text = crime.date.toString()
            crimeDate.setOnClickListener {
                findNavController().navigate(
                    CrimeDetailFragmentDirections.selectData(crime.date)
                )
            }
            crimeSolved.isChecked = crime.isSolved

            crimeReport.setOnClickListener {
                val reportIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT,getCrimeReport(crime))
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject)
                    )
                }
                val chooserIntent = Intent.createChooser(
                    reportIntent,
                    getString(R.string.crime_report)
                )
                startActivity(chooserIntent)
            }
            crimeSuspect.text = crime.suspect.ifEmpty {
                getString(R.string.crime_suspect_text)
            }
        }
    }

    private fun getCrimeReport(crime: Crime): String{
        val solvedString = if(crime.isSolved){
            getString(R.string.crime_report_solved)
            }else{
                getString(R.string.crime_report_unsolved)
            }
        val dateString = DateFormat.format(DATE_FORMAT,crime.date)
        val suspectText = if(crime.suspect.isBlank()){
            getString(R.string.crime_report_no_suspect)
        }else{
            getString(R.string.crime_report_suspect,crime.suspect)
        }
        return getString(R.string.crime_report,crime.title,dateString,solvedString,suspectText)
    }
    private fun parseContactSelection(contactUri: Uri){
        val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        val queryCursor = requireActivity().contentResolver
            .query(contactUri,queryFields,null,null,null)
        queryCursor?.use { cursor->
            if(cursor.moveToFirst()){
                val suspect = cursor.getString(0)
                crimeDetailViewModel.updateCrime { oldCrime->
                    oldCrime.copy(suspect = suspect)
                }
            }
        }
    }
    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}