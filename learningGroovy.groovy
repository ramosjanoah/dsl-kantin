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




tolong tambahkan makanan "pizza" ke pesanan



print pesanan 