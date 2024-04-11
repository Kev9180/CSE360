package application;

// Interface for handling actions on patient visit history items in the UI
public interface PatientVisitHistoryItemListener {
    
    // Method signature for handling the click event on a visit item, passing the associated patient and visit
    void onItemClick(Patient patient, Visit visit);
}
