package auction;


/**
* auction/AuctionUserPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from auction.idl
* Sunday, March 8, 2020 7:11:24 PM CDT
*/

public abstract class AuctionUserPOA extends org.omg.PortableServer.Servant
 implements auction.AuctionUserOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("offer_item", new java.lang.Integer (0));
    _methods.put ("sell", new java.lang.Integer (1));
    _methods.put ("bid", new java.lang.Integer (2));
    _methods.put ("view_high_bidder", new java.lang.Integer (3));
    _methods.put ("view_bid_status", new java.lang.Integer (4));
    _methods.put ("view_auction_status", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // auction/AuctionUser/offer_item
       {
         String user_id = in.read_string ();
         String item_desc = in.read_string ();
         float initial_price = in.read_float ();
         boolean $result = false;
         $result = this.offer_item (user_id, item_desc, initial_price);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // auction/AuctionUser/sell
       {
         String user_id = in.read_string ();
         boolean $result = false;
         $result = this.sell (user_id);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // auction/AuctionUser/bid
       {
         String user_id = in.read_string ();
         float price = in.read_float ();
         boolean $result = false;
         $result = this.bid (user_id, price);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // auction/AuctionUser/view_high_bidder
       {
         String $result = null;
         $result = this.view_high_bidder ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // auction/AuctionUser/view_bid_status
       {
         String $result = null;
         $result = this.view_bid_status ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // auction/AuctionUser/view_auction_status
       {
         String $result = null;
         $result = this.view_auction_status ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:auction/AuctionUser:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public AuctionUser _this() 
  {
    return AuctionUserHelper.narrow(
    super._this_object());
  }

  public AuctionUser _this(org.omg.CORBA.ORB orb) 
  {
    return AuctionUserHelper.narrow(
    super._this_object(orb));
  }


} // class AuctionUserPOA
