import java.util.Scanner;
import java.io.*;

public class Main {

  static Boolean userActive = false;
  static Scanner in = new Scanner(System.in);
  //commands
  static String[] cmds = { "/search", "/buy", "/view", "/post", "/delete", "/help", "/logout" };

  public static void main(String[] args) {
    //mainpage
    login();
  }
  static void login() {
    System.out.println("Welcome to the marketplace\n");
    System.out.println("Enter user and passkey to open marketplace, /r to register");
    // checks for current command
    while (in.hasNextLine()) {
      try{
        String command = in.nextLine();
      if (command.indexOf(' ') >= 0) {
        // if there is a space it will split the command by it
        String[] userInfo = command.split(" ");
        // only two items in array because the first two line are username and password
        String[] retrievedUser = new String[2];
        int lines = 0;
        try {

          File userFile = new File("Users/" + userInfo[0] + ".txt");
          Scanner reader = new Scanner(userFile);
          // only want to read the first two lines
          while (lines < 2) {
            String data = reader.nextLine();
            retrievedUser[lines] = data;
            lines++;
          }
          // checks if user password matches file password
          if (userInfo[1].equals(retrievedUser[1].split(" ")[1])) {
            Homepage(userInfo[0], false);
          } else {
            System.out.println("Wrong password, please enter information again");
          }

          reader.close();

        } catch (FileNotFoundException err) {
          //if no user file was found then the user doesnt exist
          System.out.println("User does not exist, please register if you are a new user");
        }
      } else if (command.equals("/r")) {
        register();
      } else {
        //if no input is recognized then this is the fallback statement
        System.out.println("Enter a valid input");
      }
        
      }catch(Exception e){System.out.println("Invalid input");}
      

    }

  }
  static void register() {
    System.out.println("Enter a username");
    while (in.hasNextLine()) {
      try {
        String Username = in.nextLine();
        //creates the user file
        File currentUser = new File("Users/" + Username + ".txt");
        //this function will return true if the user doesnt exist
        if (currentUser.createNewFile()) {
          System.out.println("Enter a password");
          FileWriter pass = new FileWriter("Users/" + Username + ".txt");
          String password = in.nextLine();
          //user is created with inputed username and password, listings cart and credits are created as well
          pass.write("Username: " + Username + "\nPassword: " + password + "\nListings:[]\nCart:[]\nCredits:[50000]");
          pass.close();
          //user is now registered and is sent to homepage
          Homepage(Username, true);
        } else {
          System.out.println("User already exists");
        }

      } catch (IOException err) {
        err.printStackTrace();
      }

    }
  }
  static void Homepage(String User, Boolean newUser) {
    userActive = true;
    MarketPost post = new MarketPost(User);
    MarketGet get = new MarketGet(User);
    buy buy = new buy(User);
    //if else check to see if logged in user is a new user
    if (newUser) {
      System.out.println("Welcome " + User + "! To get started try typing /help for a list of commands");
    } else {
      System.out.println("Welcome back " + User + " /help for a list of commands");
    }
    //this while loop back is essentially the rest of the program, all commands are called from here
    while (in.hasNextLine()) {
      String currentInput = in.nextLine();
      System.out.println(currentInput);
      Scanner in = new Scanner(System.in);
      //loops through command list
      for (int i = 0; i < cmds.length; i++) {
      //if commands matchs a command in the command list then it will run the switch-case with the number value of command
        try{
          if (currentInput.equals(cmds[i])) {
          switch (i) {
            default:
              System.out.println("Invalid command");
            case 0:
              get.searchListings();
              break;
            case 1:
              System.out.println("\n1 - Buy cart items\n2 - Buy Specific Listing\n3 - Back\n");
              while(in.hasNextLine()){
                String choice = in.nextLine();
                System.out.println(choice);
                if(choice.equals("1")){
                  buy.buyCart();
                  break;
                }else if(choice.equals("2")){
                  buy.buyListings(false, " ");
                  break;
                }else if(choice.equals("3")){
                  break;
                }else{
                  System.out.println("Enter valid option");
                }
              }
              break;
            case 2:
              System.out.println("What would you like to view:\n1 - All Listings\n2 - My Listings\n3 - Cart Items\n4 - Credits\n\nEnter a number");
              while(in.hasNextLine()){
                String choice = in.nextLine();        
                  try{
                    if(Integer.parseInt(choice) > 5||Integer.parseInt(choice) < 0){
                      System.out.println("Enter a valid option");
                    }else{
                      get.viewListings(Integer.parseInt(choice));
                      break;
                    }    
                }catch(NumberFormatException err){System.out.println("Enter number value");}

                }
              break;
            case 3:
              post.post();
              break;
            case 4:
              post.delete();
              break;
            case 5:
              help();
              break;
            case 6:
              userActive = false;
              login();
              break;
          }

        }
          
        }catch(Exception e){System.out.println("Invalid command");}
        
      }
    }
  }
  static void help() {
    System.out.println("\n/search -- search listings");
    System.out.println("/buy -- buy cart/current items");
    System.out.println("/view -- view listings");
    System.out.println("/post -- post listings");
    System.out.println("/delete -- delete listings");
    System.out.println("/logout");
  }
}
