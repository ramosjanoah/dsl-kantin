listOfMakanan = [:]
listOfBahanMakanan = [:]


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
            throw new Exception("Satuannya seharusnya " + existBahanMakanan[2])
        }
    }
}

makanan = Makanan.make {
    nama "Enak"
    harga 10000
    bahan "ayam", 3, "suwir"
    bahan "kucing", 2, "ekor" 
}


