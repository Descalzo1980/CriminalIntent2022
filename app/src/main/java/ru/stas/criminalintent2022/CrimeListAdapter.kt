package ru.stas.criminalintent2022

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.stas.criminalintent2022.databinding.ListItemCrimeBinding

class CrimeListAdapter(private val crimes : List<Crime>) : RecyclerView.Adapter<CrimeListAdapter.CrimeHolder>(){

    inner class CrimeHolder(val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater,parent,false)
        return CrimeHolder(binding)
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        holder.apply {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
        }
    }

    override fun getItemCount() = crimes.size

}

