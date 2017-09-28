listOfMakanan = [:]
listOfMakananYangSudahJadi = [:]
listOfBahanMakanan = [:]
listOfCurrentPesanan = [:]
listOfCurrentPesananTemporary = [:]
countMeja = 0
confirmed = false
masuk = false
dine_in = false
tambah = {
    a, b -> a + b
}
kurang = {
    a, b -> a - b
}

class Makanan {
    def nama
    def harga
    def komposisiMakanan = []
    
    def static make(closure) {
        Makanan makanan = new Makanan()
        closure.delegate = makanan
        closure()
        return makanan
    }

    def nama(nama) {
        this.nama = nama
    }

    def harga(harga) {
        this.harga = harga
    }

    def bahan(namaBahan, jumlahBahan, satuan) {
        this.komposisiMakanan.add(new Tuple(namaBahan, jumlahBahan, satuan))
    }

    def getCetak() {
        println nama
        println harga
        for (bahan in komposisiMakanan) {
            println "\t" + bahan
        }    
    }
}

def operasiBahanMakanan(tipe, namaBahan, jumlah, satuan) {
    existBahanMakanan = listOfBahanMakanan.get(namaBahan)
    if (existBahanMakanan == null) {
        bahanMakanan = new Tuple(namaBahan, jumlah, satuan)
        listOfBahanMakanan[namaBahan] = bahanMakanan
    } else {
        if (existBahanMakanan[2] == satuan) {
            jumlahAkhir = tipe(existBahanMakanan[1], jumlah)
            bahanMakanan = new Tuple(namaBahan, jumlahAkhir, satuan)
            listOfBahanMakanan[namaBahan] = bahanMakanan
        } else {
            println "Satuannya seharusnya " + existBahanMakanan[2]
        }
    }
}

def operasiMeja(tipe, jumlah) {
    jumlahAkhir = tipe(countMeja, jumlah)
    if (jumlahAkhir < 0) {
        println "Tidak mengurangi meja lebih dari jumlah meja yang ada"
    } else {
        countMeja = jumlahAkhir
    }   
}

def bahanCukup(namaMakanan, jumlah) {
    for (bahan in listOfMakanan[namaMakanan].komposisiMakanan) {
        if (listOfBahanMakanan[bahan[0]][1] - bahan[1]*jumlah  < 0) {
            return false
        }
    }
    return true
}

def tambahPesanan(namaMakanan, jumlah) {
    if (listOfCurrentPesanan.get(namaMakanan) == null) {
        listOfCurrentPesanan[namaMakanan] = new Tuple(namaMakanan, jumlah)
    } else {
        listOfCurrentPesanan[namaMakanan] = new Tuple(namaMakanan, jumlah + listOfCurrentPesanan.get(namaMakanan)[1])
    }
}

def tambahPesananTemporary(namaMakanan, jumlah) {
    if (listOfCurrentPesananTemporary.get(namaMakanan) == null) {
        listOfCurrentPesananTemporary[namaMakanan] = new Tuple(namaMakanan, jumlah)
    } else {
        listOfCurrentPesananTemporary[namaMakanan] = new Tuple(namaMakanan, jumlah + listOfCurrentPesanan.get(namaMakanan)[1])
    }
}

def pesanMakanan(namaMakanan, jumlah) {
    if (masuk) {
        existMakanan = listOfMakanan.get(namaMakanan)
        if (existMakanan == null) {
            println "Makanan tidak terdapat dalam menu"
        } else {
            existMakanan = listOfMakananYangSudahJadi.get(namaMakanan)
            selisihJumlah = existMakanan[1] - jumlah
            if (selisihJumlah < 0) {
                if (bahanCukup(namaMakanan, -selisihJumlah)) {
                    // for (bahan in listOfMakanan[namaMakanan].komposisiMakanan) {
                    //     operasiBahanMakanan(kurang, bahan[0], bahan[1]*-selisihJumlah, bahan[2])
                    // }

                    tambahPesananTemporary(namaMakanan, jumlah)
                } else {
                    println "Bahan makanan tidak cukup untuk membuat pesanan"
                }
            } else {
                makananSudahJadi = listOfMakananYangSudahJadi.get(namaMakanan)
                listOfMakananYangSudahJadi[namaMakanan] = new Tuple(makananSudahJadi[0], makananSudahJadi[1]-jumlah)
                tambahPesanan(namaMakanan, jumlah)
            }
        }
    } else {
        println "Anda sedang tidak berada di kantin"
    }
}

