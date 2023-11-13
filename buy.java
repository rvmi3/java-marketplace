import java.io.*;
import java.util.*;

public class buy extends MarketGet {
  private static String currentUser;
  private static FileEditor editor;
  protected static MarketPost post;
  public buy(String user) {
    super(user);
    post = new MarketPost(user);
  }

  public void buyCart() {
    // super.viewListings(3);
    ArrayList<String> cart = super.editor.returnInfo(3);
    ArrayList<String> usersFromCart = new ArrayList<String>();
    try {
      File listings = new File("Listings.txt");
      Scanner reader = new Scanner(listings);
      int total = 0;
      while (reader.hasNextLine()) {
          String currentLine = reader.nextLine();
          String itemName = currentLine.replace("$", "").split(" ")[0].replace(":", "");
          int price = Integer.parseInt(currentLine.replace("$", "").split(" ")[1]);
          for (int i=0;i<cart.size();i++) {
            if (itemName.equals(cart.get(i))) {
              total += price;
              usersFromCart.add(itemName+" "+Integer.toString(price));
            }
          }
          
        }
      
      int userCredits = Integer.parseInt(super.editor.returnInfo(4).get(0));
      if(userCredits < total){
        System.out.print("$"+total);
        System.out.println("Not enough credits");
        
      }else{
        System.out.println("Cart:\n"+cart+"\n");
        System.out.println("Total: $" + total + "\nYou have $"+ userCredits + ".\nWould you like to confirm purchase\n");
        System.out.println("'y' for YES\n'n' for NO");
        Scanner in = new Scanner(System.in);
        while(in.hasNextLine()){
          String choice = in.nextLine();
          if(choice.toUpperCase().equals("Y")){
            int newUserCredits = userCredits - total;
            super.editor.updateCredit(Integer.toString(newUserCredits));
            super.editor.deleteAll(3);
            for(String user: usersFromCart){
              post.payListingOwner(user.split(" ")[0], user.split(" ")[1]);
              post.deleteListing(user.split(" ")[0]);
            }
            
            System.out.println("Purchase Succesful!");
            return;
          }else if(choice.toUpperCase().equals("N")){
            return;
          }
        }
        
      }
    } catch (FileNotFoundException err) {
      err.printStackTrace();
    }

  }
  public void buyListings(Boolean startFromPartTwo, String itemToPurchase){
      String title = "";
      Scanner in = new Scanner(System.in);
      int price = 0;
      if(!startFromPartTwo){
        super.viewListings(5);
        System.out.println("\nEnter listing you would like to purchase");        
        title = in.nextLine();
        super.viewDetails(title);
      }else{title=itemToPurchase;}
      System.out.println("\nConfirm Purhase?\n'y' for yes\n'n' for no'");
      String choice = in.nextLine();
      if(choice.toUpperCase().equals("Y")){
        try{
          File listing = new File("Listings/"+title+".txt");
          Scanner reader = new Scanner(listing);
          for(int i =0;i<3;i++){
            if(i==2){
              price = Integer.parseInt(reader.nextLine().replace("$", "").split(" ")[1].trim());
            }else{
              reader.nextLine();
            }
          }
          int userCredits = Integer.parseInt(super.editor.returnInfo(4).get(0));
          if(userCredits<price){
            System.out.println("\nNot enough credits");
            return;
          }
          int newUserCredits = Integer.parseInt(super.editor.returnInfo(4).get(0)) - price;
          super.editor.updateCredit(Integer.toString(newUserCredits));
          post.payListingOwner(title, Integer.toString(price));
          post.deleteListing(title);

        }catch(FileNotFoundException err){err.printStackTrace();}
      }else if(choice.toUpperCase().equals("N")){return;}
    }
    
  }

  


