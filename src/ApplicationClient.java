import exceptions.InvalidCredentialsException;
import exceptions.InvalidSessionException;
import exceptions.InvalidFormException;
import exceptions.EmptyAnswerException;
import exceptions.InvalidQuestionNumber;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;

public class ApplicationClient {
    public static void main(String[] args) {
        try {
            int port = 20345;
            //Get reference to RMI registry
            Registry registry = LocateRegistry.getRegistry(port);
            System.out.println("Connected to RMI registry");

            String name = "ApplicationHandler";
            //Look up the remote object
            ApplicationHandler handler = (ApplicationHandler) registry.lookup(name);
            System.out.println("Found remote ApplicationHandler object");
            
            //Login
            long sessionId;
            try 
            {
                //Credentials for server login
                String username = "admin";
                String password = "password123";
                sessionId = handler.login(username, password);
                System.out.println("Login successful. Session ID: " + sessionId);
            } catch (InvalidCredentialsException e) {
                System.err.println("Login failed: " + e.getMessage());
                return;
            }
            //Download application form
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
            
            //Complete the form
            try {
                completeApplicationForm(form);
                System.out.println("Form completed successfully");
            } catch (Exception e) {
                System.err.println("Error completing form: " + e.getMessage());
                return;
            }
            
            //Submit the competed form
            try {
                
                handler.submitApplicationForm(sessionId, form);
                System.out.println("Application form submitted successfully");
            } catch (InvalidSessionException | InvalidFormException e) {
                System.err.println("Failed to submit form: " + e.getMessage());


            }
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
    private static void completeApplicationForm(ApplicationForm form) 
            throws RemoteException, InvalidQuestionNumber, EmptyAnswerException {
        //Sample answer
        String[] sampleAnswers = {
            "Alasdair Daniel",
            "123 UoG Road",
            "aDaniel123@email.com",
            "0894594324",
            "My favourite module is Distributed Systems"
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