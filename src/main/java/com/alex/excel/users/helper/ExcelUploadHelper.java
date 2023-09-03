package com.alex.excel.users.helper;

import com.alex.excel.users.entity.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class ExcelUploadHelper {

    public static boolean isValidExcelFile(MultipartFile file) {
        // Excel contentType
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return Objects.equals(file.getContentType(), contentType);
    }

    public static List<User> getUsersDataFromExcel(InputStream inputStream) {
        List<User> users = new ArrayList<>();


        // // // work with Excel   (ApachePOI)
        try {
            // workbook
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("customers");  // tab in excel

            // // go through the sheet rows
            int rowIndex = 0;
            for (Row row : sheet) {
                // skip headings
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }

                // go through each cell in order to build user
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                User user = new User();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();  // move to the next cell to end the while loop

                    // build user (depends on the Excel data structure)
                    switch (cellIndex) {
                        case 0 -> user.setFirstname(cell.getStringCellValue());
                        case 1 -> user.setLastname(cell.getStringCellValue());
                        case 2 -> user.setCountry(cell.getStringCellValue());
                        case 3 -> user.setAge((int) cell.getNumericCellValue());  // getNumeric returns double
                        case 4 -> user.setTelephone(cell.getStringCellValue());
                        default -> {
                        }
                    }

                    cellIndex++;
                }

                users.add(user);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        return users;
    }

}
