package application;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PatientSettingViewController {
	@FXML private TextField firstNameTF;
	@FXML private TextField lastNameTF;
	@FXML private DatePicker dateOfBirthPicker;
	@FXML private TextField emailTF;
	@FXML private TextField phoneNumberTF;
	@FXML private TextField addressTF;
	@FXML private TextField cityTF;
	@FXML private TextField stateTF;
	@FXML private TextField zipcodeTF;
	@FXML private TextField bankNameTF;
	@FXML private TextField cardNumberTF;
	@FXML private TextField cardCVVTF;
	@FXML private DatePicker cardExpirationPicker;
	@FXML private TextField insuranceProviderTF;
	@FXML private TextField memberIdTF;
	@FXML private TextField groupNumberTF;
	@FXML private TextField pharmacyNameTF;
	@FXML private TextField pharmacyAddressTF;
	@FXML private TextField pharmacyCityTF;
	@FXML private TextField pharmacyStateTF;
	@FXML private TextField pharmacyZipcodeTF;
	@FXML private TextField pharmacyPhoneTF;
	@FXML private TextField pharmacyEmailTF;
	@FXML private Button backBtn;
	@FXML private Button submitBtn;
	
	private Patient patient;
	
	// On initialize, determine the current user and update the patient object
	public void initialize() {
		User currentUser = UserManager.getInstance().getCurrentUser();
		String username = currentUser.getUsername();
		
		List<Patient> patientList = DatabaseUtil.getPatients();
		
		for (Patient patient : patientList) {
			if (username.equals(patient.getUsername()))
				this.patient = patient;
		}
		
		
		populateFields();
	}
	
	@FXML	// Method to go back to the patient view screen
	public void goBack(ActionEvent event) throws Exception {
		String fxmlFile = "/FXML/patient_view.fxml";
		SceneManager.loadScene(getClass(), fxmlFile, event);
	}
	
    @FXML	// Method to update the patient information with the values the user has typed into the fields
    public void updateInfo(ActionEvent event) throws Exception {
    	patient.setFirstName(firstNameTF.getText().trim());
    	patient.setLastName(lastNameTF.getText().trim());
    	patient.setDOB(dateOfBirthPicker.getValue());
    	patient.setEmail(emailTF.getText().trim());
    	patient.setPhoneNumber(phoneNumberTF.getText().trim());
    	patient.setAddress(addressTF.getText().trim());
    	patient.setCity(cityTF.getText().trim());
    	patient.setState(stateTF.getText().trim());
    	patient.setZipcode(zipcodeTF.getText().trim());
    	patient.setBankName(bankNameTF.getText().trim());
    	patient.setCardNum(cardNumberTF.getText().trim());
    	patient.setCardCVV(cardCVVTF.getText().trim());
    	patient.setCardExpiration(cardExpirationPicker.getValue());
    	patient.setInsuranceProvider(insuranceProviderTF.getText().trim());
    	patient.setMemberId(memberIdTF.getText().trim());
    	patient.setGroupNum(groupNumberTF.getText().trim());
    	patient.setPharmName(pharmacyNameTF.getText().trim());
    	patient.setPharmAddress(pharmacyAddressTF.getText().trim());
    	patient.setPharmCity(pharmacyCityTF.getText().trim());
    	patient.setPharmState(pharmacyStateTF.getText().trim());
    	patient.setPharmZipcode(pharmacyZipcodeTF.getText().trim());
    	patient.setPharmPhoneNum(pharmacyPhoneTF.getText().trim());
    	patient.setPharmEmail(pharmacyEmailTF.getText().trim());
    	
    	DatabaseUtil.updatePatientValues(patient);
    	System.out.println("Patient values updated!");
    	goBack(event);
    }
    
    // Method to populate the fields with the patient's information
    public void populateFields() {
    	firstNameTF.setText(patient.getFirstName());
    	lastNameTF.setText(patient.getLastName());
    	dateOfBirthPicker.setValue(patient.getDOB());;
    	emailTF.setText(patient.getEmail());
    	phoneNumberTF.setText(patient.getPhoneNumber());
    	addressTF.setText(patient.getAddress());
    	cityTF.setText(patient.getCity());
    	stateTF.setText(patient.getState());
    	zipcodeTF.setText(patient.getZipcode());
    	bankNameTF.setText(patient.getBankName());
    	cardNumberTF.setText(patient.getCardNum());
    	cardCVVTF.setText(patient.getCardCVV());
    	cardExpirationPicker.setValue(patient.getCardExpiration());
    	insuranceProviderTF.setText(patient.getInsuranceProvider());
    	memberIdTF.setText(patient.getMemberId());
    	groupNumberTF.setText(patient.getGroupNum());
    	pharmacyNameTF.setText(patient.getPharmName());
    	pharmacyAddressTF.setText(patient.getPharmAddress());
    	pharmacyCityTF.setText(patient.getPharmCity());
    	pharmacyStateTF.setText(patient.getPharmState());
    	pharmacyZipcodeTF.setText(patient.getPharmZipcode());
    	pharmacyPhoneTF.setText(patient.getPharmPhoneNum());
    	pharmacyEmailTF.setText(patient.getPharmEmail());   	
    }
}
