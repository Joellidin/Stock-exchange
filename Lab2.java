
import java.io.*;
import java.util.*;

public class Lab2 {
	public static String pureMain(String[] commands) {
		// Priority queues containing bids with buyers and sellers comparators.
		PriorityQueue<Bid> buy_pq = new PriorityQueue<>(new BuyersComparator()); 
		PriorityQueue<Bid> sell_pq = new PriorityQueue<>(new SellersComparator());

		// Contains output text.
		StringBuilder sb = new StringBuilder();
		
		for(int line_no=0;line_no<commands.length;line_no++){
			// Divides input in pieces.
			String line = commands[line_no];
			if( line.equals("") )continue;

			String[] parts = line.split("\\s+");
			if( parts.length != 3 && parts.length != 4)
				throw new RuntimeException("line " + line_no + ": " + parts.length + " words");
			String name = parts[0];
			if( name.charAt(0) == '\0' )
				throw new RuntimeException("line " + line_no + ": invalid name");
			String action = parts[1];
			int price;
			try {
				price = Integer.parseInt(parts[2]);
			} catch(NumberFormatException e){
				throw new RuntimeException(
						"line " + line_no + ": invalid price");
			}

			if( action.equals("K") ) {
				buy_pq.add(new Bid(name, price));
			} else if( action.equals("S") ) {
				sell_pq.add(new Bid(name, price));
			} else if( action.equals("NK") ){
				buy_pq.update(new Bid(name, price), new Bid(name, Integer.parseInt(parts[3])));
			} else if( action.equals("NS") ){
				sell_pq.update(new Bid(name, price), new Bid(name, Integer.parseInt(parts[3])));
			} else {
				throw new RuntimeException(
						"line " + line_no + ": invalid action");
			}
			
			

			if( sell_pq.size() == 0 || buy_pq.size() == 0 )continue;

			// compare the bids of highest priority from each of
			// each priority queues.
			// if the lowest seller price is lower than or equal to
			// the highest buyer price, then remove one bid from
			// each priority queue and add a description of the
			// transaction to the output.
			BidComparator bc = new BidComparator();
			// O(nlog(n)).
			while(bc.compare(sell_pq.minimum(),buy_pq.minimum()) <= 0) {
				sb.append(buy_pq.minimum().name);
				sb.append(" buys from ");
				sb.append(sell_pq.minimum().name);
				sb.append(" for ");
				sb.append(buy_pq.minimum().bid);
				sb.append("kr\n");
				
				sell_pq.deleteMinimum();
				buy_pq.deleteMinimum();
				if(sell_pq.size() == 0 || buy_pq.size() == 0) {
					break;
				}
			}
		}

		sb.append("Order book:\n");

		sb.append("Sellers: ");
		// O(n(log(n)).
		while(sell_pq.size() > 0) {
			sb.append(sell_pq.minimum());
			if(sell_pq.size() > 1) {
				sb.append(", ");
			}
			sell_pq.deleteMinimum();
		}
		sb.append("\n");

		sb.append("Buyers: ");
		// O(nlog(n)).
		while(buy_pq.size() > 0) {
			sb.append(buy_pq.minimum());
			if(buy_pq.size() > 1) {
				sb.append(", ");
			}
			buy_pq.deleteMinimum();
		}
		
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		final BufferedReader actions;
		if( args.length != 1 ){
			actions = new BufferedReader(new InputStreamReader(System.in));
		} else {
			actions = new BufferedReader(new FileReader(args[0]));
		}

		List<String> lines = new LinkedList<String>();
		while(true){
			String line = actions.readLine();
			if( line == null)break;
			lines.add(line);
		}
		actions.close();

		System.out.println(pureMain(lines.toArray(new String[lines.size()])));
	}
}
