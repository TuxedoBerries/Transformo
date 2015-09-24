/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juanssl.transformo.excel;

import org.juanssl.transformo.data.FieldMeta;
import org.juanssl.transformo.data.RowData;
import org.juanssl.transformo.data.TableMeta;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author Juan
 */
public class XLSXDataReader extends BaseXLSXReader {
    private static final Logger logger = Logger.getLogger("XLSXDataReader");
    
    private TableMeta _tmeta;
    private List<RowData> _data;
    
    public XLSXDataReader(){
        _filePath = "";
        _tmeta = null;
        _data = new ArrayList<>();
    }
    
    public XLSXDataReader(String path){
        _filePath = path;
        _tmeta = null;
        _data = new ArrayList<>();
    }
    
    public XLSXDataReader(String path, TableMeta table){
        _filePath = path;
        _tmeta = table;
        _data = new ArrayList<>();
    }
    
    public void SetFilePath(String path){
        _filePath = path;
    }
    
    public void SetTable(TableMeta table){
        _tmeta = table;
    }
    
    @Override
    protected void doRead() {
        _data.clear();
        if(_tmeta == null){
            logger.severe("Table is not set");
            return;
        }
        
        if(_tmeta.TableName.isEmpty()){
            logger.severe("Table Name is not set");
            return;
        }
        
        XSSFSheet sheet = _workBook.getSheet(_tmeta.TableName);
        int totalRows = sheet.getLastRowNum();
        if(totalRows < XLSXTableMetaReader.ROW_META_COUNT){
            logger.warning(String.format("Total number of rows less than expected [%d]. %d Rows are expected", totalRows, XLSXTableMetaReader.ROW_META_COUNT));
            return;
        }
        
        for(int i=3; i<=totalRows; ++i){
            logger.warning(String.format("Scanning Row [%s][%d].", _tmeta.TableName, i));
            RowData rdata = GenerateData(sheet.getRow(i));
            _data.add(rdata);
        }
    }
    
    public List<RowData> GetData(){
        return _data;
    }
    
    private RowData GenerateData(Row row){
        int total = row.getLastCellNum();
        RowData rdata = new RowData();
        
        for(int i=0; i<total; ++i){
            FieldMeta fmeta = _tmeta.GetOrCreateField(i);
            Cell cell = row.getCell(i);
            
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:
                    rdata.AddData(fmeta, cell.getNumericCellValue());
                    break;
                    
                case Cell.CELL_TYPE_STRING:
                    rdata.AddData(fmeta, cell.getStringCellValue());
                    break;
                    
                case Cell.CELL_TYPE_BOOLEAN:
                    rdata.AddData(fmeta, cell.getBooleanCellValue());
                    break;
                    
                case Cell.CELL_TYPE_BLANK:
                    logger.warning(String.format("Empty Data Field. Expected a [%s]", fmeta.DataType));
                    break;
                    
                case Cell.CELL_TYPE_FORMULA:
                    logger.warning("Data Type is not supported [FORMULA]");
                    break;
                    
                case Cell.CELL_TYPE_ERROR:
                    logger.severe("Error Reading Cell");
                    break;
            }
        }
        
        return rdata;
    }
}
