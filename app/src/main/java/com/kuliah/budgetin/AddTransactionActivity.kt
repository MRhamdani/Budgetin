package com.kuliah.budgetin

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class AddTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        // Inisialisasi komponen UI dari XML
        val etNama = findViewById<TextInputEditText>(R.id.etNamaTransaksi)
        val etNominal = findViewById<TextInputEditText>(R.id.etNominal)
        val rgJenis = findViewById<RadioGroup>(R.id.rgJenis)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanTransaksi)

        // Perintah saat tombol simpan diklik
        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString()
            val nominalStr = etNominal.text.toString()
            val selectedId = rgJenis.checkedRadioButtonId

            // Validasi: Pastikan semua kolom sudah diisi
            if (nama.isNotEmpty() && nominalStr.isNotEmpty() && selectedId != -1) {

                val nominalInput = nominalStr.toLong()
                val rbTerpilih = findViewById<RadioButton>(selectedId)
                val jenis = rbTerpilih.text.toString()

                // Akses SharedPreferences (Buku catatan aplikasi)
                val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                // --- LOGIKA MATEMATIKA SALDO ---
                val saldoLamaStr = sharedPref.getString("SALDO_USER", "0")
                var saldoSekarang = saldoLamaStr?.toLong() ?: 0

                if (jenis == "Pemasukan") {
                    saldoSekarang += nominalInput
                } else {
                    saldoSekarang -= nominalInput
                }

                // --- LOGIKA PENYIMPANAN RIWAYAT ---
                // Ambil daftar riwayat yang sudah ada sebelumnya
                val riwayatLama = sharedPref.getString("RIWAYAT_TRANSAKSI", "")

                // Buat format teks untuk riwayat baru (Paling atas adalah yang terbaru)
                val simbol = if (jenis == "Pemasukan") "+" else "-"
                val riwayatBaru = "$nama|$simbol$nominalInput\n$riwayatLama"

                // --- SIMPAN SEMUA DATA BARU ---
                editor.putString("SALDO_USER", saldoSekarang.toString())
                editor.putString("RIWAYAT_TRANSAKSI", riwayatBaru)
                editor.apply()

                // Notifikasi sukses
                Toast.makeText(this, "Transaksi $nama berhasil dicatat!", Toast.LENGTH_SHORT).show()

                // Menutup halaman ini dan kembali ke Dashboard (MainActivity)
                finish()

            } else {
                // Notifikasi jika ada data yang kosong
                Toast.makeText(this, "Mohon lengkapi semua data transaksi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}