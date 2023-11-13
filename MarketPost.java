import java.io.*;
import java.util.Scanner;
public class MarketPost{
  Scanner in = new Scanner(System.in);
  private static String currentUser = "";
  private static MarketGet get;
  private static FileEditor editor;
  public MarketPost(String User){
    currentUser = User;
    get = new MarketGet(currentUser);
    editor = new FileEditor("Users/"+ currentUser +".txt");
  }
  public void post(){
      String title = "";
      String description = "";
      String price = "";
      
      try{
        System.out.println("Enter a title for your listing");
        //takes the spaces from the inputs and adds underscores so its a valid file name
        title = String.join("_", in.nextLine().split(" "));
        //specific listings file
        FileWriter myWriter = new FileWriter("Listings/"+ title +".txt");
        //file with all listings, append set to true so file contents arent deleted
        FileWriter publicList = new FileWriter("Listings.txt", true);
        myWriter.write(currentUser + "'s item \n");
        System.out.println("Enter a description for your item");
        description = in.nextLine();
        myWriter.write("Description: "+ description+"\n");
        System.out.println("Enter a price for your item");
        price = in.nextLine();
        myWriter.write("Price: $"+ price);
        System.out.println("Listing succesfully added");
        //adds the listing to listings.txt file
        publicList.write(title +": $" + price+"\n");
        //adds the listing to user file
        editor.addListing(title, 2);
        myWriter.close();
        publicList.close();
      }catch (Exception e){
       e.getStackTrace();
       }
  }
  public void delete(){
    try{
    System.out.println("which listing do you want to delete? Enter the title to delete");
    get.viewListings(2);
    String Filename = in.next();
    String path = "Listings/"+Filename+".txt";
    File file = new File(path);
    editor.deleteListing(Filename, 2);
    if (file.exists()) {
         boolean delete = file.delete();
         if (delete) {
            System.out.println("Listing has been deleted");
         } else {
            System.out.println("Failed to find the listing entered");
         }
      }   
    }catch (Exception e){
    e.getStackTrace();
    }
  }
  public void deleteListing(String listing){
    try{
    String path = "Listings/"+listing+".txt";
    File file = new File(path);
    if (file.exists()) {
          file.delete();
      }   
    }catch (Exception e){
    e.getStackTrace();
    }
    
  }
  public void payListingOwner(String listing, String amount){
    try{
      String user="";
      int line=0;
      File listingFile = new File("Listings/"+listing+".txt");
      Scanner reader = new Scanner(listingFile);
      user = reader.nextLine().replace("'s item", "").trim();
      FileEditor userToPay = new FileEditor("Users/"+user+".txt");
      int pay = Integer.parseInt(userToPay.returnInfo(4).get(0))+Integer.parseInt(amount);
      userToPay.updateCredit(Integer.toString(pay));
    }catch(Exception err){
      System.out.println("ERROR HERE ERROR HERE");
      err.printStackTrace();
    } 
    
  }
}

      
    
    
    
    
    
   
