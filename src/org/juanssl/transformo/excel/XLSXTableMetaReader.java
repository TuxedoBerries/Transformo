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
 * @author Juan Silva
 */
public class XLSXTableMetaReader extends BaseXLSXReader {

    public static final int ROW_META_COUNT = 2;
    private final List<TableMeta> _tableMetas;

    public XLSXTableMetaReader() {
        _filePath = "";
        _tableMetas = new ArrayList<>();
    }

    public XLSXTableMetaReader(String path) {
        _filePath = path;
        _tableMetas = new ArrayList<>();
    }

    public void setFilePath(String path) {
        _filePath = path;
    }

    @Override
    protected void doRead() {
        _tableMetas.clear();
        if (_workBook == null) {
            Logger.Error("Work Book is null");
            return;
        }

        int totalCount = _workBook.getNumberOfSheets();
        for (int i = 0; i < totalCount; ++i) {
            TableMeta meta = generateTableMeta(_workBook.getSheetAt(i));
            if (meta == null) {
                continue;
            }

            if (tableMetaAlreadyInList(meta)) {
                continue;
            }

            _tableMetas.add(meta);
        }
    }

    private boolean tableMetaAlreadyInList(TableMeta meta) {
        for (int i = 0; i < _tableMetas.size(); ++i) {
            TableMeta currentMeta = _tableMetas.get(i);
            if (TableMeta.Equals(meta, currentMeta)) {
                return true;
            }
        }

        return false;
    }

    public List<TableMeta> getTables() {
        return _tableMetas;
    }

    private TableMeta generateTableMeta(XSSFSheet sheet) {
        int totalRows = sheet.getLastRowNum();
        if (totalRows < ROW_META_COUNT) {
            Logger.Warning("Total number of rows less than expected [%d]. %d Rows are expected", totalRows, ROW_META_COUNT);
            return null;
        }

        TableMeta meta = new TableMeta();
        meta.TableName = sheet.getSheetName();

        generateTypes(sheet.getRow(0), meta);
        generateShortNames(sheet.getRow(1), meta);
        generateNames(sheet.getRow(2), meta);

        return meta;
    }

    private void generateNames(Row row, TableMeta tmeta) {
        int total = row.getLastCellNum();
        for (int i = 0; i < total; ++i) {
            FieldMeta fmeta = tmeta.GetOrCreateField(i);
            Cell cell = row.getCell(i);
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                String name = cell.getStringCellValue();
                // Clean
                name = name.trim();
                if (name.endsWith("@")) {
                    name = name.replace("@", "");
                    fmeta.IsKey = true;
                }

                fmeta.FieldName = name;
            } else {
                Logger.Error("Bad Cell Type [%s]. String is expected.", cell.getCellType());
            }
        }
    }

    private void generateShortNames(Row row, TableMeta tmeta) {
        int total = row.getLastCellNum();
        for (int i = 0; i < total; ++i) {
            FieldMeta fmeta = tmeta.GetOrCreateField(i);
            Cell cell = row.getCell(i);
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                fmeta.FieldShortName = cell.getStringCellValue();
            } else {
                Logger.Error("Bad Cell Type [%s]. String is expected.", cell.getCellType());
            }
        }
    }

    private void generateTypes(Row row, TableMeta tmeta) {
        int total = row.getLastCellNum();
        for (int i = 0; i < total; ++i) {
            FieldMeta fmeta = tmeta.GetOrCreateField(i);
            fmeta.FieldIndex = i;
            Cell cell = row.getCell(i);
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                setDataType(cell, fmeta);
            } else {
                Logger.Error("Bad Cell Type [%s]. String is expected.", cell.getCellType());
            }
        }
    }

    private void setDataType(Cell cell, FieldMeta fmeta) {
        if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
            Logger.Error("Bad Cell Type [%s]. String is expected.", cell.getCellType());
            return;
        }

        String value = cell.getStringCellValue();
        value = value.trim();
        fmeta.DataType = FieldTypeTranslations.getType(value);
    }
}
