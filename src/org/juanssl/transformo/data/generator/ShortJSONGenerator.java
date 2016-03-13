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

import org.juanssl.transformo.data.RowData;
import org.juanssl.transformo.data.RowDataConverter;

/**
 *
 * @author Juan Silva
 */
public class ShortJSONGenerator extends BaseJSONGenerator {

    @Override
    protected String getJSON(RowData data) {
        return RowDataConverter.convertToShortJSON(data);
    }
}
