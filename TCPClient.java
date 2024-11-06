package cs250.hw3;
import java.io.*;    
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class TCPClient {
    static DataInputStream din;
    static DataOutputStream dout;
    //static Scanner userInput = new Scanner(System.in);
    static Socket clientSocket;

    public static int receiveNum(){
        try {
            int response = din.readInt(); // Reads an int from the input stream 
            return response;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1; // if an incorrect value is read, the EXIT_NUM will be returned
    }

    public static void sendNumber(int numToSend){
        try {
            dout.writeInt(numToSend); // Writes an int to the output stream
            dout.flush(); // By flushing the stream, it means to clear the stream of any element that may be or maybe not inside the stream
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    
    // Below method cleans up all of the connections by closing them and then exiting. 
    // This prevents a lot of problems, so its good practice to always make sure the connections close. 

    public static void cleanUp(){
        try {
            clientSocket.close();
            dout.close();
            din.close();

            System.exit(0);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        final int port =  Integer.parseInt(args[1]);
        final String server_ip = args[0]; 

        try{

            // Initialize Necessary Objects
            clientSocket = new Socket(server_ip, port); // Establishes a connection to the server
            dout = new DataOutputStream(clientSocket.getOutputStream()); // Instantiates out so we can then use it to send data to the client
            din = new DataInputStream(clientSocket.getInputStream()); // Instantiates in so we can then use it to receive data from the client
            long senderSum = 0;
            int randNum = 0;
            int numOfSentMessages = 0;
            long receiverSum = 0;
            int numOfReceivedMessages = 0;

             Thread.sleep(10000);
             //the client needs to expect a number immediately from the server once two clients have joined
             int numMessages = receiveNum(); //---> blocking call until server sends a value to client
             int seed = receiveNum();
             Random rand = new Random(seed);

             System.out.println("Received config\nnumber of messages = " + numMessages + "\nseed = " + seed);
             
             System.out.println("Starting to send messages to server...");
             for (int i = 0; i < numMessages; i++){
                randNum = rand.nextInt();
                senderSum += randNum;
                sendNumber(randNum);
                numOfSentMessages++;
             }

             System.out.println("Finished sending messages to server.\nTotal messages sent: " + numOfSentMessages + "\nSum of messages sent: " + senderSum);
            
             System.out.println("Starting to listen for messages from the server...");

             for (int i = 0; i < numOfSentMessages; i++){
                receiverSum += receiveNum();
                numOfReceivedMessages += 1;
             }

             System.out.println("Finished listening for messages from server.\nTotal messages received: " + numOfReceivedMessages + "\nSum of messages received: " + receiverSum);

            cleanUp();
            
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        catch(InterruptedException e){
            System.err.println(e.getMessage());
        }
    }
}