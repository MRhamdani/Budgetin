package com.kuliah.budgetin

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var txtHalo: TextView
    private lateinit var txtSaldo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi View
        txtHalo = findViewById(R.id.txtHalo)
        txtSaldo = findViewById(R.id.txtSaldoDashboard)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)
        val btnWishlist = findViewById<ImageButton>(R.id.btnWishlist)
        val fabMain = findViewById<FloatingActionButton>(R.id.fabMain)

        // Navigasi ke Wishlist
        btnWishlist.setOnClickListener {
            val intent = Intent(this, WishlistActivity::class.java)
            startActivity(intent)
        }

        // Navigasi ke Tambah Transaksi (FAB)
        fabMain.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }

        // Logika Logout (Profile)
        btnProfile.setOnClickListener {
            val builder = androidx.appcompat.app.AlertDialog.Builder(this)
            builder.setTitle("Logout & Reset")
            builder.setMessage("Apakah Anda ingin menghapus semua data dan keluar?")
            builder.setPositiveButton("Ya, Hapus") { _, _ ->
                val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }
            builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }
    }

    // Fungsi Format Rupiah agar tampilan uang rapi
    private fun formatRupiah(nominal: Long): String {
        val localeID = Locale("id", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(nominal).replace(",00", "")
    }

    override fun onResume() {
        super.onResume()
        // Mengambil data terbaru dari SharedPreferences
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("NAMA_USER", "User")
        val saldoStr = sharedPref.getString("SALDO_USER", "0")
        val riwayatRaw = sharedPref.getString("RIWAYAT_TRANSAKSI", "")

        txtHalo.text = "Halo, $nama!"
        val saldoAngka = saldoStr?.toLong() ?: 0
        txtSaldo.text = formatRupiah(saldoAngka)

        // Render Riwayat Transaksi
        val container = findViewById<LinearLayout>(R.id.containerRiwayat)
        container.removeAllViews()

        if (!riwayatRaw.isNullOrEmpty()) {
            val listTransaksi = riwayatRaw.split("\n")

            for (item in listTransaksi) {
                if (item.contains("|")) {
                    val bagian = item.split("|")
                    val namaTrx = bagian[0]
                    val nominalTrx = bagian[1]

                    // Bersihkan simbol untuk memproses angka
                    val nominalBersih = nominalTrx.replace(Regex("[^0-9]"), "").toLong()
                    val nominalCantik = formatRupiah(nominalBersih)
                    val simbol = if (nominalTrx.contains("+")) "+" else "-"

                    // Membuat Baris Transaksi secara Programmatic (Dynamic UI)
                    val row = RelativeLayout(this)
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(0, 0, 0, 32)
                    row.layoutParams = layoutParams

                    val tvNama = TextView(this)
                    tvNama.text = namaTrx
                    tvNama.textSize = 16f
                    tvNama.setTextColor(Color.BLACK)

                    val tvNominal = TextView(this)
                    tvNominal.text = "$simbol $nominalCantik"
                    tvNominal.textSize = 16f

                    val lpKanan = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                    lpKanan.addRule(RelativeLayout.ALIGN_PARENT_END)
                    tvNominal.layoutParams = lpKanan

                    // Warna hijau untuk masuk, merah untuk keluar
                    if (simbol == "+") {
                        tvNominal.setTextColor(Color.parseColor("#27AE60"))
                    } else {
                        tvNominal.setTextColor(Color.parseColor("#EB5757"))
                    }

                    row.addView(tvNama)
                    row.addView(tvNominal)
                    container.addView(row)
                }
            }
        }
    }
}