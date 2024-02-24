package com.sadad.demo.service;

import com.sadad.demo.dto.Bill;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.#####");
    private Map<String, Set<Bill>> processExcelFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Map<String, Set<Bill>> output = new HashMap<>();

            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            //This variable is for counting the repeated rows
            Set<Bill> repeatedBill = new HashSet<>();
            Set<Bill> bills = new HashSet<>();
            for (int i = 1 ; i < sheet.getLastRowNum()+1 ; i++) {
                Bill bill = new Bill();
                for (int j = 0 ; j < sheet.getRow(i).getLastCellNum() ; j ++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    switch (getCellColumnName(cell)) {
                        case "name" -> bill.setFullName(cell.getStringCellValue());

                        case "accountNumber" -> bill.setAccountNumber(decimalFormat.format(cell.getNumericCellValue()   ));

                        case "amount" -> bill.setAmount(Double.parseDouble(decimalFormat.format(cell.getNumericCellValue())));
                        default -> System.out.print(" ");
                    }
                }

                if (bills.add(bill)){
                    output.put("bills", bills);
                }
                else {
                    repeatedBill.add(bill);
                    output.put("repeatedBills", repeatedBill);
                }
            }
               return output;
        }


    }

    private int getNotRepeatedBillNumber(Set<Bill> bills){
        return bills.size();
    }

    private double calculateSumOfAmount(Set<Bill> bills){
        double sum = 0.0;
        for (Bill bill : bills) {
            sum += bill.getAmount();
        }
        return sum;
    }
    private String getCellColumnName(Cell cell) {
        return cell.getSheet().getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
    }

    public Map<String, Object> getResult(MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        Map<String, Set<Bill>>  fileOutput= processExcelFile(file);
        result.put("repeatedBills", fileOutput.get("repeatedBills"));
        result.put("notRepeatedBillsCount", getNotRepeatedBillNumber(fileOutput.get("bills")));
        result.put("sum", calculateSumOfAmount(fileOutput.get("bills")));
        return result;
    }
}
