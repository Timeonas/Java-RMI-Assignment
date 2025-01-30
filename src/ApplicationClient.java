import exceptions.InvalidCredentialsException;
import exceptions.InvalidSessionException;
import exceptions.InvalidFormException;
import exceptions.EmptyAnswerException;
import exceptions.InvalidQuestionNumber;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;

public class ApplicationClient {
    private static final String HOST = "localhost";
    private static final int PORT = 1099;
    private static final String REMOTE_OBJECT_NAME = "ApplicationHandler";
    
    // Using same credentials as defined in server
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";
    
    public static void main(String[] args) {
        try {
            // Get reference to RMI registry
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            System.out.println("Connected to RMI registry");
            
            //Look up the remote object
            ApplicationHandler handler = (ApplicationHandler) registry.lookup(REMOTE_OBJECT_NAME);
            System.out.println("Found remote ApplicationHandler object");
            
            // Step 1: Login
            long sessionId;
            try 
            {
                sessionId = handler.login(USERNAME, PASSWORD);
                System.out.println("Login successful. Session ID: " + sessionId);
            } catch (InvalidCredentialsException e) {
                System.err.println("Login failed: " + e.getMessage());
                return;
            }
            // Step 2: Download application form
            ApplicationForm form;
            try {

                form = handler.downloadApplicationForm(sessionId);
                System.out.println("Application form downloaded successfully");
                System.out.println(form.getFormInfo());
            } 
            catch (InvalidSessionException e) {
                System.err.println("Failed to download form: " + e.getMessage());
                return;
            }
            
            // Step 3: Complete the form
            try {
                completeApplicationForm(form);
                System.out.println("Form completed successfully");
            } catch (Exception e) {
                System.err.println("Error completing form: " + e.getMessage());
                return;
            }
            
            // Step 4:Submit the competed form
            try {
                
                handler.submitApplicationForm(sessionId, form);
                System.out.println("Application form submitted successfully");
            } catch (InvalidSessionException | InvalidFormException e) {
                System.err.println("Failed to submit form: " + e.getMessage());


            }
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
    private static void completeApplicationForm(ApplicationForm form) 
            throws RemoteException, InvalidQuestionNumber, EmptyAnswerException {
        // Sample answers for testing
        String[] sampleAnswers = {
            "John Doe",                 
            "123 University Avenue",     
            "john.doe@email.com",       
            "555-0123",                 
            "I am a dedicated student with excellent academic records..."
        };
        
        int totalQuestions = form.getTotalQuestions();
        System.out.println("\nCompleting form with " + totalQuestions + " questions:");
        
        for (int i = 0; i < totalQuestions; i++) {
            String question = form.getQuestion(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + question);
            form.answerQuestion(i, sampleAnswers[i]);
            System.out.println("Answered: " + sampleAnswers[i]);
        }
    }
}