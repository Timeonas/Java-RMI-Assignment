import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import exceptions.InvalidCredentialsException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.FileWriter;
import exceptions.InvalidSessionException;


import exceptions.InvalidFormException;
import java.io.IOException;
import java.util.HashMap;

//Class implements of the ApplicationHandler interface
public class ApplicationHandlerImpl extends UnicastRemoteObject implements ApplicationHandler {

    //Valid username and password hardcoded into the server
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password123";

    //Map of active sessions, maps session ID to the time it was created
    private final Map<Long, Long> activeSessions = new HashMap<>();
    //AtomicLong to generate unique session IDs, starting from 1
    private final AtomicLong sessionIdGenerator = new AtomicLong(0);
    //Session timeout in milliseconds (5 minutes)
    private static final long sessionTimeout = 5 * 60 * 1000;

    public ApplicationHandlerImpl() throws RemoteException {
        super();
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidCredentialsException {
        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            //Generate a new session ID and assign it to sessionId
            long sessionId = sessionIdGenerator.incrementAndGet();
            //Add the session ID to the activeSessions map with the current time
            activeSessions.put(sessionId, System.currentTimeMillis());
            return sessionId;
        }
        throw new InvalidCredentialsException("Invalid username or password");
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionId) throws RemoteException, InvalidSessionException {
        //Validate the session before proceeding
        validateSession(sessionId);
        return new ApplicationFormV1();
    }
    @Override
    public void submitApplicationForm(long sessionId, ApplicationForm form)
            throws RemoteException, InvalidSessionException, InvalidFormException {
        validateSession(sessionId);

        if (form == null) {
            throw new InvalidFormException("Form cannot be null");
        }

        try {
            String fileName = form.getFirstName() + "_" + form.getLastName() + "_application.txt";
            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");

            FileWriter writer = new FileWriter(fileName);
            writer.write(form.getFormContents());
            writer.close();

        } catch (IOException e) {
            throw new RemoteException("Failed to save application form", e);
        }
    }

    private void validateSession(long sessionId) throws InvalidSessionException
     {
        Long sessionCreationTime = activeSessions.get(sessionId);
        if (sessionCreationTime == null) {
            throw new InvalidSessionException("Invalid session ID");
        }

        //Check if session has expired
        if (System.currentTimeMillis() - sessionCreationTime > sessionTimeout) {
            activeSessions.remove(sessionId);
            throw new InvalidSessionException("Session has expired");
        }
    }
}