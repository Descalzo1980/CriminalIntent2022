package ru.stas.criminalintent2022.database

import androidx.room.TypeConverter
import java.util.*

class CrimeTypeConverters {
    @TypeConverter
    fun fromData(date: Date): Long{
        return date.time
    }
    @TypeConverter
    fun toDate(millsSinceEpoch: Long): Date{
        return Date(millsSinceEpoch)
    }
}