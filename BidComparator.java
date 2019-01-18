
import java.util.Comparator;

public class BidComparator implements Comparator<Bid> {
	// Compares two bids.
	@Override
	public int compare(Bid a, Bid b) {
		if(a.bid < b.bid) {
			return -1;
		} else if(a.bid > b.bid) {
			return 1;
		} else
			return 0;
	}

}
