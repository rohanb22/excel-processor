

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.rohan.model.ExcelFile;
import com.rohan.model.Info;
import com.rohan.service.RecursiceInfoExtractor;

@Component
public class FileValidator implements Validator {
	
    @Override
    public boolean supports(Class<?> clazz) {
        return ExcelFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExcelFile excelFile = (ExcelFile) target;
        Info info = null;
        if (excelFile.getFile() != null && !excelFile.getFile().getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            errors.rejectValue("file", "file.extn");
            return;
        }
        
        if (excelFile.getFile() != null && excelFile.getFile().isEmpty()){
            errors.rejectValue("file", "file.empty");
            return;
        }
        try(Workbook wb = new XSSFWorkbook(excelFile.getFile().getInputStream())) {
        	info = RecursiceInfoExtractor.extractHeader(wb);
        	if(!"Name".equalsIgnoreCase(info.getName())){
        		errors.rejectValue("file", "file.header.name");
        	}
        	if(!"Comm Address".equalsIgnoreCase(info.getCommAddress())){
        		errors.rejectValue("file", "file.header.commAdd");
        	}
        	if(!"Mobile".equalsIgnoreCase(info.getMobile())){
        		errors.rejectValue("file", "file.header.mobile");
        	}
        	if(!"Perm Address".equalsIgnoreCase(info.getPermAddress())){
        		errors.rejectValue("file", "file.header.permAdd");
        	}
        	if(!"Phone".equalsIgnoreCase(info.getPhone())){
        		errors.rejectValue("file", "file.header.phone");
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
    }

    
    
}
