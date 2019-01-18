public class BuyersComparator extends BidComparator {
	// Compares two buyer bids. Returns -1 if the first bid has higher priority than the second bid.
	@Override
	public int compare(Bid a, Bid b) {
		return super.compare(b, a);	
	}

}
