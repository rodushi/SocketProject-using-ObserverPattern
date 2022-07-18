
package ObserverPatternUsingSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandeller extends Thread implements Observer {
    public String username;
    public Socket socket;
    public DataOutputStream dataOutputStream;
    public DataInputStream dataInputStream;
    public Observable server;
    public String message;

    public ClientHandler(Socket socket, Observable server) throws Exception{
        this.server=server;
        this.socket=socket;
        dataInputStream=new DataInputStream(socket.getInputStream());
        dataOutputStream= new DataOutputStream(socket.getOutputStream());
    }

    public void sendMessage(String message) throws Exception{
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }
    public void recieveMEssage() throws Exception{
        String recievedMessage = dataInputStream.readUTF();
        String[] args = recievedMessage.split(" ",3);
        server.notifyObserver(recievedMessage);
    }

    @Override
    public void run() {
        try{
            username= dataInputStream.readUTF();
            System.out.println(username+ " has joined the server!!!");
            server.notifyObserver(username+ " has joined the server!!!");
            server.addObserver(this);

            new Thread(()->{
                while(true){
                    try {
                        recieveMEssage();
                    }catch (Exception exception){
                        try {
                            server.removeObserver(this);
                            System.out.println(username+ " has left the server!!!");
                            server.notifyObserver(username+ " has left the server!!!");
                            break;
                        }catch (IOException ioException){
                            System.out.println("Error : "+ioException.getMessage());
                        }
                    }
                }
            }).start();
        }catch (IOException ioException){
            System.out.println("Error : "+ioException.getMessage());
        }
    }

    @Override
    public void updateMessage(String message) throws Exception {
        sendMessage(message);
    }
}
