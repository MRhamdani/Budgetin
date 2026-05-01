# Budgetin - Personal Finance Management 💰

**Budgetin** adalah aplikasi manajemen keuangan pribadi berbasis Android yang dirancang dengan pendekatan minimalis dan logika keuangan yang terukur. Aplikasi ini dibangun menggunakan bahasa pemrograman **Kotlin** dengan menerapkan arsitektur **MVVM** (Model-View-ViewModel) untuk memastikan kode yang terstruktur dan mudah dipelihara.

## 📑 Analisis Proyek (5W + 1H)

- **What (Apa):** Budgetin adalah aplikasi manajemen keuangan pribadi berbasis Android yang dikembangkan menggunakan bahasa pemrograman Kotlin dan arsitektur MVVM. Aplikasi ini berfokus pada pencatatan arus kas dan efisiensi pengeluaran.
- **Who (Siapa):** Aplikasi ini dikembangkan oleh Muhammad Rhamdani sebagai proyek Ujian Tengah Semester mata kuliah Pemrograman Mobile 1. Sasaran penggunanya adalah individu yang membutuhkan alat bantu logis untuk mengontrol keuangan harian agar tidak overspending.
- **Why (Mengapa):** Dibuat untuk mengatasi masalah manajemen keuangan yang tidak teratur. Fokus utamanya adalah memberikan kepastian mengenai sisa saldo melalui logika "Safe-to-Spend", sehingga pengguna tahu persis berapa jumlah uang yang aman untuk dibelanjakan tanpa mengganggu rencana tabungan atau kebutuhan pokok.
- **Where (Di mana):** Aplikasi ini digunakan pada perangkat mobile berbasis Android. Sebagai proyek akademik, pengembangan dan pelaporannya dilakukan di lingkup Universitas Teknologi Bandung (UTB).
- **When (Kapan):** Proses pengembangan dilakukan pada semester tengah tahun akademik 2026. Aplikasi ini dirancang untuk digunakan setiap hari oleh pengguna, tepat setelah melakukan transaksi atau saat menerima pendapatan.
- **How (Bagaimana):** Aplikasi bekerja dengan cara mencatat setiap input pemasukan dan pengeluaran ke dalam database lokal. Sistem kemudian secara otomatis mengalokasikan dana menggunakan rumus 50/30/20 (Kebutuhan, Keinginan, Tabungan) dan menyajikannya dalam antarmuka minimalis untuk menjaga fokus pengguna.

## 🚀 Fitur Unggulan

- **Safe-to-Spend Logic**: Memberikan informasi real-time mengenai sisa dana yang aman untuk dibelanjakan setiap hari.
- **Wishlist Tracker**: Fitur pemantau barang impian yang secara otomatis membandingkan harga barang dengan saldo aktif pengguna, memberikan status kelayakan beli secara riil.

## 🛠️ Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/).
- **Architecture**: MVVM (Model-View-ViewModel).
- **UI Framework**: Android Jetpack (XML Layouts).
- **Design System**: Tailwind-inspired colors .

## 📈 Roadmap Pengembangan

- [ ] **Optimalisasi UI/UX (Slide to Delete)**: Mengimplementasikan fitur `ItemTouchHelper` pada `RecyclerView` untuk penghapusan transaksi melalui aksi geser (_slide_) yang lebih ringkas.
- [ ] **Visualisasi Data Interaktif**: Menambahkan komponen **Pie Chart** untuk memberikan ringkasan distribusi pengeluaran pengguna secara visual.
- [ ] **Alokasi Pendapatan Otomatis**: Mengembangkan sistem kalkulasi otomatis menggunakan Rumus 50/30/20 segera setelah pendapatan dimasukkan oleh pengguna.
- [ ] **Safe-to-Spend Logic Enhancement**: Mempertajam algoritma perhitungan untuk memberikan peringatan _real-time_ jika pengeluaran harian melampaui batas aman sebelum akhir bulan.

---

_Dibuat dengan ❤️ Muhammad Rhamdani - Universitas Teknologi Bandung._.
