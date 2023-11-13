import java.io.*;
import java.util.Scanner;
public class MarketGet{
  protected static String currentUser = "";
  
  protected static FileEditor editor;
  public MarketGet(String user){
    currentUser = user;
    editor = new FileEditor("Users/"+currentUser+".txt");
  }
  
  public void viewListings(int option){
    Scanner reader;
    File file;
    int line = 1;
    String title = "";
    String choiceStr="";
    int choiceInt = 0;
    try{
      //switches between options
      switch(option){
        case 1:
          //All Listings
          file = new File("Listings.txt");
          reader = new Scanner(file);
          while(reader.hasNextLine()){
            System.out.println(reader.nextLine());
          }
          System.out.println("Enter specific listings title to view details or type 'back' to return");
          Scanner in = new Scanner(System.in);
          while(in.hasNextLine()){
            choiceStr = in.nextLine();
            if(choiceStr.toUpperCase().equals("BACK")){return;}else{title = choiceStr;}
            if(viewDetails(title)){
              System.out.println("What would you like to do:\n1 - Buy Item\n2 - Add Item to Cart\n3 - Return to Listings\n");
            while(in.hasNextLine()){
              choiceStr = in.nextLine().replace(" ", "");   
              switch(choiceStr){
              default:
                System.out.println("Enter a valid input");
                break;
              case "1":
                buy buy = new buy(currentUser);
                buy.buyListings(true, title);
                break;
              case "2":
                editor.addListing(title, 3);
                System.out.println("Item added to cart");
                break;
              case "3":
                viewListings(1);
                break;
              }
              break;
              
            }
            break;
          }   
             
        }
            
          
          
          
          break;
        case 2:
          //Personal Listings
          System.out.println(editor.returnInfo(2));
          break;
        case 3:
          //Cart items
          System.out.println(editor.returnInfo(3));
          break;
        case 4:
          //Credits
          System.out.println(editor.returnInfo(4));
          break;
        case 5:
          file = new File("Listings.txt");
          reader = new Scanner(file);
          while(reader.hasNextLine()){
            System.out.println(reader.nextLine());
          }
          
          
      }
      
    }catch(FileNotFoundException err){
      System.out.println("Listing not found");
      viewListings(1);
    }
  }
  public Boolean viewDetails(String title){
    try{
      File file = new File("Listings/"+ title +".txt");
      Scanner reader = new Scanner(file);
      while(reader.hasNextLine()){
        System.out.println(reader.nextLine());
      }
      return true;
    } catch(FileNotFoundException err){
        System.out.println("No Listing found, enter exact title");
      return false;
    }
    
  }
  public void searchListings(){
    
    try{
      Scanner in =new Scanner(System.in);
      System.out.println("Enter the name of the item to be searched: ");
      String name=in.next();
      File newFile=new File("Listings.txt");
      Scanner read=new Scanner(newFile);
      while(read.hasNextLine()){
        String j = read.nextLine();
        if(j.indexOf(name)>-1){
          System.out.println(j);
        }
      }
      read.close();
             
    }
    catch(FileNotFoundException err){
    err.printStackTrace();
    }
    
  }

  }
  
  
  
    
     
    
  
    


  