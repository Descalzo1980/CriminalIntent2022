package ru.stas.criminalintent2022

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.stas.criminalintent2022.databinding.ListItemCrimeBinding

class CrimeListAdapter(private val crimes : List<Crime>) : RecyclerView.Adapter<CrimeListAdapter.CrimeHolder>(){


    inner class CrimeHolder(private val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(crime: Crime){
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
            binding.crimeDirtyCup.context.getDrawable(R.drawable.dirty_cup)
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

