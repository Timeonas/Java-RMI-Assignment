package src;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


public class ApplicationFormV1 extends UnicastRemoteObject implements ApplicationForm {
    // Store questions and answers
    private final String[] questions;    // Array of 5 required questions
    private final Map<Integer, String> answers;   // Store answers
    
    // Constructor
    public ApplicationFormV1() throws RemoteException {
        // Initialize answers map
    }
    
    // Interface methods
    @Override
    public String getFormInfo() throws RemoteException {
        // Return form description
    }
    
    @Override
    public int getTotalQuestions() throws RemoteException {
        // Return questions.length
    }
    
    @Override
    public String getQuestion(int questionNumber) throws RemoteException {
        // Return specific question
    }
    
    @Override
    public void answerQuestion(int questionNumber, String answer) throws RemoteException {
        // Store answer for question
    }
    
    @Override 
    public String toString() {
        // Format all questions and answers for saving to file
    }
}