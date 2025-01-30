package src;
import exceptions.InvalidCredentialsException;
import exceptions.InvalidFormException;
import exceptions.InvalidSessionException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ApplicationHandler extends Remote {
    /**
     * Authenticates user and returns a session ID
     * @param username The user's username
     * @param password The user's password
     * @return Session ID as a long value
     * @throws RemoteException If RMI-related issues occur
     * @throws InvalidCredentialsException If login credentials are invalid
     */
    long login(String username, String password)
            throws RemoteException, InvalidCredentialsException;

    /**
     * Downloads an application form for a valid session
     * @param sessionId Session ID from successful login
     * @return ApplicationForm object
     * @throws RemoteException If RMI-related issues occur
     * @throws InvalidSessionException If session ID is invalid or expired
     */
    ApplicationForm downloadApplicationForm(long sessionId)
            throws RemoteException, InvalidSessionException;

    /**
     * Submits a completed application form
     * @param sessionId Session ID from successful login
     * @param form Completed application form
     * @throws RemoteException If RMI-related issues occur
     * @throws InvalidSessionException If session ID is invalid or expired
     * @throws InvalidFormException If form data is incomplete or invalid
     */
    void submitApplicationForm(long sessionId, ApplicationForm form)
            throws RemoteException, InvalidSessionException, InvalidFormException;
}

