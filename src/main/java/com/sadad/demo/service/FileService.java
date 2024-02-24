package com.sadad.demo.service;

import com.sadad.demo.dto.Bill;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FileService {
    public Set<Bill> processExcelFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            DecimalFormat decimalFormat = new DecimalFormat("#.#####");
            //This variable is for counting the repeated rows
            Set<Bill> bills = new HashSet<>();
            for (int i = 1 ; i < sheet.getLastRowNum()+1 ; i++) {
                Bill bill = new Bill();
                for (int j = 0 ; j < sheet.getRow(i).getLastCellNum() ; j ++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    switch (getCellColumnName(cell)) {
                        case "name" -> bill.setFullName(cell.getStringCellValue());

                        case "accountNumber" -> bill.setAccountNumber(String.valueOf(cell.getNumericCellValue()));

                        case "amount" -> bill.setAmount(cell.getNumericCellValue());
                        default -> System.out.print(" ");
                    }
                }
                
                bills.add(bill);
            }
               return bills;
        }


    }
    private String getCellColumnName(Cell cell) {
        return cell.getSheet().getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
    }
}
