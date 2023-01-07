package ru.stas.criminalintent2022

import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CrimeListViewModel: ViewModel() {

    val crimes = mutableListOf<Crime>()
    init {
        for (i in 0 until 100){
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Ну да, опять кружка",
                date = Date(),
                isSolved = i % 2 == 0
            )
            crimes += crime
        }
    }

}