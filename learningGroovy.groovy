show = { println it }
square_root = { Math.sqrt(it) }

def please(action) {
  [the: { what ->
    [of: { n -> action(what(n)) }]
  }]
}

tambahkan = {a,b -> a.add(b)}
pesanan = []

def tolong(action) {
	[makanan: { what ->
		[ke: {n -> action(n, what)}
		] 
		}]	
}






// ------- KOKI SECTION -------

koki = 'koki'

def untuk(jenis_pegawai) {
	if (jenis_pegawai == 'koki') {
		[	buatkan_makanan: { makanan -> println 'Function: Koki Buatkan Makanan ' + makanan}, 
			buatkan_minuman: { minuman -> println 'Function: Koki Buatkan Minuman ' + minuman},
			siapkan_makanan: { minuman -> println 'Function: Koki Siapkan Makanan ' + minuman},
			siapkan_minuman: { minuman -> println 'Function: Koki Siapkan Minuman ' + minuman},
		]	
	}
}

