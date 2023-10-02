package com.telegram.bot.schedule.parser;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellType;

public class Parser {
    public static String firstRovCol(Sheet sheet,int row, int col){
        for (int i = 0; i < sheet.getNumMergedRegions(); i++){
            if (sheet.getMergedRegion(i).isInRange(row,col)){
                col = sheet.getMergedRegion(i).getFirstColumn();
                row = sheet.getMergedRegion(i).getFirstRow();
                return sheet.getRow(row).getCell(col).getStringCellValue();
            }
        }
        return null;
    }
    public static void pars(int number){
        try {
            FileInputStream file = new FileInputStream("C:\\Users\\emper\\IdeaProjects\\-schedule\\src\\main\\resources\\Копия расп 51.xls");
            System.out.println("jrtq");
            HSSFWorkbook wb = new HSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(2); // так как лист находится в 3 вкладке
            int startRow = 1;
            int dateCol = 0;
            int timeCol = 1;
            // прогоняем 2 строку по столбцам и записываем их к себе, если она стринг
            // ВАЖНО БАГ: почему то ячейку AH2 возращает null, хотя AH1 есть, по этому -1
            for (int col = 2 ; col < sheet.getRow(startRow).getPhysicalNumberOfCells() -1 ; col++){
                if (sheet.getRow(startRow).getCell(col).getCellType() == CellType.STRING) {
                    // если столбец со временем, то переписываем переменную времени, иначе парсим группу
                    if ( sheet.getRow(startRow).getCell(col).getStringCellValue() == sheet.getRow(startRow).getCell(timeCol).getStringCellValue()) {
                        timeCol = col;
                    }
                    else {
                        String date;
                        String gruop = sheet.getRow(startRow).getCell(col).getStringCellValue();
                        String time;
                        String subj;
                        String teach;
                        // так как пока я не трогою столбец с датой, просто там можно делать проверку для занятий
                        // по этому пока что я буду парсить в низ на 40 строк вниз (5 дней) - субботу не трогаю
                        for (int row = startRow +1; row < sheet.getLastRowNum(); row = row +2){
                            // если есть данные в табличке дата
                            if (firstRovCol(sheet,row+1,dateCol) != null){
                                // проверка есть ли занятие
                                if ((sheet.getRow(row).getCell(col).getCellType() != CellType.BLANK) || (sheet.getRow(row+1).getCell(col).getCellType() != CellType.BLANK)){
                                    // время тут записано либо объединенной ячейки, либо в первой
                                    // но это не мешает, так как в любом случа надо брать пурвую
                                    date = firstRovCol(sheet,row+1,dateCol);
                                    time = sheet.getRow(row).getCell(timeCol).getStringCellValue();
                                    subj = sheet.getRow(row+1).getCell(col).getStringCellValue();
                                    teach = sheet.getRow(row).getCell(col).getStringCellValue();
                                    System.out.println(date + "  " + gruop+ "   "+time + "   " +subj+"   "+ teach);
                                } else if (sheet.getRow(row+1).getCell(col).getCellType() == CellType.BLANK) {
                                    if (firstRovCol(sheet,row+1,col) != null){
                                        // время тут записано либо объединенной ячейки, либо в первой
                                        // но это не мешает, так как в любом случа надо брать пурвую
                                        date = firstRovCol(sheet,row+1,dateCol);
                                        time = sheet.getRow(row).getCell(timeCol).getStringCellValue();
                                        subj = firstRovCol(sheet,row+1,col) ;
                                        System.out.println(date + "  " +  gruop+ "   "+time + "   " +subj);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    System.out.println("ОШИБКА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
