package databasefx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class FXMLDocumentController implements Initializable 
{
    
    private ArrayList<Employee> employeeArrayList;
    private int index = -1;
    private boolean isCreatingNewEmp = false;

    @FXML private JFXButton newBTN;
    @FXML private JFXTextField idTF;
    @FXML private JFXTextField firstNameTF;
    @FXML private JFXTextField middleNameTF;
    @FXML private JFXTextField lastNameTF;
    @FXML private JFXTextField emailTF;
    @FXML private JFXTextField phoneTF;


    
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
         if (index < employeeArrayList.size() - 1) {
            index++;
        }
        setFieldDetails();
    }
    
    @FXML private void clickedPrevious(ActionEvent event)
    {
        if (index > 0) {
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
        employeeArrayList = DB.getEmployees();
        if (employeeArrayList.size() > 0) {
            index = 0;
        }
        setFieldDetails();
        

    }
        
        
    
}
