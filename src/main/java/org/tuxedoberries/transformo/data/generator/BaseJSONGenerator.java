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
package org.tuxedoberries.transformo.data.generator;

import org.tuxedoberries.transformo.code.generator.Modifiers;
import org.tuxedoberries.transformo.data.RowData;

/**
 *
 * @author Juan Silva
 */
public abstract class BaseJSONGenerator extends BaseGenerator {
    
    private StringBuilder _builder;

    @Override
    public void generate() {
        _builder = new StringBuilder();

        _builder.append("{");
        for (int i = 0; i < _data.size(); ++i) {
            DataPack pack = _data.get(i);
            pack.cleanData();

            if (pack.Meta.HasKeyField()) {
                AddObjectData(pack);
            } else {
                AddArrayData(pack);
            }

            if (i + 1 < _data.size()) {
                _builder.append(",");
            }
        }
        _builder.append("}");
    }

    private void AddArrayData(DataPack pack) {
        _builder.append("\"");
        _builder.append(Modifiers.toSneakCase(pack.Meta.TableName));
        _builder.append("\":");
        _builder.append("[");

        for (int d = 0; d < pack.Data.size(); ++d) {
            RowData row = pack.Data.get(d);
            _builder.append(getJSON(row));

            if (d + 1 < pack.Data.size()) {
                _builder.append(",");
            }
        }
        _builder.append("]");
    }

    private void AddObjectData(DataPack pack) {
        _builder.append("\"");
        _builder.append(Modifiers.toSneakCase(pack.Meta.TableName));
        _builder.append("\":");
        _builder.append("{");

        for (int d = 0; d < pack.Data.size(); ++d) {
            RowData row = pack.Data.get(d);
            _builder.append("\"");
            _builder.append(row.getAsString(pack.Meta.GetKeyField()));
            _builder.append("\":");
            _builder.append(getJSON(row));

            if (d + 1 < pack.Data.size()) {
                _builder.append(",");
            }
        }
        _builder.append("}");
    }
    
    protected abstract String getJSON (RowData data);

    @Override
    public String getData() {
        return _builder.toString();
    }
}
