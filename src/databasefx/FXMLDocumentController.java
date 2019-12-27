package databasefx;

import animatefx.animation.FadeOut;
import animatefx.animation.FadeOutLeft;
import animatefx.animation.FadeOutRight;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

public class FXMLDocumentController implements Initializable 
{
    
    private ArrayList<Employee> employeeArrayList;
    private int index = -1;
    private boolean isCreatingNewEmp = false;
    
    ArrayList<JFXTextField> tfArray = new ArrayList<>();

    @FXML private JFXButton newBTN;
    @FXML private JFXButton prevBTN;
    @FXML private JFXButton nextBTN;
    @FXML private JFXTextField idTF;
    @FXML private JFXTextField firstNameTF;
    @FXML private JFXTextField middleNameTF;
    @FXML private JFXTextField lastNameTF;
    @FXML private JFXTextField emailTF;
    @FXML private JFXTextField phoneTF;
        
    boolean isNextPressed, isPrevPressed;


    
    @FXML 
    private void clickedNew(ActionEvent event) {
        System.out.println("CLICKED");
        newBTN.setText("LOL");
        isCreatingNewEmp = !isCreatingNewEmp;
        if (isCreatingNewEmp) {
            newBTN.setText("Save");
            clearFields();

        } else {
            newBTN.setText("New");
            Employee newObj = new Employee();
            fillFromGUI(newObj);
            DB.insertEmployee(newObj);
            employeeArrayList = DB.getEmployees();
            setFieldDetails();
        }
    
    }
    
    @FXML 
    private void clickedUpdate(ActionEvent event) {
        if (index < 0) {
            return;
        }

        Employee emp = employeeArrayList.get(index);
        fillFromGUI(emp);

        DB.updateEmployee(emp);
    }
    
    @FXML private void clickedDelete(ActionEvent event) 
    {
        //case 1 ==> No fields in the database.
        if (index < 0) {
            return;
        }
        int realID = employeeArrayList.get(index).getId();
        DB.deleteEmployee(realID);
        employeeArrayList = DB.getEmployees();
        if (index == 0 && employeeArrayList.size() > 0) {
            index = 0;
        } else {
            index--;
        }

        setFieldDetails();
        
    }
    
    @FXML private void clickedNext(ActionEvent event)
    {
        isNextPressed = true;
         if (index < employeeArrayList.size() - 1) {
             
             animateTextField(tfArray);

            index++;
        }
        setFieldDetails();
    }
    
    @FXML private void clickedPrevious(ActionEvent event)
    {
        isPrevPressed = true;
        if (index > 0) {
            animateTextField(tfArray);
            index--;
        }
        setFieldDetails();
    }
    
    @FXML private void clickedFirst(ActionEvent event)
    {
        if (employeeArrayList.size() > 0) {
            index = 0;
        } else {
            index = -1;
        }
        setFieldDetails();
        
    }
    
    @FXML private void clickedLast(ActionEvent event)
    {
        index = employeeArrayList.size() - 1;
        setFieldDetails();
    }
    
    private void animateTextField(ArrayList<JFXTextField> TFarray)
    {
        Iterator iter = TFarray.iterator();
        while(iter.hasNext())
        {
            if(isNextPressed)
                new FadeOutRight((Node) iter.next()).setCycleCount(1).setResetOnFinished(true).play();
           else
                new FadeOutLeft((Node) iter.next()).setCycleCount(1).setResetOnFinished(true).play();
        }
        isNextPressed = isPrevPressed = false;
    }

    private void clearFields() {
        idTF.setText("");
        firstNameTF.setText("");
        middleNameTF.setText("");
        lastNameTF.setText("");
        emailTF.setText("");
        phoneTF.setText("");
    }
    
    private void fillFromGUI(Employee emp) {
        emp.setFirstName(firstNameTF.getText());
        emp.setLastName(lastNameTF.getText());
        emp.setMiddleName(middleNameTF.getText());
        emp.setEmail(emailTF.getText());
        emp.setPhone(phoneTF.getText());
    }
    
        private void setFieldDetails() {
        if (index < 0) {
            clearFields();
        }
        
        idTF.setText(String.valueOf(employeeArrayList.get(index).getId()));
        firstNameTF.setText(employeeArrayList.get(index).getFirstName());
        middleNameTF.setText(employeeArrayList.get(index).getMiddleName());
        lastNameTF.setText(employeeArrayList.get(index).getLastName());
        emailTF.setText(employeeArrayList.get(index).getEmail());
        phoneTF.setText(employeeArrayList.get(index).getPhone());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        isNextPressed = isPrevPressed = false;
        
        employeeArrayList = DB.getEmployees();
        if (employeeArrayList.size() > 0) {
            index = 0;
        }
        setFieldDetails();
        
        
        //Filling the JFXTextField arrayList with all the TextField elements.
        tfArray.add(idTF);
        tfArray.add(firstNameTF);
        tfArray.add(middleNameTF);
        tfArray.add(lastNameTF);
        tfArray.add(emailTF);
        tfArray.add(phoneTF);
    }
    
        
        
    
}
