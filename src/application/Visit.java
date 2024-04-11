package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

//Class representing a visit made by a patient
public class Visit {
	// information about each visit from the patient
	private String height;
	private String weight;
	private String temperature;
	private String bloodPressure;
	private List<String> immunizations = new ArrayList<String>();
	private List<String> allergies = new ArrayList<String>();
	private List<String> prescribedMedication = new ArrayList<String>();
	private String healthConcerns;
	private List<String> dosages = new ArrayList<String>();
	private String location;
	private String physicalExamNotes;
	private String medicationNotes;
	private LocalDateTime visitDateFormatted;
	private LocalDate visitDate;
	
	// Default constructor
	public Visit() {}
	
	// Constructor with essential visit details
	public Visit(String healthConcerns, LocalDateTime dateTime, LocalDate date) {
		this.healthConcerns = healthConcerns;
		this.visitDateFormatted = dateTime;
		this.visitDate = date;
	}
	
	// Constructor with full visit details
	public Visit(String height, String weight, String temperature, String bloodPressure, List<String> immunizations,
			List<String> allergies, List<String> prescribedMedication, String healthConcerns, List<String> dosages,
			String location, String physicalExamNotes, String medicationNotes) {
		this.height = height;
		this.weight = weight;
		this.temperature = temperature;
		this.bloodPressure = bloodPressure;
		this.immunizations = immunizations;
		this.allergies = allergies;
		this.prescribedMedication = prescribedMedication;
		this.healthConcerns = healthConcerns;
		this.dosages = dosages;
		this.location = location;
		this.physicalExamNotes = physicalExamNotes;
		this.medicationNotes = medicationNotes;
		this.visitDateFormatted = LocalDateTime.now();
		this.visitDate = LocalDate.now();
	}
	
	// I'm going insane
	// Getters and setters for visit attributes
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public List<String> getImmunizations() {
		return immunizations;
	}
	public void setImmunizations(List<String> immunizations) {
		this.immunizations = immunizations;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	public List<String> getPrescribedMedication() {
		return prescribedMedication;
	}
	public void setPrescribedMedication(List<String> prescribedMedication) {
		this.prescribedMedication = prescribedMedication;
	}
	public String getHealthConcerns() {
		return healthConcerns;
	}
	public void setHealthConcerns(String healthConcerns) {
		this.healthConcerns = healthConcerns;
	}
	public List<String> getDosages() {
		return dosages;
	}
	public void setDosages(List<String> dosages) {
		this.dosages = dosages;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhysicalExamNotes() {
		return physicalExamNotes;
	}
	public void setPhysicalExamNotes(String physicalExamNotes) {
		this.physicalExamNotes = physicalExamNotes;
	}
	public String getMedicationNotes() {
		return medicationNotes;
	}
	public void setMedicationNotes(String medicationNotes) {
		this.medicationNotes = medicationNotes;
	}
	public LocalDateTime getVisitDateFormatted() {
		return visitDateFormatted;
	}
	public void setVisitDateFormatted(LocalDateTime date) {
		this.visitDateFormatted = date;
	}
	public LocalDate getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(LocalDate date) {
		this.visitDate = date;
	}
}
