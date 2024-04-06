package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

// DatabaseUtil will handle database operations, to include initializing the various database schemas
public class DatabaseUtil {
	// Create/open userList.db
	private static final String DB_URL = "jdbc:sqlite:userList.db";

	// TODO: Will need to implement a staff database for doctors and nurses possibly

	// Method to initialize the userList database by defining the database schema
	public static void initializeDatabase() {
		String sql = "CREATE TABLE IF NOT EXISTS userList (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "username TEXT UNIQUE NOT NULL," + "password TEXT NOT NULL," + "role TEXT NOT NULL,"
				+ "firstName TEXT NULL," + "lastName TEXT NULL," + "dateOfBirth TEXT NULL," + "email TEXT NULL,"
				+ "phoneNumber TEXT NULL," + "securityQuestion TEXT NULL," + "securityAnswer TEXT NULL,"
				+ "address TEXT NULL," + "city TEXT NULL," + "state TEXT NULL," + "zipcode TEXT NULL,"
				+ "bankName TEXT NULL," + "cardNumber TEXT NULL," + "cardCVV TEXT NULL," + "cardExpiration TEXT NULL,"
				+ "insuranceProvider TEXT NULL," + "memberId TEXT NULL," + "groupNumber TEXT NULL,"
				+ "pharmacyName TEXT NULL," + "pharmacyAddress TEXT NULL," + "pharmacyCity TEXT NULL,"
				+ "pharmacyState TEXT NULL," + "pharmacyZipcode TEXT NULL," + "pharmacyPhoneNumber TEXT NULL,"
				+ "pharmacyEmail TEXT NULL);";

		try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			System.out.println("Database and user table initialized successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void preloadUsers() {
		addUser(new User("doctor1", "password", Role.DOCTOR));
		addUser(new User("nurse1", "password", Role.NURSE));
		addUser(new Patient("patient1", "password", Role.PATIENT, "John", "Doe", LocalDate.of(2001, 1, 1),
				"123-456-7890", "123 Main St", "Phoenix", "Arizona", "85014", "patient1@gmail.com",
				"What is your favorite food?", "Pizza"));

		Patient myPatient = new Patient("username1", "password", Role.PATIENT, "first", "last", LocalDate.now(),
				"1234567890", "address", "city", "state", "00000", "email@example.com", "securityQuestion",
				"securityAnswer");
		Visit myVisit = new Visit(100, 100, 100, 100, null, null, null, "concerns", 10, "location", "examNotes",
				"medicationNotes");
		Patient myPatient2 = new Patient("username2", "password", Role.PATIENT, "first2", "last2", LocalDate.now(),
				"1234567890", "address", "city", "state", "00000", "email@example.com", "securityQuestion",
				"securityAnswer");
		Visit myVisit2 = new Visit(100, 100, 100, 100, null, null, null, "concerns", 10, "location", "examNotes",
				"medicationNotes");
		myPatient.addVisit(myVisit);
		addUser(myPatient);
		myPatient.addVisit(myVisit2);
		addUser(myPatient2);

	}

	// Method to add a patient to the database
	public static void addUser(User user) {
		String sql = "INSERT INTO userList(username, password, role, firstName, lastName, dateOfBirth, email, phoneNumber, securityQuestion, securityAnswer, address, city, state, zipcode, bankName, cardNumber, cardCVV, cardExpiration, insuranceProvider, memberId, groupNumber, pharmacyName, pharmacyAddress, pharmacyCity, pharmacyState, pharmacyZipcode, pharmacyPhoneNumber, pharmacyEmail) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getRole().toString());

			if (user instanceof Patient) {
				Patient patient = (Patient) user;

				pstmt.setString(4, patient.getFirstName());
				pstmt.setString(5, patient.getLastName());
				pstmt.setString(6, patient.getDOB() != null ? patient.getDOB().toString() : null);
				pstmt.setString(7, patient.getEmail());
				pstmt.setString(8, patient.getPhoneNumber());
				pstmt.setString(9, patient.getSecQuestion());
				pstmt.setString(10, patient.getSecAnswer());
				pstmt.setString(11, patient.getAddress());
				pstmt.setString(12, patient.getCity());
				pstmt.setString(13, patient.getState());
				pstmt.setString(14, patient.getZipcode());
				pstmt.setString(15, patient.getBankName() != null ? patient.getBankName() : null);
				pstmt.setString(16, patient.getCardNum() != null ? patient.getCardNum() : null);
				pstmt.setString(17, patient.getCardCVV() != null ? patient.getCardCVV() : null);
				pstmt.setString(18,
						patient.getCardExpiration() != null ? patient.getCardExpiration().toString() : null);
				pstmt.setString(19, patient.getInsuranceProvider() != null ? patient.getInsuranceProvider() : null);
				pstmt.setString(20, patient.getMemberId() != null ? patient.getMemberId() : null);
				pstmt.setString(21, patient.getGroupNum() != null ? patient.getGroupNum() : null);
				pstmt.setString(22, patient.getPharmName() != null ? patient.getPharmName() : null);
				pstmt.setString(23, patient.getPharmAddress() != null ? patient.getPharmAddress() : null);
				pstmt.setString(24, patient.getPharmCity() != null ? patient.getPharmCity() : null);
				pstmt.setString(25, patient.getPharmState() != null ? patient.getPharmState() : null);
				pstmt.setString(26, patient.getPharmZipcode() != null ? patient.getPharmZipcode() : null);
				pstmt.setString(27, patient.getPharmPhoneNum() != null ? patient.getPharmPhoneNum() : null);
				pstmt.setString(28, patient.getPharmEmail() != null ? patient.getPharmEmail() : null);
			} else {
				// Set all the patient attribute fields to null for doctors and nurses
				for (int i = 4; i <= 28; i++) {
					pstmt.setNull(i, java.sql.Types.VARCHAR);
				}
			}

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error adding user: " + e.getMessage());
		}
	}

