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
package org.juanssl.transformo.data.generator;

import java.util.List;
import org.juanssl.transformo.data.RowData;
import org.juanssl.transformo.data.TableMeta;

/**
 *
 * @author Juan Silva
 */
public class DataPack {

    // Table For this Data
    public TableMeta Meta;

    // Data of the Table
    List<RowData> Data;

    public DataPack() {
        Meta = null;
        Data = null;
    }

    public DataPack(TableMeta meta) {
        Meta = meta;
        Data = null;
    }

    public DataPack(List<RowData> data) {
        Meta = null;
        Data = data;
    }

    public DataPack(TableMeta meta, List<RowData> data) {
        Meta = meta;
        Data = data;
    }

    /**
     * Removes all the empty rows in the Data list
     */
    public void cleanData() {
        for (int i = Data.size() - 1; i >= 0; --i) {
            RowData data = Data.get(i);
            if (data.getDataCount() <= 0) {
                Data.remove(i);
            }
        }
    }
}
