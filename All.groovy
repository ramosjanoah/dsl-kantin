listOfMakanan = [:]
listOfMakananYangSudahJadi = [:]
listOfBahanMakanan = [:]
listOfCurrentPesanan = [:]
countMeja = 0

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

def pesanMakanan(namaMakanan, jumlah) {
    existMakanan = listOfMakanan.get(namaMakanan)
    if (existMakanan == null) {
        println "Makanan tidak terdapat dalam menu"
    } else {
        existMakanan = listOfMakananYangSudahJadi.get(namaMakanan)
        selisihJumlah = existMakanan[1] - jumlah
        if (selisihJumlah < 0) {
            if (bahanCukup(namaMakanan, -selisihJumlah)) {
                for (bahan in listOfMakanan[namaMakanan].komposisiMakanan) {
                    operasiBahanMakanan(kurang, bahan[0], bahan[1]*-selisihJumlah, bahan[2])
                }
                tambahPesanan(namaMakanan, jumlah)
            } else {
                println "Bahan makanan tidak cukup untuk membuat pesanan"
            }
        } else {
            makananSudahJadi = listOfMakananYangSudahJadi.get(namaMakanan)
            listOfMakananYangSudahJadi[namaMakanan] = new Tuple(makananSudahJadi[0], makananSudahJadi[1]-jumlah)
            tambahPesanan(namaMakanan, jumlah)
        }
    }
}

def batalPesan(namaMakanan, jumlah) {
    existPesanan = listOfCurrentPesanan.get(namaMakanan)
    if (existPesanan == null) {
        println "Makanan tidak terdapat dalam pesanan"
    } else {
        selisihJumlah = existPesanan[1] - jumlah
        if (selisihJumlah <= 0) {
            listOfCurrentPesanan.remove(namaMakanan)
            listOfMakananYangSudahJadi[namaMakanan] = new Tuple(existMakanan[0], listOfMakananYangSudahJadi[namaMakanan][1]+existPesanan[1])
        } else {
            listOfCurrentPesanan[namaMakanan] = new Tuple(existPesanan[0], selisihJumlah)
            listOfMakananYangSudahJadi[namaMakanan] = new Tuple(existMakanan[0], listOfMakananYangSudahJadi[namaMakanan][1]+jumlah)
        }
        
    }
}

makanan = Makanan.make {
    nama "Pizza"
    harga 100000
    bahan "cabe", 5, "gram"
    bahan "tepung", 10, "gram"
    bahan "ayam", 3, "ekor"
}
listOfMakanan[makanan.nama] = makanan
listOfMakananYangSudahJadi[makanan.nama] = new Tuple(makanan.nama, 10)

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
operasiBahanMakanan(tambah, "cabe", 5, "gram")
operasiBahanMakanan(tambah, "tepung", 10, "gram")
operasiBahanMakanan(tambah, "ayam", 3, "ekor")

pesanMakanan("Pizza", 10)
pesanMakanan("Pizza", 5)
batalPesan("Pizza", 2)
println listOfMakanan
println listOfMakananYangSudahJadi
println listOfBahanMakanan
println listOfCurrentPesanan