	// Method to extract patients from the table, create a Patient object, and add
	// that patient to the patient list
	public static List<Patient> getPatients() {
		List<Patient> patients = new ArrayList<>();
		String sql = "SELECT * FROM userList WHERE role = 'PATIENT'";

		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				Patient patient = new Patient(rs.getString("username"), rs.getString("password"),
						Role.valueOf(rs.getString("role")), rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("dateOfBirth") != null ? LocalDate.parse(rs.getString("dateOfBirth")) : null,
						rs.getString("phoneNumber"), rs.getString("address"), rs.getString("city"),
						rs.getString("state"), rs.getString("zipcode"), rs.getString("email"),
						rs.getString("securityQuestion"), rs.getString("securityAnswer"));

				// Check for all the values that could be null if the patient has not updated
				// them yet
				if (rs.getString("bankName") != null)
					patient.setBankName(rs.getString("bankName"));
				if (rs.getString("cardNumber") != null)
					patient.setCardNum(rs.getString("cardNumber"));
				if (rs.getString("cardCVV") != null)
					patient.setCardCVV(rs.getString("cardCVV"));
				if (rs.getString("cardExpiration") != null)
					patient.setCardExpiration(LocalDate.parse(rs.getString("cardExpiration")));
				if (rs.getString("insuranceProvider") != null)
					patient.setInsuranceProvider(rs.getString("insuranceProvider"));
				if (rs.getString("memberId") != null)
					patient.setMemberId(rs.getString("memberId"));
				if (rs.getString("groupNumber") != null)
					patient.setGroupNum(rs.getString("groupNumber"));
				if (rs.getString("pharmacyName") != null)
					patient.setPharmName(rs.getString("pharmacyName"));
				if (rs.getString("pharmacyAddress") != null)
					patient.setPharmAddress(rs.getString("pharmacyAddress"));
				if (rs.getString("pharmacyCity") != null)
					patient.setPharmCity(rs.getString("pharmacyCity"));
				if (rs.getString("pharmacyState") != null)
					patient.setPharmState(rs.getString("pharmacyState"));
				if (rs.getString("pharmacyZipcode") != null)
					patient.setPharmZipcode(rs.getString("pharmacyZipcode"));
				if (rs.getString("pharmacyPhoneNumber") != null)
					patient.setPharmPhoneNum(rs.getString("pharmacyPhoneNumber"));
				if (rs.getString("pharmacyEmail") != null)
					patient.setPharmEmail(rs.getString("pharmacyEmail"));

				patients.add(patient);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return patients;
	}

