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
package org.tuxedoberries.transformo.data;

import java.util.Map;
import org.tuxedoberries.transformo.code.generator.Modifiers;

/**
 *
 * @author Juan Silva
 */
public class RowDataConverter {

    /**
     * Convert the given Row Data to a Full JSON Object
     *
     * @param data
     * @return
     */
    public static String convertToFullJSON(RowData data) {
        Map<FieldMeta, Object> map = data.getFullData();
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        int total = map.size();
        int count = 0;
        for (FieldMeta meta : map.keySet()) {
            builder.append("\"");
            builder.append(Modifiers.toSneakCase(meta.FieldName));
            builder.append("\"");
            builder.append(":");

            if (FieldTypeTranslations.isNumeric(meta.DataType)) {
                builder.append(map.get(meta));
            }
            if (FieldTypeTranslations.isText(meta.DataType)) {
                builder.append("\"");
                builder.append(map.get(meta));
                builder.append("\"");
            }
            if (FieldTypeTranslations.isBoolean(meta.DataType)) {
                builder.append(map.get(meta));
            }

            if (count + 1 < total) {
                builder.append(",");
            }
            count++;
        }
        builder.append("}");

        return builder.toString();
    }

    /**
     * Convert the given RowData into a Short Name JSON Object
     *
     * @param data
     * @return
     */
    public static String convertToShortJSON(RowData data) {
        Map<FieldMeta, Object> map = data.getFullData();
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        int total = map.size();
        int count = 0;
        for (FieldMeta meta : map.keySet()) {
            builder.append("\"");
            builder.append(Modifiers.toSneakCase(meta.FieldShortName));
            builder.append("\"");
            builder.append(":");

            if (FieldTypeTranslations.isNumeric(meta.DataType)) {
                builder.append(map.get(meta));
            }
            if (FieldTypeTranslations.isText(meta.DataType)) {
                builder.append("\"");
                builder.append(map.get(meta));
                builder.append("\"");
            }
            if (FieldTypeTranslations.isBoolean(meta.DataType)) {
                builder.append(map.get(meta));
            }

            if (count + 1 < total) {
                builder.append(",");
            }
            count++;
        }
        builder.append("}");

        return builder.toString();
    }
}