def batalPesan(namaMakanan, jumlah) {
    if (masuk) {
        if (!confirmed) {
            existPesanan = listOfCurrentPesanan.get(namaMakanan)
            if (existPesanan == null) {
                println "Makanan tidak terdapat dalam pesanan"
            } else {
                existPesananTemp = listOfCurrentPesananTemporary.get(namaMakanan)
                selisihJumlah = existPesananTemp[1] - jumlah
                if (selisihJumlah <= 0) {
                    listOfCurrentPesananTemporary.remove(namaMakanan)
                    selisihJumlah = listOfCurrentPesanan.get(namaMakanan)[1] + selisihJumlah
                    if (selisihJumlah <= 0) {
                        listOfCurrentPesanan.remove(namaMakanan)
                        listOfMakananYangSudahJadi[namaMakanan] = new Tuple(existMakanan[0], listOfMakananYangSudahJadi[namaMakanan][1]+existPesanan[1])
                    } else {
                        listOfCurrentPesanan[namaMakanan] = new Tuple(existPesanan[0], selisihJumlah)
                        listOfMakananYangSudahJadi[namaMakanan] = new Tuple(existMakanan[0], listOfMakananYangSudahJadi[namaMakanan][1]+jumlah)
                    }
                }
                else {
                    listOfCurrentPesananTemporary[namaMakanan] = new Tuple(namaMakanan, selisihJumlah)
                }
            }
        } else {
            println "Anda sudah melakukan konfirmasi pesanan tidak dapat membatalkan lagi"
        }
    } else {
        println "Anda sedang tidak berada di kantin"
    }
}

def pelangganMasuk(mode) {
    if (mode == "dine-in") {
        if (countMeja == 0) {
            println "Tidak ada meja yang tersedia"
            masuk = false
            dine_in = false
        } else {
            operasiMeja(kurang, 1)
            masuk = true
            dine_in = true
        }
    } else {
        masuk = true
        dine_in = false
    } 
}

def pelangganKeluar() {
    masuk = false
    confirmed = false
    if (dine_in)
        operasiMeja(tambah, 1)
}

def konfirmasiPesanan() {
    confirmed = true
    for (makanan in listOfCurrentPesananTemporary) {
        for (bahan in listOfMakanan[makanan.key].komposisiMakanan) {
            operasiBahanMakanan(kurang, bahan[0], bahan[1]*makanan.value[1], bahan[2])
        }
    }
    listOfCurrentPesananTemporary = [:]
    listOfCurrentPesanan = [:]
}


//---------------------------GRAMMAR--------------------
int kasir_kas = 0
tambahkan = {m,j ->  pesanMakanan(m,j)}
kurangi = {m,j ->  batalPesan(m,j)}
bayar_pesanan = {uang -> kasir_kas = kasir_kas+uang}
masuk = {mode -> pelangganMasuk(mode)}
keluar = { pelangganKeluar() }
konfirmasi = { konfirmasiPesanan() }

/*Operasi pelanggan untuk bayar pesanan. Asumsi: pelanggan pasti memberikan uang pas
    contoh: mau bayar_pesanan sebesar "800"
*/
def mau(action) {
    bayar_pesanan: [sebesar: {u -> action(u.toInteger())}]
}

