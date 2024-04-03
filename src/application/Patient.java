package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
	//Personal and contact information
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String email;
	private String phoneNumber;
	
	//Security question attributes
	private String securityQuestion;
	private String securityAnswer;
	
	//List of visits
	private List<Visit> visitHistory = new ArrayList<>();
	
	//Address attributes
	private String address;
	private String city;
	private String state;
	private String zipcode;
	
	//Payment attributes
	private String bankName;
	private String cardNumber;
	private String cardCVV;
	private LocalDate cardExpiration;
	
	//Insurance attributes
	private String insuranceProvider;
	private String memberId;
	private String groupNumber;
	
	//Pharmacy attributes
	private String pharmacyName;
	private String pharmacyAddress;
	private String pharmacyCity;
	private String pharmacyState;
	private String pharmacyZipcode;
	private String pharmacyPhoneNumber;
	private String pharmacyEmail;
	
	//Constructor - creates a new patient and user object and updates the personal and contact information
	public Patient(String username, String password, Role role, String firstName, String lastName, 
				   LocalDate dateOfBirth, String phoneNumber, String streetAddress, String city,
				   String state, String zipcode, String email, String securityQuestion, String securityAnswer) {
		//Call the User superclass constructor
		super(username, password, role);
		
		//Update Patient attributes
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.address = streetAddress;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.email = email;
		this.securityQuestion = securityQuestion;
		this.securityAnswer = securityAnswer;
	}
	
	//Update payment details for a patient
	public void updatePaymentInfo(String bank, String cardNum, String securityCode, LocalDate expiration) {
		this.bankName = bank;
		this.cardNumber = cardNum;
		this.cardCVV = securityCode;
		this.cardExpiration = expiration;
	}
	
	//Update insurance information for a patient
	public void updateInsuranceInfo(String provider, String memberId, String groupNum) {
		this.insuranceProvider = provider;
		this.memberId = memberId;
		this.groupNumber = groupNum;
	}
	
	//Update preferred pharmacy information for a patient
	public void updatedPharmacyInfo(String pharmName, String pharmAddress, String pharmCity, String pharmState,
									String pharmZip, String pharmPhoneNum, String pharmEmail) {
		this.pharmacyName = pharmName;
		this.pharmacyAddress = pharmAddress;
		this.pharmacyCity = pharmCity;
		this.pharmacyState = pharmState;
		this.pharmacyZipcode = pharmZip;
		this.pharmacyPhoneNumber = pharmPhoneNum;
		this.pharmacyEmail = pharmEmail;
	}
	
	//TODO: Add a method to add a visit to the list of visits
	
	
	//Getter for username
	public String getUsername() {
		return super.getUsername();
	}
	
	//Getter for name
	public String getName() {
		return this.firstName + " " + this.lastName;
	}
}
