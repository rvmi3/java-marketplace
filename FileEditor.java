import java.io.*;
import java.util.*;
public class FileEditor{
  private static String filePath;
  public FileEditor(String filePath){
    this.filePath = filePath;
  }
  public void addListing(String listingToAdd, int option){
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> temp = new ArrayList<String>();
    try{
      //2 - listings
      //3 - cart
      //4 - credits
      int lineNum = option;
      
      File file = new File(filePath);
      Scanner reader = new Scanner(file);
      while(reader.hasNextLine()){
        list.add(reader.nextLine());
      
      }
      String[] lineToBeEdited = list.get(lineNum).split(":");
      
      
      String strippedLine = lineToBeEdited[1].replace("]", "");
      strippedLine = strippedLine.replace("[", "");
      strippedLine = strippedLine.replace(",", "");
      strippedLine = strippedLine.trim();
      String[] listFromLine = strippedLine.split(" ");

      for(int i = 0; i < listFromLine.length; i++){
        temp.add(listFromLine[i]);
      }
      temp.add(listingToAdd);
      //the item/cart is added now we have to have to replace the entire line
      list.set(lineNum, lineToBeEdited[0] +":"+ temp);
      FileWriter writer = new FileWriter(file);
      for(String x: list){
        writer.write(x+"\n");
      }
      writer.close();
      
    }catch(IOException err){err.printStackTrace();}
  
  }
  public void delete(String toDelete){
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> temp = new ArrayList<String>();

    try{
      File file = new File(filePath);
      Scanner reader = new Scanner(file);
      while(reader.hasNextLine()){
        list.add(reader.nextLine());
      }
      for(String item:list){
        if(!(item.contains(toDelete))){
          temp.add(item);
        }
      }
      FileWriter writer = new FileWriter(filePath);
      for(String x: list){
        writer.write(x+"\n");
      }
      writer.close();
      
    }catch(IOException err){err.printStackTrace();}
  
  }
  public void deleteListing(String listingToDelete, int option){
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> temp = new ArrayList<String>();
    try{
      //2 - listings
      //3 - cart
      //4 - credits
      //5 - purchase history
      int lineNum = option;
      File file = new File(filePath);
      Scanner reader = new Scanner(file);
      while(reader.hasNextLine()){
        list.add(reader.nextLine());
      }
      String[] lineToBeEdited = list.get(lineNum).split(":");
      
      
      String strippedLine = lineToBeEdited[1].replace("]", "");
      strippedLine = strippedLine.replace("[", "");
      strippedLine = strippedLine.replace(",", "");
      strippedLine = strippedLine.trim();
      String[] listFromLine = strippedLine.split(" ");

      for(int i = 0; i < listFromLine.length; i++){
        if(!(listFromLine[i].equals(listingToDelete))){
          temp.add(listFromLine[i]);
        }
      }
      //the item/cart is deleted now we have to have to replace the entire line
      list.set(lineNum, lineToBeEdited[0] +":"+ temp);
      FileWriter writer = new FileWriter(file);
      for(String x: list){
        writer.write(x+"\n");
      }
      writer.close();
      
    }catch(IOException err){err.printStackTrace();}
  }
  public ArrayList<String> returnInfo(int lineNum){
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> temp = new ArrayList<String>();
    try{
      //2 - listings
      //3 - cart
      //4 - credits
      //5 - purchase history

      
      File file = new File(filePath);
      Scanner reader = new Scanner(file);
      while(reader.hasNextLine()){
        list.add(reader.nextLine());
      
      }
      String[] lineToBeEdited = list.get(lineNum).split(":");
      String strippedLine = lineToBeEdited[1].replace("]", "");
      strippedLine = strippedLine.replace("[", "");
      strippedLine = strippedLine.replace(",", "");
      strippedLine = strippedLine.trim();
      String[] listFromLine = strippedLine.split(" ");
      for(int i = 0; i < listFromLine.length; i++){
        temp.add(listFromLine[i]);
      }
      
      //the item/cart is added now we have to have to replace the entire line
      
      
    }catch(IOException err){err.printStackTrace();}
    return temp;
  }
  public void updateCredit(String newCredits){
    deleteListing(returnInfo(4).get(0), 4);
    addListing(newCredits, 4);
  }
  public void deleteAll(int lineNum){
    ArrayList<String> line = returnInfo(lineNum);
    for(String item:line){
      deleteListing(item, lineNum);
    }  
  }

}