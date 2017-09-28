show = { println it }
square_root = { Math.sqrt(it) }

pesanan = []
int kasir_kas = 0

tambahkan = {
	a,b ->
	int idx = 0
	boolean stop = false
	if (a.isEmpty()) a.add(b)
	else {
		while (idx<a.size() && !stop){
			if (a[idx][0]==b[0]) stop = true
			else{
				idx=idx+1
			}
		}
		if (stop){
			a.set(idx, new Tuple(a[idx][0], (a[idx][1]+b[1])))
		}
	}
}
kurangi = {
	a,b ->
	int idx = 0
	boolean stop = false
	if (!(a.isEmpty())) {
		while (idx<a.size() && !stop){
			if (a[idx][0]==b[0]) stop = true
			else{
				idx=idx+1
			}
		}
		if (stop){
			if (b[1]<a[idx][1]) {
				a.set(idx, new Tuple(a[idx][0], (a[idx][1]-b[1])))
			}else{
				a.remove(idx)
			}
		}
	}
}


def tolong(action) {
	tambahkan:	[makanan: { m -> [sebanyak: { j -> obj = new Tuple(m,j.toInteger())	
		[ke: { n -> action(n, obj) }] }] }]
	
	kurangi: [makanan: {m -> [sebanyak: { j -> obj = new Tuple(m,j.toInteger()) 
		[ke: { n -> action(n, obj )}]}]}]

}



tolong tambahkan makanan "pizza" sebanyak "5" ke pesanan
tolong tambahkan makanan "pizza" sebanyak "5" ke pesanan
tolong kurangi makanan "pizza" sebanyak "2" ke pesanan


print pesanan 
