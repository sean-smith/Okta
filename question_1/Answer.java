/*
 * Sean Smith 2015 
 * All rights reserved
 */

/*
 * This is the file with your answer, do not rename or move it.
 * Write your code in it, and save it before submitting your answer.
 */

/*
* Write the code for your answer in this class.
*/
public class Answer
{
    /**
    * Determine if the provided string is a valid socket address.
    *
    * This method declaration must be kept unmodified.
    *
    * @param socketAddress A string with a socket address, eg, 
    *                '127.12.23.43:5000', to be checked for validity.
    * @return boolean indicating wether the provided string is a valid
    *                  socket address
    */
    public static boolean isValidSocketAddress(String socketAddress)
    {
         // Loop through the characters
        for (int i = 0; i < socketAddress.length(); i++){
         // Find the address of the first dot
         if (socketAddress.charAt(i) == '.'){
           if (!validateInt(socketAddress.substring(0,i))) {
             return false;
           } else {
             return isValidSocketAddress(socketAddress.substring(i+1));
           }
         } else if (socketAddress.charAt(i) == ':') {
          String port = socketAddress.substring(i+1);
          if (Integer.parseInt(port) <= 65535 && Integer.parseInt(port) >= 1){
            return true;
          } else {
            return false;
          }
         } else if (!(socketAddress.charAt(i) <= 57 && socketAddress.charAt(i) >= 48)) {
          return false;
         }
        }
        // cover the case when the String is empty
        return false;
    }

    public static boolean validateInt(String intString) {
    //This methods converts the string to it's integer representation and checks that it's within bounds
     int num = Integer.parseInt(intString);
     if (num >= 0 && num <= 255) {
      return true;
     } else {
      return false;
     }
    }

    /*
    * This tests your code with the examples given in the question,
    * and is provided only for your conveinience.
    */
    public static void main(String[] args)
    {
        String[] addresses = {"127.12.23.43:5000", "127.A:-12", "000000.000000.000000.0000:000000"};
        for (String socketAddress : addresses)
        {
            System.out.println(isValidSocketAddress(socketAddress));
        }
    }
}