/*Operasi pelanggan untuk menambah/mengurangi pesanan
    Contoh menambah pesanan: tolong tambahkan pesanan "Pizza" sebanyak "5" 
    Contoh mengurangi pesanan: tolong kurangi pesanan "Pizza" sebanyak "2" 
*/
def tolong(action) {
    tambahkan:  [pesanan: { m -> [sebanyak: { j -> action(m,j.toInteger()) }] }] 
    kurangi: [pesanan: { m -> [sebanyak: { j -> action(m,j.toInteger()) }] }]
}

/*Operasi untuk pelanggan:
    Bila pelanggan keluar kantin: pelanggan keluar
    Bila pelanggan masuk:   pelanggan masuk untuk "dine-in"
                            pelanggan masuk untuk "takeaway"
    Bila pelanggan konformasi pesanan: pelanggan konfirmasi pesanan
*/
def pelanggan(action) {
    if (action==keluar) pelangganKeluar()
    else if (action==konfirmasi) konfirmasiPesanan()
    else{
        masuk: [ untuk : { mode -> action(mode) }]
    }
}

meja = {m, j -> operasiMeja(m, j)}
def menambah(action) {
    meja: [sebanyak : {j -> [buah : action(tambah, j)]}]
}

makanan = Makanan.make {
    nama "Pizza"
    harga 100000
    bahan "cabe", 5, "gram"
    bahan "tepung", 10, "gram"
    bahan "ayam", 3, "ekor"
}
menambah meja sebanyak 10 buah
listOfMakanan[makanan.nama] = makanan
listOfMakananYangSudahJadi[makanan.nama] = new Tuple(makanan.nama, 10)

//operasiBahanMakanan(tambah, "cabe", 5, "gram")
operasiBahanMakanan(tambah, "tepung", 10, "gram")
operasiBahanMakanan(tambah, "ayam", 3, "ekor")
operasiBahanMakanan(tambah, "cabe", 5, "gram")
operasiBahanMakanan(tambah, "tepung", 10, "gram")
operasiBahanMakanan(tambah, "ayam", 3, "ekor")
operasiBahanMakanan(tambah, "cabe", 5, "gram")
operasiBahanMakanan(tambah, "tepung", 10, "gram")
operasiBahanMakanan(tambah, "ayam", 3, "ekor")
operasiBahanMakanan(tambah, "cabe", 5, "gram")
operasiBahanMakanan(tambah, "tepung", 10, "gram")
operasiBahanMakanan(tambah, "ayam", 3, "ekor")
operasiBahanMakanan(tambah, "cabe", 5, "gram")
operasiBahanMakanan(tambah, "tepung", 10, "gram")
operasiBahanMakanan(tambah, "ayam", 3, "ekor")

//pelangganMasuk('dine-in')

pelanggan masuk untuk "dine-in"
tolong tambahkan pesanan "Pizza" sebanyak "5" 
tolong tambahkan pesanan "Pizza" sebanyak "5"
println listOfMakanan
println listOfMakananYangSudahJadi
println listOfBahanMakanan
println listOfCurrentPesanan
println listOfCurrentPesananTemporary
println()
tolong tambahkan pesanan "Pizza" sebanyak "3"
println listOfMakanan
println listOfMakananYangSudahJadi
println listOfBahanMakanan
println listOfCurrentPesanan
println listOfCurrentPesananTemporary
println()

tolong kurangi pesanan "Pizza" sebanyak "52"
println listOfMakanan
println listOfMakananYangSudahJadi
println listOfBahanMakanan
println listOfCurrentPesanan
println listOfCurrentPesananTemporary
println()
pelanggan konfirmasi
pelanggan keluar
//konfirmasiPesanan()
//pelangganKeluar()

println listOfMakanan
println listOfMakananYangSudahJadi
println listOfBahanMakanan
println listOfCurrentPesanan
println listOfCurrentPesananTemporary