	// Method to search for a user by their username
	public static User getUserByUsername(String username) {
		String sql = "SELECT * FROM userList WHERE username = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Role role = Role.valueOf(rs.getString("role"));

					switch (role) {
					case PATIENT:
						return createPatient(rs);
					case NURSE:
					case DOCTOR:
						return new User(rs.getString("username"), rs.getString("password"), role);
					default:
						throw new IllegalArgumentException("Unsupported role: " + role);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error retrieving user: " + e.getMessage());
		}

		return null;
	}

	// Method to create and return a Patient object
	private static Patient createPatient(ResultSet rs) throws SQLException {
		LocalDate dob = null;
		String dobStr = rs.getString("dateOfBirth");

		// Convert date of birth to a LocalDate
		if (dobStr != null) {
			dob = LocalDate.parse(dobStr);
		}

		// Create a patient object to return
		Patient patient = new Patient(rs.getString("username"), rs.getString("password"),
				Role.valueOf(rs.getString("role")), rs.getString("firstName"), rs.getString("lastName"), dob,
				rs.getString("phoneNumber"), rs.getString("address"), rs.getString("city"), rs.getString("state"),
				rs.getString("zipcode"), rs.getString("email"), rs.getString("securityQuestion"),
				rs.getString("securityAnswer"));

		// Check for all the values that could be null if the patient has not updated
		// them yet
		if (rs.getString("bankName") != null)
			patient.setBankName(rs.getString("bankName"));
		if (rs.getString("cardNumber") != null)
			patient.setCardNum(rs.getString("cardNumber"));
		if (rs.getString("cardCVV") != null)
			patient.setCardCVV(rs.getString("cardCVV"));
		if (rs.getString("cardExpiration") != null)
			patient.setCardExpiration(LocalDate.parse(rs.getString("cardExpiration")));
		if (rs.getString("insuranceProvider") != null)
			patient.setInsuranceProvider(rs.getString("insuranceProvider"));
		if (rs.getString("memberId") != null)
			patient.setMemberId(rs.getString("memberId"));
		if (rs.getString("groupNumber") != null)
			patient.setGroupNum(rs.getString("groupNumber"));
		if (rs.getString("pharmacyName") != null)
			patient.setPharmName(rs.getString("pharmacyName"));
		if (rs.getString("pharmacyAddress") != null)
			patient.setPharmAddress(rs.getString("pharmacyAddress"));
		if (rs.getString("pharmacyCity") != null)
			patient.setPharmCity(rs.getString("pharmacyCity"));
		if (rs.getString("pharmacyState") != null)
			patient.setPharmState(rs.getString("pharmacyState"));
		if (rs.getString("pharmacyZipcode") != null)
			patient.setPharmZipcode(rs.getString("pharmacyZipcode"));
		if (rs.getString("pharmacyPhoneNumber") != null)
			patient.setPharmPhoneNum(rs.getString("pharmacyPhoneNumber"));
		if (rs.getString("pharmacyEmail") != null)
			patient.setPharmEmail(rs.getString("pharmacyEmail"));

		return patient;
	}

	// Method to check credentials when a patient is logging in
	public static boolean checkCredentials(String username, String password) {
		String sql = "SELECT password FROM userList WHERE username = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String storedPassword = rs.getString("password");
				return password.equals(storedPassword);
			}
		} catch (SQLException e) {
			System.err.println("Login error: " + e.getMessage());
		}
		return false;
	}

	// Method to update a user's password in the database
	public static boolean updatePassword(String username, String newPassword) {
		String sql = "UPDATE userList SET password = ? WHERE username = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, newPassword);
			pstmt.setString(2, username);

			int affectedRows = pstmt.executeUpdate();

			return affectedRows > 0;
		} catch (SQLException e) {
			System.err.println("Error updating password: " + e.getMessage());
			return false;
		}
	}

	// Method to check the database for a username to ensure only unique usernames
	// are given out
	public static boolean usernameExists(String username) {
		String sql = "SELECT id FROM userList WHERE username = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			System.err.println("Error checking for username: " + e.getMessage());
			return false;
		}
	}

	// Method to return the password for a user
	public static String getCurrentPassword(String username) {
		String sql = "SELECT password FROM userList WHERE username = ?";

		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString("password");
			}
		} catch (SQLException e) {
			System.err.println("Error fetching password: " + e.getMessage());
		}
		return null;
	}
}
