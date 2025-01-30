
import java.rmi.RemoteException;
import exceptions.EmptyAnswerException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


import java.util.Map;

import exceptions.InvalidQuestionNumber;
public class ApplicationFormV1 extends UnicastRemoteObject implements ApplicationForm {
    private static final long serialVersionUID = 1L;
    private final String[] questions = {
        "Please enter your full name:",
        "Please enter your address:",
        "Please enter your email:",
        "Please enter your contact number:",
        "Please provide a personal statement (including qualifications and additional details):"
    };
    
    private final Map<Integer, String> answers;
    
    public ApplicationFormV1() throws RemoteException {
        super();
        answers = new HashMap<>();
    }
    
    @Override
    public String getFormInfo() throws RemoteException {
        return "University Course Application Form V1 - Basic Information Collection";
    }
    
    @Override
    public int getTotalQuestions() 
    throws RemoteException {
        return questions.length;
    }
    
    @Override
    public String getQuestion(int questionNumber) throws RemoteException {
        if (questionNumber < 0 || questionNumber >= questions.length) {
            throw new RemoteException("Invalid question number: " + questionNumber);
        }
        return questions[questionNumber];
    }
    
    @Override
    public void answerQuestion(int questionNumber, String answer) throws RemoteException, InvalidQuestionNumber, EmptyAnswerException {
        if (questionNumber < 0 || questionNumber >= questions.length) {
            throw new InvalidQuestionNumber("Invalid question number: " + questionNumber);
        }
        if (answer == null || answer.trim().isEmpty()) {
            throw new EmptyAnswerException("Answer cannot be empty");
        }
        answers.put(questionNumber, answer.trim());
    }
    @Override
    public String getFirstName() throws RemoteException {
        String fullName = answers.get(0);  // First question is full name
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new RemoteException("Name has not been provided");
        }
        String[] nameParts = fullName.trim().split("\\s+");
        return nameParts[0];  
    }

    @Override
    public String getLastName() throws RemoteException {
        String fullName = answers.get(0); 
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new RemoteException("Name has not beenprovided");
        }
        String[] nameParts = fullName.trim().split("\\s+");
        if (nameParts.length < 2) {
            throw new RemoteException("Full name must include last name");
        }
        return nameParts[nameParts.length - 1]; 
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Application Form Submission\n");
        sb.append("-------------------------\n");
        for (int i = 0; i < questions.length; i++) {
            sb.append(questions[i]).append("\n");
            sb.append("Answer: ").append(answers.getOrDefault(i, "Not answered")).append("\n\n");
        }
        return sb.toString();
    }
}