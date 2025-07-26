import service.TugasManager // Pastikan import ini benar sesuai package TugasManager Anda
import java.util.InputMismatchException
import java.util.Scanner

fun main() {
    val tugasManager = TugasManager()
    val scanner = Scanner(System.`in`)
    var pilihan: Int
    do {
        println("\n===== Aplikasi Pencatat Tugas =====")
        println("1. Tambah Tugas")
        println("2. Tandai Tugas Selesai")
        println("3. Edit Tugas")
        println("4. Tampilkan Semua Tugas")
        println("5. Hapus Tugas")
        println("6. Tampilkan Tugas Selesai")
        println("7. Tampilkan Tugas Belum Selesai")
        println("8. Keluar")
        print("Pilih menu (1-8): ")
        try {
            pilihan = scanner.nextInt()
            scanner.nextLine() // Consume newline

            when (pilihan) {
                1 -> tugasManager.tambahTugas()
                2 -> tugasManager.tandaiTugasSelesai()
                3 -> tugasManager.editTugas()
                4 -> tugasManager.tampilkanSemuaTugas()
                5 -> tugasManager.hapusTugas()
                6 -> tugasManager.tampilkanTugasSelesai()
                7 -> tugasManager.tampilkanTugasBelumSelesai()
                8 -> println("Terima kasih telah menggunakan aplikasi!")
                else -> println("Pilihan tidak valid. Silakan coba lagi.")
            }
        } catch (e: InputMismatchException) {
            println("Input tidak valid. Harap masukkan a\ngka.")
            scanner.nextLine() // Clear the invalid input
            pilihan = 0 // Set to 0 to continue the loop
        }

    } while (pilihan != 8)

    tugasManager.closeScanner()
    scanner.close()
}