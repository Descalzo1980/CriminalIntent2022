package ru.stas.criminalintent2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.stas.criminalintent2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = CrimeDetailFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,fragment)
            .commitNow()
    }
}