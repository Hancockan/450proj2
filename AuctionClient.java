import auction.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Scanner;


public class AuctionClient
{
  static AuctionUser auctionImpl;

  public static void main(String args[])
    {

    Scanner scan = new Scanner(System.in);

      try{
        
        ORB orb = ORB.init(args, null);

        
        org.omg.CORBA.Object objRef = 
            orb.resolve_initial_references("NameService");
         
          
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
 
        
        String name = "auction";
        auctionImpl = AuctionUserHelper.narrow(ncRef.resolve_str(name));

        System.out.println("Obtained a handle on server object: " + auctionImpl);
        //System.out.println(auctionImpl.view_high_bidder());
        
	//code added for auction use
	//first get the user name
	
	System.out.println("Enter Username:");
	String user_name = scan.nextLine();

	System.out.println("Enter a number corresponding to the action you'd like to perform:");
	System.out.println("(1) Offer item\n(2) Sell\n(3) Bid\n(4) View the highest bidder\n(5) View bid status\n(6) View auction status");
	
	while(true){


	String command = scan.nextLine();

	switch(command){
		case "1":
			Float item_price = -1.0f;
			//ask for item desc
			System.out.println("Please enter an item description:");
			String item_desc = scan.nextLine();
			System.out.println("Would you like to enter an initial price?");
			if(scan.nextLine().equals("yes")){
				System.out.println("Please enter a price:");
				item_price = Float.parseFloat(scan.nextLine());
			}
			if(auctionImpl.offer_item(user_name, item_desc, item_price) == true){
				System.out.println("Item has successfully gone to auction");
			}else{
				System.out.println("Item failed to go to auction");
			}
			break;
		case "2":
			if(auctionImpl.sell(user_name) == true){
				System.out.println("Auction for your item successfully ended");
			}else{
				System.out.println("Selling failed");
			}
			break;
		case "3":
			System.out.println("Enter the amount you would like to bid:");
			float amount = Float.parseFloat(scan.nextLine());
			if(auctionImpl.bid(user_name, amount) == true){
				System.out.println("bid successfully placed");
			}else{
				System.out.println("bid failed to place");
			}
			break;
		case "4":
			System.out.println(auctionImpl.view_high_bidder());
			break;
		case "5":
			System.out.println(auctionImpl.view_bid_status());
			break;
		case "6":
			System.out.println(auctionImpl.view_auction_status());
			break;
		default:
			System.out.println("Invalid command number");
			break;


		}

	System.out.println("Enter a command:");
	}


        //auctionImpl.shutdown();

        } catch (Exception e) {
          System.out.println("ERROR : " + e) ;
          e.printStackTrace(System.out);
          }
    }

}
