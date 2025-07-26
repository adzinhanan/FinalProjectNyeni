package interfaces

interface TugasOperasi {
    fun tampilkanDetail()
    fun tandaiSelesai()
    fun tandaiBelumSelesai()
    var selesai: Boolean
}