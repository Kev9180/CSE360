package application;

// Handles the deletion of an immunization item
// Method can be called with the index of the immunization item for it to be deleted
public interface PatientImmunizationItemListener {
	public void onDeleteItem(int index);
}
