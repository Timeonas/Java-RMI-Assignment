import exceptions.InvalidCredentialsException;
import exceptions.InvalidFormException;
import java.rmi.Remote;
import exceptions.InvalidSessionException;

import java.rmi.RemoteException;

public interface ApplicationHandler extends Remote {
    
    //Remote method for user login, based on username and password
    long login(String username, String password) throws RemoteException, InvalidCredentialsException;

    //Downloads an application form for a valid session
    ApplicationForm downloadApplicationForm(long sessionId) throws RemoteException, InvalidSessionException;

 
    //Submitting a completed Application form
    void submitApplicationForm(long sessionId, ApplicationForm form) throws RemoteException, InvalidSessionException, InvalidFormException;
}

