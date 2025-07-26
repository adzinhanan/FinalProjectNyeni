package abstract_classes

import interfaces.TugasOperasi
import model.Prioritas

abstract class BaseTugas(
    open val nama: String,
    open val hari: String,
    open val tanggal: Int,
    open val bulan: String,
    open val tahun: Int,
    open val jam: String
) : TugasOperasi {
    override var selesai: Boolean = false

    override fun tandaiSelesai() {
        selesai = true
    }

    override fun tandaiBelumSelesai() {
        selesai = false
    }

    abstract fun getPrioritasText(): String
}