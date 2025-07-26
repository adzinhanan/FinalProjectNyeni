package model

import abstract_classes.BaseTugas

data class Tugas(
    override val nama: String,
    override val hari: String,
    override val tanggal: Int,
    override val bulan: String,
    override val tahun: Int,
    override val jam: String
) : BaseTugas(nama, hari, tanggal, bulan, tahun, jam) {

    override fun tampilkanDetail() {
        println("Nama Tugas: $nama")
        println("Tanggal: $hari, $tanggal $bulan $tahun Pukul $jam")
        println("Status: ${if (selesai) "Selesai" else "Belum Selesai"}")
        println("Prioritas: ${getPrioritasText()}")
    }

    override fun getPrioritasText(): String {
        return "Tidak Ada Prioritas"
    }
}

data class TugasPrioritas(
    override val nama: String,
    override val hari: String,
    override val tanggal: Int,
    override val bulan: String,
    override val tahun: Int,
    override val jam: String,
    val prioritas: Prioritas
) : BaseTugas(nama, hari, tanggal, bulan, tahun, jam) {

    override fun tampilkanDetail() {
        println("Nama Tugas: $nama")
        println("Tanggal: $hari, $tanggal $bulan $tahun Pukul $jam")
        println("Status: ${if (selesai) "Selesai" else "Belum Selesai"}")
        println("Prioritas: ${getPrioritasText()}")
    }

    override fun getPrioritasText(): String {
        return prioritas.name.replace("_", " ").lowercase().capitalize() // Mengubah ENUM menjadi format lebih rapi
    }
}