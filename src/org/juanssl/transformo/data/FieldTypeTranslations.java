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
package org.juanssl.transformo.data;

import java.util.HashMap;
import org.juanssl.transformo.app.Logger;

/**
 *
 * @author Juan Silva
 */
public class FieldTypeTranslations {

    private static final HashMap<String, FieldType> _cSharpTranslation = new HashMap<>();
    private static final HashMap<FieldType, String> _cSharpTranslationBack = new HashMap<>();

    static {
        // C# Translations from String
        _cSharpTranslation.put(AllowedFieldType.BYTE, FieldType.CSHARP_BYTE);
        _cSharpTranslation.put(AllowedFieldType.SBYTE, FieldType.CSHARP_SBYTE);
        _cSharpTranslation.put(AllowedFieldType.INT, FieldType.CSHARP_INT);
        _cSharpTranslation.put(AllowedFieldType.UINT, FieldType.CSHARP_UINT);
        _cSharpTranslation.put(AllowedFieldType.SHORT, FieldType.CSHARP_SHORT);
        _cSharpTranslation.put(AllowedFieldType.USHORT, FieldType.CSHARP_USHORT);
        _cSharpTranslation.put(AllowedFieldType.LONG, FieldType.CSHARP_LONG);
        _cSharpTranslation.put(AllowedFieldType.ULONG, FieldType.CSHARP_ULONG);
        _cSharpTranslation.put(AllowedFieldType.FLOAT, FieldType.CSHARP_FLOAT);
        _cSharpTranslation.put(AllowedFieldType.DOUBLE, FieldType.CSHARP_DOUBLE);
        _cSharpTranslation.put(AllowedFieldType.CHAR, FieldType.CSHARP_CHAR);
        _cSharpTranslation.put(AllowedFieldType.BOOL, FieldType.CSHARP_BOOL);
        _cSharpTranslation.put(AllowedFieldType.STRING, FieldType.CSHARP_STRING);
        _cSharpTranslation.put(AllowedFieldType.DECIMAL, FieldType.CSHARP_DECIMAL);

        // C# Translations from Type
        for (String name : _cSharpTranslation.keySet()) {
            _cSharpTranslationBack.put(_cSharpTranslation.get(name), name);
        }
    }

    public static boolean isNumeric(FieldType type) {
        switch (type) {
            case CSHARP_BYTE:
            case CSHARP_SBYTE:
            case CSHARP_SHORT:
            case CSHARP_USHORT:
            case CSHARP_INT:
            case CSHARP_UINT:
            case CSHARP_LONG:
            case CSHARP_ULONG:
            case CSHARP_FLOAT:
            case CSHARP_DOUBLE:
            case CSHARP_DECIMAL:
                return true;
        }

        return false;
    }

    public static boolean isText(FieldType type) {
        switch (type) {
            case CSHARP_CHAR:
            case CSHARP_STRING:
                return true;
        }

        return false;
    }

    public static boolean isBoolean(FieldType type) {
        switch (type) {
            case CSHARP_BOOL:
                return true;
        }

        return false;
    }

    public static String getType(FieldType type) {
        if (_cSharpTranslationBack.containsKey(type)) {
            return _cSharpTranslationBack.get(type);
        }

        return "";
    }

    public static boolean existType(String type) {
        return _cSharpTranslation.containsKey(type);
    }

    public static FieldType getType(String type) {
        if (_cSharpTranslation.containsKey(type)) {
            return _cSharpTranslation.get(type);
        }

        Logger.Warning("Returning NONE for type [%s]", type);
        return FieldType.NONE;
    }
}
