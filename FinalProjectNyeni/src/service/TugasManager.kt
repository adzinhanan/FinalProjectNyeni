package service // Pastikan ini sesuai dengan nama folder 'service'

import interfaces.TugasOperasi
import model.Tugas
import model.TugasPrioritas
import model.Prioritas
import java.util.InputMismatchException
import java.util.Scanner

class TugasManager {
    private val daftarTugas = mutableListOf<TugasOperasi>()
    private val scanner = Scanner(System.`in`)

    fun tambahTugas() {
        println("\n--- Tambah Tugas Baru ---")
        print("Nama Tugas: ")
        val nama = scanner.nextLine()
        print("Hari (contoh: Senin): ")
        val hari = scanner.nextLine()
        print("Tanggal (1-31): ")
        val tanggal = getValidIntInput("Tanggal")
        print("Bulan (contoh: Januari): ")
        val bulan = scanner.nextLine()
        print("Tahun: ")
        val tahun = getValidIntInput("Tahun")
        print("Jam (HH:MM): ")
        val jam = scanner.nextLine()

        print("Apakah ini tugas prioritas? (ya/tidak): ")
        val isPrioritasStr = scanner.nextLine().lowercase()

        if (isPrioritasStr == "ya") {
            var prioritasEnum: Prioritas? = null
            while (prioritasEnum == null) {
                print("Prioritas (Tinggi/Sedang/Rendah): ")
                val prioritasInput = scanner.nextLine().uppercase()
                prioritasEnum = try {
                    Prioritas.valueOf(prioritasInput)
                } catch (e: IllegalArgumentException) {
                    println("Prioritas tidak valid. Mohon masukkan Tinggi, Sedang, atau Rendah.")
                    null
                }
            }
            daftarTugas.add(TugasPrioritas(nama, hari, tanggal, bulan, tahun, jam, prioritasEnum))
        } else {
            daftarTugas.add(Tugas(nama, hari, tanggal, bulan, tahun, jam))
        }
        println("Tugas '$nama' berhasil ditambahkan!")
    }

    fun tandaiTugasSelesai() {
        if (daftarTugas.isEmpty()) {
            println("Belum ada tugas untuk ditandai selesai.")
            return
        }
        tampilkanSemuaTugas()
        print("Masukkan nomor tugas yang ingin ditandai selesai: ")
        val index = getValidIntInput("Nomor tugas") - 1

        if (index >= 0 && index < daftarTugas.size) {
            val tugas = daftarTugas[index]
            tugas.tandaiSelesai()
            val namaTugas = when (tugas) {
                is Tugas -> tugas.nama
                is TugasPrioritas -> tugas.nama
                else -> "Tugas Tidak Dikenal"
            }
            println("Tugas '$namaTugas' berhasil ditandai selesai!")
        } else {
            println("Nomor tugas tidak valid.")
        }
    }

