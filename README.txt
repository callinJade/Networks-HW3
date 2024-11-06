java cs250.hw3.TCPServer <port-number> <seed> <number-Of-Messages>
java cs250.hw3.TCPClient <server-host> <server-port>

Compile: javac cs250/hw3/TCPServer.java
Compile: javac cs250/hw3/TCPClient.java
Run: java cs250.hw3.TCPServer 1234 42 4
Run: java cs250.hw3.TCPClient eldora 1234

Test Provided: 
    Client: java -cp Networking.jar cs250.hw3.SubmissionOneClient <server host> <server port>
    Server: java -cp Networking.jar cs250.hw3.SubmissionTwoServer <server port> <seed> <num messages>

ssh wahoo.cs.colostate.edu
ssh dhaka.cs.colostate.edu
cd Documents 
    Client: java -cp Networking.jar cs250.hw3.SubmissionOneClient eldora 1234
    Server: java -cp Networking.jar cs250.hw3.SubmissionTwoServer 1234 42 4
at end: logout

Zip: zip -r Jade-Collins-HW3.zip cs250

This program has two classes: TCPClient and TCPServer

TCPServer waits for two clients to connect, then sends them each a random number and a number detailing
how many messages should be sent back while documenting its process in outputs to the console. The server
then sends the messages received from client A to client B and vice versa while keeping track of the sum
of these numbers as well as how many messages it has received from each client. It prints this information
out to the console after.

TCPClient connects to the server and then receives the random number and the number of messages to send 
back. After receiving this number, the client uses it to bound a random number generator. It then uses 
this to generate random number to send back to the server. It sends the same number of messages as the 
number it received to send. It also keeps track of the sum of the random numbers it is sending and the 
number of messages it has sent thus far. It also documents its process in the outputs to the console.
The client then receives the numbers from the other client through the server. It keeps track of the sum
of these messages and the number of messages received. It then prints this to the console after.
