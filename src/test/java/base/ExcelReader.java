package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public FileInputStream fis;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	String path;

	public ExcelReader(String path) throws IOException {
		this.path=path;
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(0);
		fis.close();
	}

	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);
		int count = sheet.getLastRowNum() + 1;
		return count;
	}

	public int getRowNum(String sheetName, String testcaseName) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			int row_Num = 0;
			sheet = workbook.getSheetAt(index);
			int rowCount = getRowCount(sheetName);
			for (int i = 0; i < rowCount; i++) {
				cell = sheet.getRow(i).getCell(0);
				if (!cell.getStringCellValue().equals("")) {
					if (cell.getStringCellValue().trim().equals(testcaseName.trim())) {
						row_Num = i;
					}
				}
			}
			return row_Num;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		int index = workbook.getSheetIndex(sheetName);
		int col_Num = 0;
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
				col_Num = i;
				break;
			}
		}
		row = sheet.getRow(rowNum);
		cell = row.getCell(col_Num);
		if (cell == null)
			return "";
		
		return cell.getStringCellValue();
	}

	public int getRowCount(String sheetName, String colName, String methodName) {
		ArrayList<Integer> rows = new ArrayList<>();
		for (int i = 1; i < getRowCount(sheetName); i++) {
			String tcid = getCellData(sheetName, colName, i);
			if (tcid.equals(methodName)) {
				rows.add(i);
			}
		}
		return rows.size();
	}

	// returns number of columns in a sheet
	public int getColumnCount(String sheetName) {
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		return row.getLastCellNum();
	}

	public int getColumnNumber(String sheetName,String colName){
		//To get Column count in the specified sheet
		XSSFRow row=sheet.getRow(0);
		int colCount=row.getLastCellNum();
		//System.out.println("Column count: "+colCount);

		//To get data from specified column and row number
		int colNum=0;
		for(int i=0;i<colCount;i++){
			if(row.getCell(i).getStringCellValue().equals(colName)){
				colNum=i;
				break;
			}
		}
		return colNum;
	}

	public int getFirstDataRowNum(String sheetName, String colName, String methodName) {
		for (int i = 1; i < getRowCount(sheetName); i++) {
			String tcid = getCellData(sheetName, colName, i);
			if (tcid.equals(methodName)) {
				return i;
			}
		}
		return -1;
	}

	public int getLastDataRowNum(String SheetName, String colName, String methodName, int firstRow) {
		for (int i = firstRow; i <= getRowCount(SheetName); i++) {
			String tcid = getCellData(SheetName, colName, i);
			if (i == getRowCount(SheetName)) {
				if (tcid.contentEquals(methodName)) {
					return i;
				}
			}
			if (!tcid.contentEquals(methodName)) {
				return i - 1;
			}
		}
		return -1;
	}
	//Set Cell Data based on the test case id and column Name 
	public void setCellData(String sheetName, String testcaseName, String columnName, String status) throws IOException {
		int sheetIndex = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(sheetIndex);
		
		int caseRowNumber=getRowNum(sheetName, testcaseName);
		int colNumber=getColumnNumber(sheetName, columnName);
		
		sheet.getRow(caseRowNumber).createCell(colNumber).setCellValue(status);
		FileOutputStream fileOutput = new FileOutputStream(path);
		workbook.write(fileOutput);
		fileOutput.close();

	}
	// obj[0][0]
	
	public ArrayList<AddUser> getUsersDataFromExcel(String sheetName, String colName, String methodName) {
		int rowCount=getRowCount(sheetName, colName, methodName);
		int colCount=getColumnCount(sheetName);
		int firstRow=getFirstDataRowNum(sheetName, colName, methodName);
		String data[][]=new String[rowCount][colCount];
		ArrayList<AddUser> al=new ArrayList<>();
		for(int i=0;i<rowCount;i++) {
			for(int j=0;j<colCount;j++) {
				data[i][j]=sheet.getRow(firstRow+i).getCell(j).getStringCellValue();
			}
		
			al.add(new AddUser(data[i][0],data[i][1],data[i][2],data[i][3],data[i][4],data[i][5]));
		}
		
		return al;
		
	}

}
