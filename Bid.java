

public class Bid {
	final public String name; // Name of person
	final public int bid; // Bid size

	public Bid(String name, int bid) {
		this.name = name;
		this.bid = bid;
	}

	public int hashCode() {
		return 1 + 23*bid + 31*name.hashCode();
	}

	// Checks equality of two Bids
	public boolean equals(Object obj){
		if (obj == null || !(obj instanceof Bid)) return false;
		Bid bid = (Bid) obj;
		BidComparator bc = new BidComparator();
		return bc.compare(this, bid) == 0;
	}
	
	// Returns object as string
	public String toString(){
		return this.name + " " + this.bid;
	}
}

