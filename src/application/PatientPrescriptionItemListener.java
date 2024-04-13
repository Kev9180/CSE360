package application;

// Method listens for the deletion events of the prescription items
public interface PatientPrescriptionItemListener {
	public void onDeleteItem(int index);
}
