package application;

//Interface for handling actions on patient immunization items in the UI
// Handles the deletion of an immunization item
// Method can be called with the index of the immunization item for it to be deleted
public interface PatientImmunizationItemListener {
	// Method signature for handling the deletion of an item at the specified index
	public void onDeleteItem(int index);
}
  