/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator.excel;

import com.company.codegenerator.data.TableMeta;
import com.company.codegenerator.data.FieldMeta;
import com.company.codegenerator.data.FieldTypeTranslations;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * 
 * @author Juan
 */
public class XLSXTableMetaReader extends BaseXLSXReader {
    
    public static final int ROW_META_COUNT = 2;
    private static final Logger logger = Logger.getLogger("XLSXTableMetaReader");
    private final List<TableMeta> _tableMetas;
    
    public XLSXTableMetaReader(){
        _filePath = "";
        _tableMetas = new ArrayList<>();
    }
    
    public XLSXTableMetaReader(String path){
        _filePath = path;
        _tableMetas = new ArrayList<>();
    }
    
    public void SetFilePath(String path){
        _filePath = path;
    }
    
    @Override
    protected void doRead() {
        _tableMetas.clear();
        
        int totalCount = _workBook.getNumberOfSheets();
        for(int i=0; i<totalCount; ++i){
            TableMeta meta = GenerateTableMeta( _workBook.getSheetAt(i) );
            if(meta == null){
                continue;
            }
            _tableMetas.add(meta);
        }
    }
    
    public List<TableMeta> GetTables(){
        return _tableMetas;
    }
    
    private TableMeta GenerateTableMeta(XSSFSheet sheet){
        int totalRows = sheet.getLastRowNum();
        if(totalRows < ROW_META_COUNT){
            logger.warning(String.format("Total number of rows less than expected [%d]. %d Rows are expected", totalRows, ROW_META_COUNT));
            return null;
        }
        
        TableMeta meta = new TableMeta();
        meta.TableName = sheet.getSheetName();
        
        GenerateTypes(sheet.getRow(0), meta);
        GenerateShortNames(sheet.getRow(1), meta);
        GenerateNames(sheet.getRow(2), meta);
        
        return meta;
    }
    
    private void GenerateNames(Row row, TableMeta tmeta){
        int total = row.getLastCellNum();
        for(int i=0; i<total; ++i){
            FieldMeta fmeta = tmeta.GetOrCreateField(i);
            Cell cell = row.getCell(i);
            if(cell.getCellType()== Cell.CELL_TYPE_STRING){
                fmeta.FieldName = cell.getStringCellValue();
            }else{
                logger.severe(String.format("Bad Cell Type [%s]. String is expected.", cell.getCellType()));
            }
        }
    }
    
    private void GenerateShortNames(Row row, TableMeta tmeta){
        int total = row.getLastCellNum();
        for(int i=0; i<total; ++i){
            FieldMeta fmeta = tmeta.GetOrCreateField(i);
            Cell cell = row.getCell(i);
            if(cell.getCellType()== Cell.CELL_TYPE_STRING){
                fmeta.FieldShortName = cell.getStringCellValue();
            }else{
                logger.severe(String.format("Bad Cell Type [%s]. String is expected.", cell.getCellType()));
            }
        }
    }
    
    private void GenerateTypes(Row row, TableMeta tmeta){
        int total = row.getLastCellNum();
        for(int i=0; i<total; ++i){
            FieldMeta fmeta = tmeta.GetOrCreateField(i);
            fmeta.FieldIndex = i;
            Cell cell = row.getCell(i);
            if(cell.getCellType()== Cell.CELL_TYPE_STRING){
                SetDataType(cell, fmeta);
            }else{
                logger.severe(String.format("Bad Cell Type [%s]. String is expected.", cell.getCellType()));
            }
        }
    }
    
    private void SetDataType(Cell cell, FieldMeta fmeta){
        if(cell.getCellType() != Cell.CELL_TYPE_STRING){
            logger.severe(String.format("Bad Cell Type [%s]. String is expected.", cell.getCellType()));
            return;
        }
        
        String value = cell.getStringCellValue();
        value = value.trim();
        fmeta.DataType = FieldTypeTranslations.GetType(value);
    }
}
