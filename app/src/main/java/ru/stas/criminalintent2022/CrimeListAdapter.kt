package ru.stas.criminalintent2022

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.stas.criminalintent2022.databinding.ListItemCrimeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CrimeListAdapter(private val crimes : List<Crime>) : RecyclerView.Adapter<CrimeListAdapter.CrimeHolder>(){


    inner class CrimeHolder(private val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(crime: Crime) {
            binding.crimeTitle.text = crime.title
            val current = LocalDateTime.now()
            val simpleDateFormat = DateTimeFormatter.ofPattern(""""EEEE,MMMM dd, yyyy"""")
            val formatted = current.format(simpleDateFormat)
            binding.crimeDate.text = formatted.toString()
            binding.root.setOnClickListener {
                Snackbar.make(
                    binding.root,
                    "${crime.title} clicked!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            binding.crimeSolved.visibility =
                when {
                    crime.isSolved -> View.VISIBLE
                    else -> View.GONE
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater,parent,false)
        return CrimeHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        holder.bind(crime)
    }

    override fun getItemCount() = crimes.size

}

