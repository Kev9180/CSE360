package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VisitHistoryManager {
	private static final String BASE_DIRECTORY = "./patient_visits/";
	
	// Method to store a visit for a specified patient in the patients visit history directory
	public static void storeVisit(Patient patient, Visit visit) throws IOException {
		String patientUsername = patient.getUsername();
		String directoryName = BASE_DIRECTORY + patientUsername + "_VisitHistory";
		File directory = new File(directoryName);
		
		// Create the directory if it doesn't exist
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		// The actual visit filename inside of the specific patient directory will be yyyy-MM-dd_HH-mm-ss_visit.txt
		// Create a date formatter to format the date for the filename
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		String filename = visit.getVisitDateFormatted().format(formatter) + "_visit.txt";
		
		try (FileWriter writer = new FileWriter(new File(directory, filename))) {
			writer.write("Patient Username: " + patientUsername + "\n");
			writer.write("First Name: " + patient.getFirstName() + "\n");
            writer.write("Last Name: " + patient.getLastName() + "\n");
            writer.write("Height: " + visit.getHeight() + "\n");
            writer.write("Weight: " + visit.getWeight() + "\n");
            writer.write("Temperature: " + visit.getTemperature() + "\n");
            writer.write("Blood Pressure: " + visit.getBloodPressure() + "\n");
            
            // Allergies, Immunizations, and Prescribed Medications are a list, so user a comma delimiter
            writer.write("Allergies: " + String.join(",", visit.getAllergies()) + "\n");
            writer.write("Immunizations: " + String.join(",", visit.getImmunizations()) + "\n");
            writer.write("Prescribed Medications: " + String.join(",", visit.getPrescribedMedication()) + "\n");
            
            writer.write("Health Concerns: " + visit.getHealthConcerns() + "\n");
            writer.write("Dosage: " + String.join(",", visit.getDosages()) + "\n");
            writer.write("Location: " + visit.getLocation() + "\n");
            writer.write("Physical Exam Notes: " + visit.getPhysicalExamNotes() + "\n");
            writer.write("Medication Notes: " + visit.getMedicationNotes() + "\n");
            writer.write("Visit Date: " + visit.getVisitDate() + "\n");
            writer.write("Visit Date Formatted: " + visit.getVisitDateFormatted());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method to return a list of all visits for a specified patient
	public static List<Visit> getVisitsForPatient(String patientUsername) {
		List<Visit> visits = new ArrayList<>();
		String directoryName = BASE_DIRECTORY + patientUsername + "_VisitHistory";
		File directory = new File(directoryName);
		
		// If the directory doesn't exist, then this patient doesn't have any visits associated with them
		if (!directory.exists()) {
			return visits;
		}
		
		File[] files = directory.listFiles();
		
		// If visit files were found for the patient, parse each visit from each file
		if (files != null) {
			for (File file: files) {
				Visit visit = parseVisitFromFile(file);
				if (visit != null) {
					visits.add(visit);
				}
			}
		}
		
		return visits;
	}
	
	// Method to parse Visit information from a file
	private static Visit parseVisitFromFile(File file) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			Visit visit = new Visit();
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(": ", 2);
				
				if (parts.length == 2) {
					switch (parts[0]) {
                    case "Height":
                        visit.setHeight(parts[1]);
                        break;
                    case "Weight":
                        visit.setWeight(parts[1]);
                        break;
                    case "Temperature":
                        visit.setTemperature(parts[1]);
                        break;
                    case "Blood Pressure":
                        visit.setBloodPressure(parts[1]);
                        break;
                    case "Allergies":
                        visit.setAllergies(parseListFromString(parts[1]));
                        break;
                    case "Immunizations":
                        visit.setImmunizations(parseListFromString(parts[1]));
                        break;
                    case "Prescribed Medications":
                        visit.setPrescribedMedication(parseListFromString(parts[1]));
                        break;
                    case "Health Concerns":
                        visit.setHealthConcerns(parts[1]);
                        break;
                    case "Dosage":
                        visit.setDosages(parseListFromString(parts[1]));
                        break;
                    case "Location":
                        visit.setLocation(parts[1]);
                        break;
                    case "Physical Exam Notes":
                        visit.setPhysicalExamNotes(parts[1]);
                        break;
                    case "Medication Notes":
                        visit.setMedicationNotes(parts[1]);
                        break;
                    case "Visit Date":
                        visit.setVisitDate(LocalDate.parse(parts[1]));
                        break;
                    case "Visit Date Formatted":
                    	visit.setVisitDateFormatted(LocalDateTime.parse(parts[1]));
                    	break;
					}
				}
			}
			
			return visit;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// Method to create a list from a string of comma separated values
	private static List<String> parseListFromString(String s) {
		List<String> list = new ArrayList<>();
		String[] items = s.split(",");	//Split into parts using a comma delimiter
		
		// For each item in the list, trim to remove whitespace and add to the list if the item isn't empty
		for (String item: items) {
			item = item.trim();
			
			if (!item.isEmpty()) {
				list.add(item);
			}
		}
		
		return list;
	}
	
	// Method to update specific visit information - Doctor's portion will need to use this function
	public static void updateVisit(Patient patient, LocalDateTime localDateTime, String location, String physicalExamNotes, String medicationNotes) throws IOException {
		String patientUsername = patient.getUsername();
		String directoryName = BASE_DIRECTORY + patientUsername + "_VisitHistory";
		File directory = new File(directoryName);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		String filename = localDateTime.format(formatter) + "_visit.txt";
		File visitFile = new File(directory, filename);
		
		// If the file couldn't be found, then it doesn't exist or there was an error
		if (!visitFile.exists()) {
			System.out.println("Error: file does not exist");
			return;
		}
		
		// Parse the existing visit info from the file
		Visit visit = parseVisitFromFile(visitFile);
		
		// Update the visit with the information from the doctor
		if (location != null) visit.setLocation(location);
		if (physicalExamNotes != null) visit.setPhysicalExamNotes(physicalExamNotes);
		if (medicationNotes != null) visit.setMedicationNotes(medicationNotes);
		
		// Overwrite the file with the updated info
		try (FileWriter writer = new FileWriter(visitFile)) {
			writer.write("Patient Username: " + patientUsername + "\n");
			writer.write("First Name: " + patient.getFirstName() + "\n");
            writer.write("Last Name: " + patient.getLastName() + "\n");
            writer.write("Height: " + visit.getHeight() + "\n");
            writer.write("Weight: " + visit.getWeight() + "\n");
            writer.write("Temperature: " + visit.getTemperature() + "\n");
            writer.write("Blood Pressure: " + visit.getBloodPressure() + "\n");
            
            // Allergies, Immunizations, and Prescribed Medications are a list, so user a comma delimiter
            writer.write("Allergies: " + String.join(",", visit.getAllergies()) + "\n");
            writer.write("Immunizations: " + String.join(",", visit.getImmunizations()) + "\n");
            writer.write("Prescribed Medications: " + String.join(",", visit.getPrescribedMedication()) + "\n");
            
            writer.write("Health Concerns: " + visit.getHealthConcerns() + "\n");
            writer.write("Dosages: " + String.join(",", visit.getDosages()) + "\n");
            writer.write("Location: " + visit.getLocation() + "\n");
            writer.write("Physical Exam Notes: " + visit.getPhysicalExamNotes() + "\n");
            writer.write("Medication Notes: " + visit.getMedicationNotes() + "\n");
            writer.write("Visit Date: " + visit.getVisitDate() + "\n");
            writer.write("Visit Date Formatted: " + visit.getVisitDateFormatted());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
//all comments completed
