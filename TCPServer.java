package cs250.hw3;
import java.io.*;    
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class TCPServer {
    static DataInputStream[] din = new DataInputStream[2];
    static DataOutputStream[] dout = new DataOutputStream[2];
    //static Scanner userInput = new Scanner(System.in);
    static Socket[] clientSocket = new Socket[2];
    static ServerSocket serverSocket;

    public static int receiveNum(int index){
        try {
            int response = din[index].readInt();
            return response;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return -1;
    }

    public static void sendNumber(int numToSend, int index){
        try {
            dout[index].writeInt(numToSend);
            dout[index].flush(); // By flushing the stream, it means to clear the stream of any element that may be or maybe not inside the stream
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static void sendNumbers(int num1, int num2, int index){
        try {
            dout[index].writeInt(num1);
            dout[index].writeInt(num2);
            dout[index].flush(); // By flushing the stream, it means to clear the stream of any element that may be or maybe not inside the stream
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static void cleanUp(){
        try {
            serverSocket.close();
            for (int i = 0; i < 2; i++){
                clientSocket[i].close();
                dout[i].close();
                din[i].close();
            }
            System.exit(0);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public static void main(String[] args){
        final int port =  Integer.parseInt(args[0]);
        final int seed =  Integer.parseInt(args[1]);
        final int numMessages =  Integer.parseInt(args[2]);

        try{
            System.out.println("IP Address: " + InetAddress.getLocalHost() + "\nPort Number " + port); 

            Random rand = new Random(seed);
            serverSocket = new ServerSocket(port);
            System.out.println("waiting for client...");
            clientSocket[0] = serverSocket.accept(); // Blocking call --> waits here until a request comes in from a client
            clientSocket[1] = serverSocket.accept(); // Blocking call --> waits here until a request comes in from a client
            System.out.println("Client Connected!");
            dout[0] = new DataOutputStream(clientSocket[0].getOutputStream()); // Instantiates dout so we can then use it to send data to the client
            din[0] = new DataInputStream(clientSocket[0].getInputStream()); // Instantiates din so we can then use it to receive data from the client
            dout[1] = new DataOutputStream(clientSocket[1].getOutputStream()); // Instantiates dout so we can then use it to send data to the client
            din[1] = new DataInputStream(clientSocket[1].getInputStream()); // Instantiates din so we can then use it to receive data from the client
            long clientASum = 0;
            long clientBSum = 0;
            long clientANum = 0;
            long clientBNum = 0;
            int messFromClientA = 0;
            int messFromClientB = 0;
            String clientA = clientSocket[0].getInetAddress().getHostName();
            String clientB = clientSocket[1].getInetAddress().getHostName();


            System.out.println("Sending config to clients...");
            sendNumber(numMessages, 0);
            int randNum = rand.nextInt();
            sendNumber(randNum, 0);

            int randNum2 = rand.nextInt();
            sendNumber(numMessages, 1);
            sendNumber(randNum2, 1);

            System.out.println(clientA + " " + randNum);
            System.out.println(clientB + " " + randNum2);
            System.out.println("Finished sending config to clients.");

            System.out.println("Starting to listen for client messages...");

            for (int i = 0; i < numMessages; i++){
                clientANum = receiveNum(0);
                clientASum += clientANum;
                messFromClientA++;
                sendNumber((int) (clientANum), 1);
                clientBNum = receiveNum(1);
                clientBSum += clientBNum;
                messFromClientB++;
                sendNumber((int) clientBNum, 0);
            }

            System.out.println("Finished listening for client messages.\n" + clientA + "\n    Messages received: " + messFromClientA + "\n    Sum received: " + clientASum + "\n" + clientB + "\n    Messages received: " + messFromClientB + "\n    Sum received: " + clientBSum);

            /* while(true){
                System.out.print("Server Message: ");
                int message = Integer.parseInt(userInput.nextLine());
                
                if(message == EXIT_NUM){
                    System.out.println("Ending Communication");
                    cleanUp();
                }

                sendNumber(message);
                int response = receiveNum();
                System.out.println("Client Response: " + response);
            } */
            //System.out.println(clientSocket[0].getInetAddress().getHostName()); //prints url of client


            cleanUp();
            
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        catch(IllegalArgumentException e){
            //System.out.println("IP Address: " + InetAddress.getLocalHost() + "\nPort Number " + port + "\nAddress already in use (Bind failed)");
            System.err.println(e.getMessage());
        }
    }
}
