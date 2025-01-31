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


public class ApplicationHandlerImpl extends UnicastRemoteObject implements ApplicationHandler {
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password123";

    private final Map<Long, Long> activeSessions = new HashMap<>();
    private final AtomicLong sessionIdGenerator = new AtomicLong(0);
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; //30 minutes in milliseconds

    public ApplicationHandlerImpl() throws RemoteException {
        super();
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidCredentialsException {
        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            long sessionId = sessionIdGenerator.incrementAndGet();
            activeSessions.put(sessionId, System.currentTimeMillis());
            return sessionId;
        }
        throw new InvalidCredentialsException("Invalid username or password");
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionId) throws RemoteException, InvalidSessionException {
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
            //Sanitize filename to remove invalid characters
            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");

            FileWriter writer = new FileWriter(fileName);
            writer.write(form.toString());
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
        if (System.currentTimeMillis() - sessionCreationTime > SESSION_TIMEOUT) {
            activeSessions.remove(sessionId);
            throw new InvalidSessionException("Session has expired");
        }

        //Update session timestmp
        activeSessions.put(sessionId, System.currentTimeMillis());
    }
}