package application;

//Enum to define the types of Roles available for Users
public enum Role {
	PATIENT, NURSE, DOCTOR;
	
	@Override
	public String toString() {
		return name();
	}
}
