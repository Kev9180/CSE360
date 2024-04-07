package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
	//Personal and contact information
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
		super(username, password, role, firstName, lastName);
		
		//Update Patient attributes
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
	
	public void addVisit(Visit visit) {
		visitHistory.add(visit);
	}
	
	public String getUsername() {
		return super.getUsername();
	}
	
	public String getName() {
		return this.firstName + " " + this.lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public LocalDate getDOB() {
		return this.dateOfBirth;
	}
	
	public void setDOB(LocalDate dob) {
		this.dateOfBirth = dob;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNum) {
		this.phoneNumber = phoneNum;
	}
	
	public String getSecQuestion() {
		return this.securityQuestion;
	}
	
	public void setSecQuestion(String secQuestion) {
		this.securityQuestion = secQuestion;
	}
	
	public String getSecAnswer() {
		return this.securityAnswer;
	}
	
	public void setSecAnswer(String secAnswer) {
		this.securityAnswer = secAnswer;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZipcode() {
		return this.zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getCardNum() {
		return this.cardNumber;
	}
	
	public void setCardNum(String cardNum) {
		this.cardNumber = cardNum;
	}
	
	public LocalDate getCardExpiration() {
		return this.cardExpiration;
	}
	
	public void setCardExpiration(LocalDate expiration) {
		this.cardExpiration = expiration;
	}
	
	public String getCardCVV() {
		return this.cardCVV;
	}
	
	public void setCardCVV(String cvv) {
		this.cardCVV = cvv;
	}
	
	public String getInsuranceProvider() {
		return this.insuranceProvider;
	}
	
	public void setInsuranceProvider(String provider) {
		this.insuranceProvider = provider;
	}
	
	public String getMemberId() {
		return this.memberId;
	}
	
	public void setMemberId(String id) {
		this.memberId = id;
	}
	
	public String getGroupNum() {
		return this.groupNumber;
	}
	
	public void setGroupNum(String groupNum) {
		this.groupNumber = groupNum;
	}
	
	public String getPharmName() {
		return this.pharmacyName;
	}
	
	public void setPharmName(String name) {
		this.pharmacyName = name;
	}
	
	public String getPharmAddress() {
		return this.pharmacyAddress;
	}
	
	public void setPharmAddress(String address) {
		this.pharmacyAddress = address;
	}
	
	public String getPharmCity() {
		return this.pharmacyCity;
	}
	
	public void setPharmCity(String city) {
		this.pharmacyCity = city;
	}
	
	public String getPharmState() {
		return this.pharmacyState;
	}
	
	public void setPharmState(String state) {
		this.pharmacyState = state;
	}
	
	public String getPharmZipcode() {
		return this.pharmacyZipcode;
	}
	
	public void setPharmZipcode(String zip) {
		this.pharmacyZipcode = zip;
	}
	
	public String getPharmEmail() {
		return this.pharmacyEmail;
	}
	
	public void setPharmEmail(String email) {
		this.pharmacyEmail = email;
	}
	
	public String getPharmPhoneNum() {
		return this.pharmacyPhoneNumber;
	}
	
	public void setPharmPhoneNum(String phoneNum) {
		this.pharmacyPhoneNumber = phoneNum;
	}
	
	//Getter for visit history
	public List<Visit> getVisitHistory() {
		return visitHistory;
	}
	
	//
	public String serialize() {
		return "";
	}
}
