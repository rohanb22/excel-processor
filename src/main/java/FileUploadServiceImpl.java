

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rohan.bo.InfoBO;
import com.rohan.dao.InfoDAO;
import com.rohan.model.Info;
import com.rohan.model.ValidationResult;

@Service
public class FileUploadServiceImpl implements FileUploadService{

	@Autowired
	private InfoProcessor infoProcessor;
	
	@Autowired
	private InfoDAO infoDAO;
	
	public ValidationResult uploadFile(MultipartFile excelfile){
		List<Info> records = null;
		List<InfoBO> bos = null;
		ValidationResult result = new ValidationResult();
	    
		try(Workbook wb = new XSSFWorkbook(excelfile.getInputStream());) {
			
			Sheet sheet = wb.getSheetAt(0);
            
            ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            RecursiceInfoExtractor infoExtractor = new RecursiceInfoExtractor(sheet, 1, sheet.getPhysicalNumberOfRows(), result);
            records = pool.invoke(infoExtractor);
            result.setRecords(records);
			if(!result.getHasErrors()){
				bos = infoProcessor.process(records);
				//TODO fork join to save bos using InfoDAO
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*public InfoExtractor getInfoExtractor() {
		return infoExtractor;
	}

	public void setInfoExtractor(InfoExtractor infoExtractor) {
		this.infoExtractor = infoExtractor;
	}*/
	
	
	
}
