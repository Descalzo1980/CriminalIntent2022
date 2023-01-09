package ru.stas.criminalintent2022

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    val crimes = crimeRepository.getCrimes()

    init {
        viewModelScope.launch {

        }
    }
}
