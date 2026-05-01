package com.kuliah.budgetin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(
            androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val etNama = findViewById<TextInputEditText>(R.id.etNamaUser)
        val etSaldo = findViewById<TextInputEditText>(R.id.etSaldoAwal)
        val btnMulai = findViewById<Button>(R.id.btnMulai)

        btnMulai.setOnClickListener {
            val nama = etNama.text.toString()
            val saldo = etSaldo.text.toString()

            if (nama.isNotEmpty() && saldo.isNotEmpty()) {
                // Simpan data ke buku catatan kecil (SharedPreferences)
                val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("NAMA_USER", nama)
                editor.putString("SALDO_USER", saldo)
                editor.apply()

                editor.clear()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Isi dulu nama dan saldonya ya!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}