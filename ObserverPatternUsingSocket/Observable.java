
package ObserverPatternUsingSocket;

import java.io.IOException;
public interface Observable {
   void addObserver(Observer newObserver);
    void removeObserver(Observer observer);
    void notifyObserver(String message) throws IOException; 
}
