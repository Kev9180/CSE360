package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;

// DatabaseUtil will handle database operations, to include initializing the various database schemas
public class DatabaseUtil {
	// Create/open userList.db
	private static final String DB_URL = "jdbc:sqlite:userList.db";
	
	// Create/open messageList.db
	private static final String MSG_DB_URL = "jdbc:sqlite:msgList.db";

	// TODO: Will need to implement a staff database for doctors and nurses possibly

	// Method to initialize the userList database by defining the database schema
	public static void initializeDatabase() {
		String sql = "CREATE TABLE IF NOT EXISTS userList (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "username TEXT UNIQUE NOT NULL," + "password TEXT NOT NULL," + "role TEXT NOT NULL,"
				+ "firstName TEXT NOT NULL," + "lastName TEXT NOT NULL," + "dateOfBirth TEXT NULL," + "email TEXT NULL,"
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
	
	// Method to initialize the msgList database
	public static void initializeMessageDatabase() {
		String sql = "CREATE TABLE IF NOT EXISTS msgList (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "sender_id INTEGER," + "recipient_id INTEGER," + "subject TEXT," + "body TEXT,"
				+ "timestamp TEXT," + "read_status INTEGER DEFAULT 0," + "thread_id INTEGER,"
				+ "FOREIGN KEY (sender_id) REFERENCES userList(id)," + "FOREIGN KEY (recipient_id) REFERENCES userList(id));";
		
		try (Connection conn = DriverManager.getConnection(MSG_DB_URL); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			System.out.println("Message database and table initialized successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void preloadUsers() {
		addUser(new User("doctor1", "password", Role.DOCTOR, "Sam", "Smith"));
		addUser(new User("nurse1", "password", Role.NURSE, "Alexa", "Alexerson"));
		addUser(new User("doctor2", "password", Role.DOCTOR, "Bam", "Bmith"));
		addUser(new User("nurse2", "password", Role.NURSE, "Zam", "Zmith"));
		
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
			pstmt.setString(4, user.getFirstName());
			pstmt.setString(5, user.getLastName());


			if (user instanceof Patient) {
				Patient patient = (Patient) user;

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
				for (int i = 6; i <= 28; i++) {
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
	
	// Method to get a list of all nurses and doctors
	public static List<User> getNursesAndDoctors() {
		List<User> users = new ArrayList<>();
		
		String sql = "SELECT * FROM userList WHERE role = 'NURSE' OR role = 'DOCTOR'";
		
		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
	        while (rs.next()) {
	        	Role role = Role.valueOf(rs.getString("role"));
	            users.add(new User(rs.getString("username"), rs.getString("password"), role, rs.getString("firstName"), rs.getString("lastName")));
	        }
	    } catch (SQLException e) {
	        System.err.println(e.getMessage());
	    }
	    return users;
	}
	
	// Method to get a user Id from a name and role
	public static int getUserIdByNameAndRole(String firstName, String lastName, String role) {
		String sql = "SELECT id FROM userList WHERE firstName = ? AND lastName = ? AND role = ?";
		
		 try (Connection conn = DriverManager.getConnection(DB_URL);
				 PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
			 pstmt.setString(1, firstName);
			 pstmt.setString(2, lastName);
			 pstmt.setString(3, role);
	        
			 ResultSet rs = pstmt.executeQuery();
			 
			 if (rs.next()) {
	            return rs.getInt("id");
			 }
	    } catch (SQLException e) {
	        System.err.println("Error fetching user ID by name and role: " + e.getMessage());
	    }
		 
	    return -1;
	}
	
	// Method to get the current user's Id
	public static int getCurrentUserId() {
		User currentUser = UserManager.getInstance().getCurrentUser();
		
		if (currentUser == null) {
			System.err.println("No user logged in!");
			return -1;
		}
		
		String sql = "SELECT id FROM userList WHERE username = ?";
		
		try (Connection conn = DriverManager.getConnection(DB_URL);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	             
	            pstmt.setString(1, currentUser.getUsername());
	            
	            ResultSet rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	                return rs.getInt("id");
	            }
	        } catch (SQLException e) {
	            System.err.println("Error fetching current user ID: " + e.getMessage());
	        }
	        return -1;
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
						return new User(rs.getString("username"), rs.getString("password"), role, rs.getString("firstName"), rs.getString("lastName"));
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
	
	// Method to return the current users ID
	public static int getUserId(String username) {
		String sql = "SELECT id FROM userList WHERE username = ?";
		
		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
	        pstmt.setString(1, username);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("id");
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println(e.getMessage());
	    }
	    return -1;
	}
	
	// Method to return a username from an id
	public static String getUsernameById(int userId) {
		String sql = "SELECT username FROM userList WHERE id = ?";
		
		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, userId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            return rs.getString("username");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error fetching username: " + e.getMessage());
	    }
	    return "Unknown User";
	}
	
	// Method to fetch all messages at startup
    public static Map<Integer, List<Message>> fetchAllMessages() {
        Map<Integer, List<Message>> userMessages = new HashMap<>();
        String sql = "SELECT * FROM msgList ORDER BY timestamp DESC";

        try (Connection conn = DriverManager.getConnection(MSG_DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("id"),
                        rs.getInt("sender_id"),
                        rs.getInt("recipient_id"),
                        rs.getString("subject"),
                        rs.getString("body"),
                        rs.getString("timestamp"),
                        rs.getInt("read_status"),
                        rs.getInt("thread_id"));

                // Add message to recipient's list
                List<Message> recipientMessages = userMessages.computeIfAbsent(message.recipientId, k -> new ArrayList<>());
                recipientMessages.add(message);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching messages: " + e.getMessage());
        }

        return userMessages;
    }
    
    // Method to send a message
    public static void sendMessage(int senderId, int recipientId, String subject, String body) {
    	Integer threadId = findThreadId(senderId, recipientId, subject);
        String sql = "INSERT INTO msgList (sender_id, recipient_id, subject, body, timestamp, read_status, thread_id) VALUES (?, ?, ?, ?, datetime('now'), 0, ?);";
        
        try (Connection conn = DriverManager.getConnection(MSG_DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, recipientId);
            pstmt.setString(3, subject);
            pstmt.setString(4, body);
            
            //If the message is a new thread, initially set threadId to null
            if (threadId == null) {
            	pstmt.setNull(5, Types.INTEGER);
            } else {
            	pstmt.setInt(5, threadId);
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
            	throw new SQLException("Sending message failed");
            }
            
            if (threadId == null) {
            	try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            		if (generatedKeys.next()) {
            			int newMessageId = generatedKeys.getInt(1);
            			updateThreadId(newMessageId, newMessageId);
            		} else {
            			throw new SQLException("Sending message failed");
            		}
            	}
            }
            
            System.out.println("Message sent successfully.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    //
    private static Integer findThreadId(int senderId, int recipientId, String subject) {
    	String sqlFindThread = "SELECT thread_id FROM msgList " +
                "WHERE sender_id = ? AND recipient_id = ? AND subject = ? " +
                "LIMIT 1;";
        
        try (Connection conn = DriverManager.getConnection(MSG_DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sqlFindThread)) {
            
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, recipientId);
            pstmt.setString(3, subject);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("thread_id");
            }
        } catch (SQLException e) {
            System.err.println("Finding thread ID failed: " + e.getMessage());
        }
        
        return null;
    }
    
    //
    private static void updateThreadId(int messageId, int threadId) {
        String sqlUpdate = "UPDATE msgList SET thread_id = ? WHERE id = ?;";
        
        try (Connection conn = DriverManager.getConnection(MSG_DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            
            pstmt.setInt(1, threadId);
            pstmt.setInt(2, messageId);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Updating thread ID failed: " + e.getMessage());
        }
    }

    
    // Method to get messages for a specific user
    public static Map<Integer, List<Message>> getMessagesForUser(int userId) {
        Map<Integer, List<Message>> threads = new HashMap<>();
        String sql = "SELECT * FROM msgList WHERE recipient_id = ? ORDER BY timestamp DESC";

        try (Connection conn = DriverManager.getConnection(MSG_DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int threadId = rs.getInt("thread_id");
                Message message = new Message(
                    rs.getInt("id"),
                    rs.getInt("sender_id"),
                    rs.getInt("recipient_id"),
                    rs.getString("subject"),
                    rs.getString("body"),
                    rs.getString("timestamp"),
                    rs.getInt("read_status"),
                    threadId
                );
                
                if (!threads.containsKey(threadId)) {
                    threads.put(threadId, new ArrayList<>());
                }
                threads.get(threadId).add(message);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching messages for user: " + e.getMessage());
        }
        return threads;
    }


}
