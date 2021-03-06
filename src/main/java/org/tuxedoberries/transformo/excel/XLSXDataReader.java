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
package org.tuxedoberries.transformo.excel;

import org.tuxedoberries.transformo.data.FieldMeta;
import org.tuxedoberries.transformo.data.RowData;
import org.tuxedoberries.transformo.data.TableMeta;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.tuxedoberries.transformo.app.Logger;

/**
 *
 * @author Juan Silva
 */
public class XLSXDataReader extends BaseXLSXReader {

    private TableMeta _tmeta;
    private List<RowData> _data;

    public XLSXDataReader() {
        _filePath = "";
        _tmeta = null;
    }

    public XLSXDataReader(String path) {
        _filePath = path;
        _tmeta = null;
    }

    public XLSXDataReader(String path, TableMeta table) {
        _filePath = path;
        _tmeta = table;
    }

    public void setFilePath(String path) {
        _filePath = path;
    }

    public void setTable(TableMeta table) {
        _tmeta = table;
    }

    @Override
    protected void doRead() {
        _data = new ArrayList<RowData>();
        if (_tmeta == null) {
            Logger.Error("Table is not set");
            return;
        }
        if (_tmeta.TableName == null) {
            Logger.Error("Table Name is null");
            return;
        }

        if (_tmeta.TableName.isEmpty()) {
            Logger.Error("Table Name is not set");
            return;
        }

        if (_workBook == null) {
            Logger.Error("Work Book is null");
            return;
        }

        XSSFSheet sheet = _workBook.getSheet(_tmeta.TableName);
        if (sheet == null) {
            Logger.Error("Null sheet %s", _tmeta.TableName);
            return;
        }

        int totalRows = sheet.getLastRowNum();
        if (totalRows < XLSXTableMetaReader.ROW_META_COUNT) {
            Logger.Warning("Total number of rows less than expected [%d]. %d Rows are expected", totalRows, XLSXTableMetaReader.ROW_META_COUNT);
            return;
        }

        for (int i = 3; i <= totalRows; ++i) {
            RowData rdata = generateData(sheet.getRow(i));
            if (rdata != null) {
                _data.add(rdata);
            }
        }
    }

    public List<RowData> getData() {
        return _data;
    }

    private RowData generateData(Row row) {
        if (row == null) {
            Logger.Error("Row is null");
            return null;
        }

        int total = row.getLastCellNum();
        RowData rdata = new RowData();

        for (int i = 0; i < total; ++i) {
            FieldMeta fmeta = _tmeta.GetOrCreateField(i);
            Cell cell = row.getCell(i);
            if (cell == null) {
                continue;
            }

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    rdata.addData(fmeta, cell.getNumericCellValue());
                    break;

                case Cell.CELL_TYPE_STRING:
                    rdata.addData(fmeta, cell.getStringCellValue());
                    break;

                case Cell.CELL_TYPE_BOOLEAN:
                    rdata.addData(fmeta, cell.getBooleanCellValue());
                    break;

                case Cell.CELL_TYPE_BLANK:
                    Logger.Warning("Empty Data Field. Expected a [%s]", fmeta.DataType);
                    break;

                case Cell.CELL_TYPE_FORMULA:
                    Logger.Warning("Data Type is not supported [FORMULA]");
                    break;

                case Cell.CELL_TYPE_ERROR:
                    Logger.Error("Error Reading Cell");
                    break;
            }
        }

        return rdata;
    }
}
