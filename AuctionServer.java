import auction.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import java.util.Properties;

class AuctionUserImpl extends AuctionUserPOA {
  private ORB orb;

  boolean auction_in_progress = false;
  String seller = null;
  String item_desc = null;
  float price = 0.0f;
  long auction_start_time = 0;
  String winning_id = null;
  float winning_price = 0.0f;

  //auctions will last 2 minutes

  private void update(){
	if(auction_in_progress){
		if(System.currentTimeMillis() - auction_start_time > 120000){
			//auction ended
			auction_in_progress = false;
			seller = null;
			item_desc = null;
			price = 0.0f;
		}
	}

  }


  public void setORB(ORB orb_val) {
    orb = orb_val; 
  }
   
  public boolean offer_item(String user_id, String item_desc0, float initial_price){
	update();
	//System.out.println(item_desc);
	if(auction_in_progress){
		return false;
	}else{
		auction_start_time = System.currentTimeMillis();
		auction_in_progress = true;	
		seller = user_id;
		item_desc = item_desc0;
		if(initial_price < 0){
			price = 1.0f; //default price if one is not given
		}else{
			price = initial_price;
		}
		winning_price = price;
		return true;
	}
  }

  public boolean sell(String user_id){
	update();
	if(user_id != seller){
		return false;
	}else{
		//end the auction
		auction_in_progress = false;
		seller = null;
		item_desc = null;
		return true;
	}
  }

  public boolean bid(String user_id, float price){
	update();
	if(!auction_in_progress){
		return false;
	}else{
		if(price > winning_price){
			winning_id = user_id;
			winning_price = price;
		}
		return true;
	}
  }

  public String view_high_bidder(){
	update();
  	return winning_id;
  }

  public String view_bid_status(){
	update();
    return "highest winnning bid is " + winning_price;
  }

  public String view_auction_status(){
	update();
	if(!auction_in_progress){
		return "empty";
	}else{
		return "active" + ", " + item_desc + ", " + winning_price;
	}
  }


//method below from example
 
  // implement sayHello() method
  public String sayHello() {
    return "\nHello world !!\n";
  }
               
  // implement shutdown() method
  public void shutdown() {
    orb.shutdown(false);
  }
}

public class AuctionServer {

  public static void main(String args[]) {
    try{
      // create and initialize the ORB
      ORB orb = ORB.init(args, null);
      
      // get reference to rootpoa & activate the POAManager
      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();
      
      // create servant and register it with the ORB
      AuctionUserImpl auctionuserImpl = new AuctionUserImpl();
      auctionuserImpl.setORB(orb); 
      
      // get object reference from the servant
      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(auctionuserImpl);
      AuctionUser href = AuctionUserHelper.narrow(ref);

      // get the root naming context
      // NameService invokes the name service
      org.omg.CORBA.Object objRef =
          orb.resolve_initial_references("NameService");
      // Use NamingContextExt which is part of the Interoperable
      // Naming Service (INS) specification.
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);


      // bind the Object Reference in Naming
      String name = "auction";
      NameComponent path[] = ncRef.to_name( name );
      ncRef.rebind(path, href);
      
      System.out.println("AuctionServer ready and waiting ...");
      
      // wait for invocations from clients
      orb.run();
    }

      catch (Exception e) {
        System.err.println("ERROR: " + e);
        e.printStackTrace(System.out);
      }
          
      System.out.println("AuctionServer Exiting ...");
        
  }
}
















