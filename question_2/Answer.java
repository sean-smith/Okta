import java.io.File;
import java.util.Scanner;
import java.util.Hashtable;
/*
 * Sean Smith 2015 
 * All rights reserved
 */

/*
 * This is the file with your answer, do not rename or move it.
 * Write your code in it, and save it before submitting your answer.
 */

/**
 * This class is a container for your answer,
 * and its declaration must be kept unmodified.
 */
public class Answer {
    /**
     * Returns an array of IP addresses that are deemed suspicious.
     *
     * This method declaration must be kept unmodified.
     *
     * @param logPath The full path of a log file. * This is the file with your answer, do not rename or move it.
     * @return An array of IP addresses.
     */
    public static String[] findSuspiciousIps(String logPath) {
      /* In this function, I parse the log file then add ip's to a hash table if they 
       * have the right path and error code. 
       * I then lookup ip's that have been added to the hash table and I run compare
       * which tells me if the new ip hit within a second. 
       * If it did I check to see if the counter hit three and if it did.
       */
      String[] ips = new String[100];
      int index = 0;
      Hashtable<String, Time> hash = new Hashtable<String, Time>();

      try {
        File file = new File(logPath);
        Scanner s = new Scanner(file);
        
        while (s.hasNextLine()) {
          String line = s.nextLine();
          //System.out.println(line);
          String[] params = line.split(" ");
          String path = params[params.length - 4];
          if (path.equals("/account/transfer")) {
            String code = params[params.length - 2];
            if (Integer.parseInt(code) >= 400 && Integer.parseInt(code) <= 499) {
              // Need to save the ip address and the time here
              String ip = params[0];
              String time = params[3];
              //System.out.println(line);
              if (hash.get(ip) != null){
                if(hash.get(ip).compare(time)){
                  if (hash.get(ip).getCount() == 2){
                    //System.out.println(line);
                    ips[index] = ip;
                    index++;
                  }
                  hash.get(ip).incCount();
                 } else {
                   Time t = new Time(time);
                   hash.put(ip, t);
                 }    
              } else {
                Time t = new Time(time);
                hash.put(ip, t);
              }
            }
          }
          
        }
        s.close();
      } catch (Exception e){
        e.printStackTrace();
      }
      
      // return the array resized to the correct size
      return resize(ips);
    }
    
    public static String[] resize(String[] a) {
      int size = getSize(a);
      String[] array = new String[size];
      return copy(a, array);
    }
    
    public static String[] copy(String[] a, String[] b) {
      for (int i = 0; a[i] != null && i < a.length; i++) {
       b[i] = a[i]; 
      }
      return b;
    }
    
    public static int getSize(String[] a) {
      int i = 0;
      while(a[i] != null) {
       i++;
      }
      return i;
    }
    

    /**
     * Tests the method abbreviate with the examples given in the question. 
     * This test code is provided only for your convenience.
     */
    public static void main(String[] args) {
        System.out.println("example.log:");
        for (String ip : findSuspiciousIps("example.log"))
        {

            System.out.println(ip);
        }
        System.out.println("bank_logs.log:");
        for (String ip : findSuspiciousIps("bank_logs.log"))
        {
            System.out.println(ip);
        }
    }
}

class Time {
  int seconds;
  String rest;
  int count;
  
  Time(String time) {
    seconds = parseSeconds(time);
    rest = parseRest(time);
    count = 1;
  }

  int parseSeconds(String t) {
    return Integer.parseInt(t.substring(t.length() - 2, t.length()));
  }
  
  int getCount(){
   return count; 
  }
  
  void incCount(){
   count++; 
  }
  
  String parseRest(String t) {
    return t.substring(0, t.length() - 2);
  }
  
  boolean compare(String t2){
    if (rest.equals(parseRest(t2)) && (parseSeconds(t2) - seconds <= 1)) {
      return true;
    } else {
      return false;
    }
  }
}