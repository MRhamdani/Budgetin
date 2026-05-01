package com.kuliah.budgetin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class WishlistActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout
    private var saldoSekarang: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val txtSaldo = findViewById<TextView>(R.id.txtSaldoWishlist)
        val edtNama = findViewById<EditText>(R.id.edtNamaBarang)
        val edtHarga = findViewById<EditText>(R.id.edtHargaBarang)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanWishlist)
        container = findViewById(R.id.containerWishlist)

        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        saldoSekarang = sharedPref.getString("SALDO_USER", "0")?.toLong() ?: 0
        txtSaldo.text = formatRupiah(saldoSekarang)

        // Load data wishlist yang tersimpan sebelumnya
        loadWishlistData()

        btnSimpan.setOnClickListener {
            val nama = edtNama.text.toString().trim()
            val hargaStr = edtHarga.text.toString().trim()

            if (nama.isNotEmpty() && hargaStr.isNotEmpty()) {
                val harga = hargaStr.toLong()

                // Simpan ke SharedPreferences
                saveWishlistToPref(nama, harga)

                // Tambahkan ke UI
                tambahItemKeUI(nama, harga)

                // Reset Input
                edtNama.text.clear()
                edtHarga.text.clear()
                Toast.makeText(this, "Wishlist ditambahkan!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun tambahItemKeUI(nama: String, harga: Long) {
        val selisih = harga - saldoSekarang

        val itemView = layoutInflater.inflate(android.R.layout.simple_list_item_2, null)
        val text1 = itemView.findViewById<TextView>(android.R.id.text1)
        val text2 = itemView.findViewById<TextView>(android.R.id.text2)

        text1.text = "$nama - ${formatRupiah(harga)}"
        text1.textSize = 18f
        text1.setTypeface(null, android.graphics.Typeface.BOLD) // Benar

        if (selisih <= 0) {
            text2.text = "Status: Saldo Cukup! (Lebih: ${formatRupiah(Math.abs(selisih))})"
            text2.setTextColor(Color.parseColor("#10B981"))
        } else {
            text2.text = "Status: Belum Cukup (Kurang: ${formatRupiah(selisih)})"
            text2.setTextColor(Color.parseColor("#EF4444"))
        }

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 40)
        itemView.layoutParams = params

        container.addView(itemView, 0) // Tambah ke posisi paling atas
    }

    private fun saveWishlistToPref(nama: String, harga: Long) {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val wishlistLama = sharedPref.getString("DATA_WISHLIST", "")
        val dataBaru = "$nama|$harga\n$wishlistLama"

        sharedPref.edit().putString("DATA_WISHLIST", dataBaru).apply()
    }

    private fun loadWishlistData() {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val wishlistRaw = sharedPref.getString("DATA_WISHLIST", "") ?: ""

        if (wishlistRaw.isNotEmpty()) {
            val list = wishlistRaw.split("\n")
            for (item in list) {
                if (item.contains("|")) {
                    val bagian = item.split("|")
                    tambahItemKeUI(bagian[0], bagian[1].toLong())
                }
            }
        }
    }

    private fun formatRupiah(nominal: Long): String {
        val localeID = Locale("id", "ID")
        return NumberFormat.getCurrencyInstance(localeID).format(nominal).replace(",00", "")
    }
}