    fun editTugas() {
        if (daftarTugas.isEmpty()) {
            println("Belum ada tugas untuk diedit.")
            return
        }
        tampilkanSemuaTugas()
        print("Masukkan nomor tugas yang ingin diedit: ")
        val index = getValidIntInput("Nomor tugas") - 1

        if (index >= 0 && index < daftarTugas.size) {
            val tugasToEdit = daftarTugas[index]
            val namaSekarang = when (tugasToEdit) {
                is Tugas -> tugasToEdit.nama
                is TugasPrioritas -> tugasToEdit.nama
                else -> "Tugas Tidak Dikenal"
            }
            println("Mengedit tugas: $namaSekarang")

            print("Nama Tugas Baru (kosongkan jika tidak berubah): ")
            val newNama = scanner.nextLine()
            print("Hari Baru (kosongkan jika tidak berubah): ")
            val newHari = scanner.nextLine()
            print("Tanggal Baru (0 jika tidak berubah): ")
            val newTanggal = getValidIntInput("Tanggal")
            print("Bulan Baru (kosongkan jika tidak berubah): ")
            val newBulan = scanner.nextLine()
            print("Tahun Baru (0 jika tidak berubah): ")
            val newTahun = getValidIntInput("Tahun")
            print("Jam Baru (kosongkan jika tidak berubah): ")
            val newJam = scanner.nextLine()

            val updatedTugas: TugasOperasi = when (tugasToEdit) {
                is TugasPrioritas -> {
                    print("Prioritas Baru (Tinggi/Sedang/Rendah, kosongkan jika tidak berubah): ")
                    val newPrioritasStr = scanner.nextLine().uppercase()
                    val newPrioritas = if (newPrioritasStr.isNotBlank()) {
                        try {
                            Prioritas.valueOf(newPrioritasStr)
                        } catch (e: IllegalArgumentException) {
                            println("Prioritas tidak valid. Menggunakan prioritas lama.")
                            tugasToEdit.prioritas
                        }
                    } else {
                        tugasToEdit.prioritas
                    }

                    tugasToEdit.copy(
                        nama = if (newNama.isNotBlank()) newNama else tugasToEdit.nama,
                        hari = if (newHari.isNotBlank()) newHari else tugasToEdit.hari,
                        tanggal = if (newTanggal != 0) newTanggal else tugasToEdit.tanggal,
                        bulan = if (newBulan.isNotBlank()) newBulan else tugasToEdit.bulan,
                        tahun = if (newTahun != 0) newTahun else tugasToEdit.tahun,
                        jam = if (newJam.isNotBlank()) newJam else tugasToEdit.jam,
                        prioritas = newPrioritas
                    ).apply { selesai = tugasToEdit.selesai } // Salin status 'selesai'
                }
                is Tugas -> {
                    tugasToEdit.copy(
                        nama = if (newNama.isNotBlank()) newNama else tugasToEdit.nama,
                        hari = if (newHari.isNotBlank()) newHari else tugasToEdit.hari,
                        tanggal = if (newTanggal != 0) newTanggal else tugasToEdit.tanggal,
                        bulan = if (newBulan.isNotBlank()) newBulan else tugasToEdit.bulan,
                        tahun = if (newTahun != 0) newTahun else tugasToEdit.tahun,
                        jam = if (newJam.isNotBlank()) newJam else tugasToEdit.jam
                    ).apply { selesai = tugasToEdit.selesai } // Salin status 'selesai'
                }
                else -> {
                    println("Jenis tugas tidak dikenal. Tidak dapat mengedit.")
                    return
                }
            }
            daftarTugas[index] = updatedTugas
            println("Tugas berhasil diedit!")
        } else {
            println("Nomor tugas tidak valid.")
        }
    }

    fun tampilkanSemuaTugas() {
        if (daftarTugas.isEmpty()) {
            println("Belum ada tugas yang tercatat.")
            return
        }
        println("\n--- Daftar Semua Tugas ---")
        daftarTugas.forEachIndexed { index, tugas ->
            println("${index + 1}.")
            tugas.tampilkanDetail()
            println("--------------------")
        }
    }

    fun hapusTugas() {
        if (daftarTugas.isEmpty()) {
            println("Belum ada tugas untuk dihapus.")
            return
        }
        tampilkanSemuaTugas()
        print("Masukkan nomor tugas yang ingin dihapus: ")
        val index = getValidIntInput("Nomor tugas") - 1

        if (index >= 0 && index < daftarTugas.size) {
            val namaTugasDihapus = when (daftarTugas[index]) {
                is Tugas -> (daftarTugas[index] as Tugas).nama
                is TugasPrioritas -> (daftarTugas[index] as TugasPrioritas).nama
                else -> "Tugas Tidak Dikenal"
            }
            daftarTugas.removeAt(index)
            println("Tugas '$namaTugasDihapus' berhasil dihapus!")
        } else {
            println("Nomor tugas tidak valid.")
        }
    }

    fun tampilkanTugasSelesai() {
        val tugasSelesai = daftarTugas.filter { it.selesai } // Sekarang tidak error
        if (tugasSelesai.isEmpty()) {
            println("Belum ada tugas yang selesai.")
            return
        }
        println("\n--- Daftar Tugas Selesai ---")
        tugasSelesai.forEachIndexed { index, tugas ->
            println("${index + 1}.")
            tugas.tampilkanDetail()
            println("--------------------")
        }
    }

    fun tampilkanTugasBelumSelesai() {
        val tugasBelumSelesai = daftarTugas.filter { !it.selesai } // Sekarang tidak error
        if (tugasBelumSelesai.isEmpty()) {
            println("Semua tugas sudah selesai!")
            return
        }
        println("\n--- Daftar Tugas Belum Selesai ---")
        tugasBelumSelesai.forEachIndexed { index, tugas ->
            println("${index + 1}.")
            tugas.tampilkanDetail()
            println("--------------------")
        }
    }

    private fun getValidIntInput(prompt: String): Int {
        var input: Int? = null
        while (input == null) {
            try {
                print("$prompt: ")
                input = scanner.nextInt()
                scanner.nextLine() // Consume newline
            } catch (e: InputMismatchException) {
                println("Input tidak valid. Harap masukkan angka untuk $prompt.")
                scanner.nextLine() // Clear the invalid input
            }
        }
        return input
    }

    fun closeScanner() {
        scanner.close()
    }
}