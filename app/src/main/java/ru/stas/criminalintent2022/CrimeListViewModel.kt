package ru.stas.criminalintent2022

import androidx.lifecycle.ViewModel
import java.util.*

class CrimeListViewModel: ViewModel() {

    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100){
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Ну да, опять кружка",
                date = Date(),
                isSolved = i % 2 == 0,
                pic = R.drawable.dirty_cup
            )
            crimes += crime
        }
    }

}