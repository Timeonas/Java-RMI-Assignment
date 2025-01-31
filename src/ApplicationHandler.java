import exceptions.InvalidCredentialsException;
import exceptions.InvalidFormException;
import java.rmi.Remote;
import exceptions.InvalidSessionException;

import java.rmi.RemoteException;

public interface ApplicationHandler extends Remote {
    
    //Authenticates user and returns a session ID
    long login(String username, String password) throws RemoteException, InvalidCredentialsException;

     //Downloads an application form for a valid session
    ApplicationForm downloadApplicationForm(long sessionId) throws RemoteException, InvalidSessionException;

 
    //Submits a completed application form
    void submitApplicationForm(long sessionId, ApplicationForm form) throws RemoteException, InvalidSessionException, InvalidFormException;
}

