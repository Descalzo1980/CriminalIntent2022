package ru.stas.criminalintent2022


import androidx.lifecycle.ViewModel

class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    val crimes = mutableListOf<Crime>()

    suspend fun loadCrimes(): List<Crime> {
        return crimeRepository.getCrimes()
    }
}
