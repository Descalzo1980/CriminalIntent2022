package ru.stas.criminalintent2022

import android.app.Application

class CriminalIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}