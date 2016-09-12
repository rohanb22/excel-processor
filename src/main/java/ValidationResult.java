

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

	private boolean hasErrors;
	private List<Info> records;
	private List<String> errors;
	public boolean getHasErrors() {
		return hasErrors;
	}
	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
	public List<Info> getRecords() {
		if(records == null || records.isEmpty()){
			records = new ArrayList<Info>();
		}
		return records;
	}
	public void setRecords(List<Info> records) {
		this.records = records;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
}
