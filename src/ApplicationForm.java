
import java.rmi.Remote;
import java.rmi.RemoteException;

import exceptions.EmptyAnswerException;
import exceptions.InvalidQuestionNumber;

public interface ApplicationForm extends Remote {
 
 
    //return String containing general form information
  
    String getFormInfo() throws RemoteException;
    

     //return integer representing total number of questions

    int getTotalQuestions() throws RemoteException;
    
    //return String containing the question at the specified index
    String getQuestion(int questionNumber) throws RemoteException;
    
    //answer specific quesiton in form
    void answerQuestion(int questionNumber, String answer) throws RemoteException, InvalidQuestionNumber, EmptyAnswerException;

    String getLastName() throws RemoteException;
    String getFirstName() throws RemoteException;
}