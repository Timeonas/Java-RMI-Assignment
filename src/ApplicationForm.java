
import java.rmi.Remote;
import java.rmi.RemoteException;

import exceptions.EmptyAnswerException;
import exceptions.InvalidQuestionNumber;

public interface ApplicationForm extends Remote {
 
 
    //Retrieve general information about the form
    String getFormInfo() throws RemoteException;
    

    //Retrieve total number of questions to be answered
    int getTotalQuestions() throws RemoteException;
    
    //Method to retrieve question based on question number
    String getQuestion(int questionNumber) throws RemoteException;
    
    //Method to answer question based on question number
    void answerQuestion(int questionNumber, String answer) throws RemoteException, InvalidQuestionNumber, EmptyAnswerException;

    String getLastName() throws RemoteException;
    String getFirstName() throws RemoteException;

    String getFormContents() throws RemoteException;
}