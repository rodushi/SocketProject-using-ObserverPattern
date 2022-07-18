
package ObserverPatternUsingSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Observable{
    public Socket socket;
    ArrayList<Observer> observers = new ArrayList<>();

    public static void main(String[] args) {
        while (true){
            try{
                System.out.println("Server has started");
                new Server().start();
            } catch (Exception exception){
                System.out.println("There was an error : "+exception.getMessage());
            }
        }
    }

    private void start() throws IOException {
        ServerSocket serverSocket= new ServerSocket(1234);
        while(true){
            try{
                socket = serverSocket.accept();
                ClientHandler  newClient = null;
                newClient = new ClientHandler(socket,this);
                newClient.start();
            }catch (Exception exception){
                System.out.println("Error : "+ exception.getMessage());
            }
        }
    }

    @Override
    public void addObserver(Observer newObserver) {
        observers.add(newObserver);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(String message) throws IOException {
        for (Observer observer : observers){
            try{
                observer.updateMessage(message);
            }catch (Exception exception){
                System.out.println("Error : "+exception.getMessage());
            }
        }
    }
}
