

import org.springframework.web.multipart.MultipartFile;

import com.rohan.model.ValidationResult;

public interface FileUploadService {

	public ValidationResult uploadFile(MultipartFile excelfile);
}
