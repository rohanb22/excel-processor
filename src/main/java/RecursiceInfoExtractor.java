

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.rohan.model.Info;
import com.rohan.model.ValidationResult;

public class RecursiceInfoExtractor extends RecursiveTask<List<Info>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4201086428704039718L;
	private Sheet sheet;
	private int start;
	private int end;
	private ValidationResult result;
	
	public RecursiceInfoExtractor(Sheet sheet,int start, int end, ValidationResult result){
		this.sheet = sheet;
		this.start = start;
		this.end = end;
		this.result = result;
		
	}
	@Override
	protected List<Info> compute() {
		int totalNumberOfRows = 0;
		List<Info> list = new ArrayList<Info>();
	    List<RecursiceInfoExtractor> tasks = new ArrayList<RecursiceInfoExtractor>();
	    Row row = null;
		totalNumberOfRows = end - start + 1;
		boolean isInvalid = false;
		
		
		if(totalNumberOfRows > 10){
			RecursiceInfoExtractor task1 = new RecursiceInfoExtractor(sheet,start,start+totalNumberOfRows/2 -1, result);
			RecursiceInfoExtractor task2 = new RecursiceInfoExtractor(sheet,start+totalNumberOfRows/2,start+totalNumberOfRows-1, result);
			task1.fork();
			task2.fork();
			tasks.add(task1);
			tasks.add(task2);
			
		}else{
			for (int i = start; i <= end; i++){
				
				row = sheet.getRow(i);
           	 
                List<Cell> cells = new ArrayList<Cell>();
                int lastColumn = Math.max(row.getLastCellNum(), 5);
                for (int cn = 0; cn < lastColumn; cn++) {
                    Cell c = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                    cells.add(c);
                }
                Info info = extractInfoFromCell(cells);
                isInvalid = validate(info);
                if(!result.getHasErrors() && isInvalid){
                	result.setHasErrors(isInvalid);
                }
                list.add(info);
            }
		}
		addResultsFromTasks(list,tasks);
		return list;
	}

	private void addResultsFromTasks(List<Info> list, List<RecursiceInfoExtractor> tasks)
	   {
	      for (RecursiceInfoExtractor item : tasks)
	      {
	         list.addAll(item.join());
	      }
	   }
	
	public boolean validate(Info record){
		boolean isInvalid = false;
		StringBuilder sb = new StringBuilder();
		if(record.getName().isEmpty()){
			sb.append("Name cannot be empty");
		}
		
		record.setErrorDescription(sb.toString());
		return isInvalid;
	}

    private static Info extractInfoFromCell(List<Cell> cells) {
        Info info = new Info();
        Cell nameCell = cells.get(0);
        if (nameCell != null) {
            nameCell.setCellType(Cell.CELL_TYPE_STRING);
            info.setName(nameCell.getStringCellValue());
        }
        Cell mobileCell = cells.get(1);
        if (mobileCell != null) {
            mobileCell.setCellType(Cell.CELL_TYPE_STRING);
            info.setMobile(mobileCell.getStringCellValue());
        }
        Cell phoneCell = cells.get(2);
        if (phoneCell != null) {
            phoneCell.setCellType(Cell.CELL_TYPE_STRING);
            info.setPhone(phoneCell.getStringCellValue());
        }
        Cell permAddressCell = cells.get(3);
        if (permAddressCell != null) {
            permAddressCell.setCellType(Cell.CELL_TYPE_STRING);
            info.setPermAddress(permAddressCell.getStringCellValue());
        }
        Cell commAddressCell = cells.get(4);
        if (commAddressCell != null) {
            commAddressCell.setCellType(Cell.CELL_TYPE_STRING);
            info.setCommAddress(commAddressCell.getStringCellValue());
        }
        return info;
    }
    
    public static Info extractHeader(Workbook wb){
    	Info info = null;
    	Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(0);
        List<Cell> cells = new ArrayList<Cell>();
        int lastColumn = Math.max(row.getLastCellNum(), 5);
        for (int cn = 0; cn < lastColumn; cn++) {
            Cell c = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
            cells.add(c);
        }
        info = extractInfoFromCell(cells);
        return info;
    }

}
