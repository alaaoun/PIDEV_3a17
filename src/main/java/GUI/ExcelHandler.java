package GUI;
import javafx.collections.ObservableList;
import models.trotinette;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.trotinetteservice;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import models.trotinette;

public class ExcelHandler {
    trotinetteservice trotinetteservice=new trotinetteservice();
    // Méthode pour lire un fichier Excel
    public void readExcelFile(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

        // Iterate over rows
        for (Row row : sheet) {
            // Iterate over cells
            for (Cell cell : row) {
                System.out.print(cell.toString() + "\t");
            }
            System.out.println();
        }

        workbook.close();
        fileInputStream.close();
    }

    // Méthode pour écrire dans un fichier Excel
    public void writeExcelFile(String filePath) throws IOException {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Trotinettes");

            // Création de l'en-tête
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Vitesse", "Charge", "Couleur", "Disponibilité", "ID de la Station"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                // Appliquer le style au titre
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            trotinetteservice trotinetteservice = new trotinetteservice();
            // Remplissage des données
            List<trotinette> trotinettes = trotinetteservice.recuperer();
            for (int i = 0; i < trotinettes.size(); i++) {
                trotinette trotinette = trotinettes.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(trotinette.getId_trotinette());
                row.createCell(1).setCellValue(trotinette.getVitesse());
                row.createCell(2).setCellValue(trotinette.getCharge());
                row.createCell(3).setCellValue(trotinette.getCouleur());
                row.createCell(4).setCellValue(trotinette.getDispo());
                row.createCell(5).setCellValue(trotinette.getId_station());
            }

            // Redimensionner automatiquement les colonnes pour s'adapter au contenu
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Sauvegarde du fichier Excel
            FileOutputStream fileOut = new FileOutputStream("trotinettes.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }}

    // Vos autres méthodes ici



