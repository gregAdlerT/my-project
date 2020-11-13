package com.example.application.utils;

import com.example.application.data.entity.Order;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Greg Adler
 */

public class DataMapper {
    public static void mapToXls(List<Order> orders) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet( "Data");
        XSSFRow row;
        row=spreadsheet.createRow(0);
        
        XSSFCell cellDate= row.createCell(0);
        cellDate.setCellValue("תאריך");
        XSSFCell cellGardenHead= row.createCell(1);
        cellGardenHead.setCellValue("\"שם גן/מרכז\"");
        XSSFCell cellDepHead= row.createCell(2);
        cellDepHead.setCellValue("מס מחלקה");
        XSSFCell cellDurationHead= row.createCell(3);
        cellDurationHead.setCellValue("תאריך ארך");
        XSSFCell cellProviderHead= row.createCell(4);
        cellProviderHead.setCellValue("ספק");
        XSSFCell cellCheckNumberHead= row.createCell(5);
        cellCheckNumberHead.setCellValue("מס חשבונית");
        XSSFCell cellAmountHead= row.createCell(6);
        cellAmountHead.setCellValue("סכום");
        XSSFCell cellTypeHead= row.createCell(7);
        cellTypeHead.setCellValue("סוג");
        XSSFCell cellDetailsHead= row.createCell(8);
        cellDetailsHead.setCellValue("פרוט");
        XSSFCell cellPaymentMethodHead= row.createCell(9);
        cellPaymentMethodHead.setCellValue("עמצעי תשלום");
        XSSFCell cellPaymentQuantityHead= row.createCell(10);
        cellPaymentQuantityHead.setCellValue("מס תשלומים");
        XSSFCell cellFirstPaymentDateHead= row.createCell(11);
        cellFirstPaymentDateHead.setCellValue("תאריך תשלום ראשון");
        
        row.createCell(12).setCellValue("תאריכים");
        row.createCell(13).setCellValue("שם מאשר");
        row.createCell(14).setCellValue("נמסר לה\"ח");
        row.createCell(15).setCellValue("שולם");
        row.createCell(16).setCellValue("תאריך");
        row.createCell(17).setCellValue("תאריך קבלה");
        row.createCell(18).setCellValue("תאריך שליחה");
        row.createCell(19).setCellValue("עבור");
        row.createCell(20).setCellValue("כתובת");
        row.createCell(21).setCellValue("סוג מסמך");
        row.createCell(22).setCellValue("אסמכתא פנימית");
        row.createCell(23).setCellValue("ע\"ס");
        row.createCell(24).setCellValue("שם המקבל");
        row.createCell(25).setCellValue("RR...IL");
        row.createCell(26).setCellValue("הערות");
        

        for (int i = 1; i < orders.size(); i++) {
             row=spreadsheet.createRow(i);
            Order order = orders.get(i);
            row.createCell(0).setCellValue(order.getCurrentDate()==null?null:order.getCurrentDate().toString());
            row.createCell(1).setCellValue(order.getGarden());
            row.createCell(2).setCellValue(order.getDepartmentNumber());
            row.createCell(3).setCellValue(order.getDurationNumber()==null?null:order.getDurationNumber().toString());
            row.createCell(4).setCellValue(order.getProvider());
            row.createCell(5).setCellValue(order.getCheckNumber());
            row.createCell(6).setCellValue(order.getAmount());
            row.createCell(7).setCellValue(order.getDocumentType());
            row.createCell(8).setCellValue(order.getDetail());
            row.createCell(9).setCellValue(order.getPaymentMethods());
            row.createCell(10).setCellValue(order.getPaymentQuantity());
            row.createCell(11).setCellValue(order.getFirstPaymentDate()==null?null:order.getFirstPaymentDate().toString());
            row.createCell(12).setCellValue(order.getPaymentDatesDetails());
            row.createCell(13).setCellValue(order.getConfirmsName());
            row.createCell(14).setCellValue(order.getDeliveredTo());
            row.createCell(15).setCellValue(order.getComplaintPay());
            row.createCell(16).setCellValue(order.getPayDate()==null?null:order.getPayDate().toString());
            row.createCell(17).setCellValue(order.getDateInCheck()==null?null:order.getDateInCheck().toString());
            row.createCell(18).setCellValue(order.getSendComplaintDate()==null?null:order.getSendComplaintDate().toString());
            row.createCell(19).setCellValue(order.getSendWorkerName());
            row.createCell(20).setCellValue(order.getAddress());
            row.createCell(21).setCellValue(order.getDocumentType());
            row.createCell(22).setCellValue(order.getInternalReference());
            row.createCell(23).setCellValue(order.getSocialWork());
            row.createCell(24).setCellValue(order.getClientName());
            row.createCell(25).setCellValue(order.getRrIL());
            row.createCell(26).setCellValue(order.getRemarks());


            try (FileOutputStream out = new FileOutputStream(
                    new File("D:\\upload\\report.xlsx"))){
                
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
