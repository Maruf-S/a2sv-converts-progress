package com.example.maruf.a2svc.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SheetsServiceUtil {
    public List<String> getUsers() throws IOException, URISyntaxException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("static/new.xlsx");
//        FileInputStream file = new FileInputStream(stream);
        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(stream);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheet("Info");
        //Iterate through each row's one by one
        Iterator<Row> rowIterator = sheet.iterator();
        List<String> players = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(16);
            if (cell == null) {
            continue;
            }
            Hyperlink hyperlink = cell.getHyperlink();
            if (hyperlink != null) {
//                    System.out.println(hyperlink.getAddress());
                URI uri = new URI(hyperlink.getAddress());
                String[] segments = uri.getPath().split("/");
                String username = segments[segments.length - 1];
                    System.out.println(username);
                players.add(username);
            }
        }
//        file.close();

        return players;
    }
}