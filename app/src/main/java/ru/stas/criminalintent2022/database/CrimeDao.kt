package ru.stas.criminalintent2022.database

import androidx.room.Dao
import androidx.room.Query
import ru.stas.criminalintent2022.Crime
import java.util.UUID

@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
    suspend fun getCrimes(crime: Crime)

    @Query("SELECT * FROM crime WHERE id=(:id)")
    suspend fun getCrime(id: UUID)
}