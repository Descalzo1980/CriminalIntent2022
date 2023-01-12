package ru.stas.criminalintent2022

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.stas.criminalintent2022.databinding.ListItemCrimeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CrimeListAdapter(
    private val crimes : List<Crime>,
    private val onCrimeClicked: (crimeId: UUID) -> Unit,
//    private val onDeleteClicked: (crimeId: UUID) -> Unit
    ) : RecyclerView.Adapter<CrimeListAdapter.CrimeHolder>(){


    inner class CrimeHolder(private val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(crime: Crime, onCrimeClicked:(crimeId: UUID)->Unit) {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
            binding.root.setOnClickListener {
                onCrimeClicked(crime.id)
            }
//            binding.root.setOnClickListener {
//                onDeleteClicked(crime.id)
//            }
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

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        holder.bind(crime,onCrimeClicked)
    }

    override fun getItemCount() = crimes.size

}

