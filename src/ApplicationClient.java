package src;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class ApplicationClient{

    private static final String localhost = "localhost";
    private static final int port = 1099;
    private static final String REMOTE_OBJECT_NAME = "ApplicatioHandler";

    private static String username = "testUsername";
    private static String password = "testPassword";
    
   public static void main(String[] args){

        try{
            Registry registry = LocateRegistry.getRegistry(localhost,port);

            ApplicationHandler handler = (ApplicationHandler)registry.lookup(REMOTE_OBJECT_NAME);

            System.out.print("Connected to server sucessfully!");

            long sessionId = handler.login(username, password);
            System.out.println("Login successful. Session ID: " + sessionId);


            ApplicationForm form = handler.downloadApplicationForm(sessionId);

        }
        catch(Exception e){
            System.out.print(e.getMessage());

        }
   }
}