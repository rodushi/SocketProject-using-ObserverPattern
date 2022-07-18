package ObserverPatternUsingSocket;
import java.io.*;
import java.net.Socket;

public class Client {
    public Socket socket;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    public BufferedReader bufferedReader;
    String username;

    public void start() throws IOException {
        socket= new Socket("localhost",1234);
        dataInputStream= new DataInputStream(socket.getInputStream());
        dataOutputStream= new DataOutputStream(socket.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your username : ");
        username= bufferedReader.readLine();
        dataOutputStream.writeUTF(username);
        dataOutputStream.flush();
        run();
    }
    public void sendMessage() throws Exception{
        String message = bufferedReader.readLine();
        message = username +  ": "+message;
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }
    public void  recieveMessage() throws Exception{
        String message = dataInputStream.readUTF();
        System.out.println(message);
    }

    public void run(){
        new Thread(() ->{
            while (true){
                try {
                    sendMessage();
                }catch (Exception exception){
                    System.out.println("Error : "+exception.getMessage());
                    break;
                }
            }
        }).start();
        while (true){
            try {
                recieveMessage();
            }catch (Exception exception){
                System.out.println("Error : "+exception.getMessage());
                break;
            }
        }
    }

    public static void main(String[] args) {
        while (true){
            try {
                new Client().start();
            }catch (Exception exception){
                System.out.println("Error : "+exception.getMessage());
            }
        }
    }
}