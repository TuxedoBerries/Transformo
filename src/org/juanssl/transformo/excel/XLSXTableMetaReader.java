/*
 * Copyright (C) 2015 Juan Silva <juanssl@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.juanssl.transformo.excel;

import org.juanssl.transformo.data.TableMeta;
import org.juanssl.transformo.data.FieldMeta;
import org.juanssl.transformo.data.FieldTypeTranslations;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.juanssl.transformo.app.Logger;

/**
 *
 * 
 * @author Juan
 */
public class XLSXTableMetaReader extends BaseXLSXReader {
    
    public static final int ROW_META_COUNT = 2;
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
            Logger.Warning("Total number of rows less than expected [%d]. %d Rows are expected", totalRows, ROW_META_COUNT);
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
                Logger.Error("Bad Cell Type [%s]. String is expected.", cell.getCellType());
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
                Logger.Error("Bad Cell Type [%s]. String is expected.", cell.getCellType());
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
                Logger.Error("Bad Cell Type [%s]. String is expected.", cell.getCellType());
            }
        }
    }
    
    private void SetDataType(Cell cell, FieldMeta fmeta){
        if(cell.getCellType() != Cell.CELL_TYPE_STRING){
            Logger.Error("Bad Cell Type [%s]. String is expected.", cell.getCellType());
            return;
        }
        
        String value = cell.getStringCellValue();
        value = value.trim();
        fmeta.DataType = FieldTypeTranslations.GetType(value);
    }
}
