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
import java.util.Map;
import org.juanssl.transformo.app.Logger;

/**
 *
 * @author Juan
 */
public class RowData {
    
    private final Map<FieldMeta, Object> _data;
    
    /**
     * Initialize this instance
     */
    public RowData(){
        _data = new HashMap<>();
    }
    
    /**
     * Add Data to this Row Data.
     * @param meta
     * @param data 
     */
    public void AddData(FieldMeta meta, Object data){
        if(_data.containsKey(meta)){
            Logger.Error("Already contains key [%s]", meta);
            return;
        }
        
        FieldType type = meta.DataType;
        switch(type){
            case CSHARP_BOOL:
                _data.put(meta, DataConverter.ForceCastToBoolean(data));
                break;
            case CSHARP_SBYTE:
            case CSHARP_BYTE:
            case CSHARP_INT:
            case CSHARP_UINT:
            case CSHARP_SHORT:
            case CSHARP_USHORT:
            case CSHARP_LONG:
            case CSHARP_ULONG:
                _data.put(meta, DataConverter.ForceCastToLong(data));
                break;
            case CSHARP_FLOAT:
            case CSHARP_DOUBLE:
            case CSHARP_DECIMAL:
                _data.put(meta, DataConverter.ForceCastToDouble(data));
                break;
            case CSHARP_STRING:
            case CSHARP_CHAR:
                _data.put(meta, DataConverter.ForceCastToString(data));
                break;
                
            default:
                Logger.Error("Data Format Unknown [%s]", type);
                break;
        }
    }
    
    /**
     * Check if this instance already contains data for the given field.
     * @param meta
     * @return 
     */
    public boolean Contains(FieldMeta meta){
        return _data.containsKey(meta);
    }
    
    /**
     * Gets the boolean value for the given field.
     * @param meta
     * @return 
     */
    public boolean GetBoolean(FieldMeta meta){
        return (boolean)_data.get(meta);
    }
    
    /**
     * Gets the long value for the given field.
     * @param meta
     * @return 
     */
    public long GetLong(FieldMeta meta){
        return (long)_data.get(meta);
    }
    
    /**
     * Gets the Double value for the given field.
     * @param meta
     * @return 
     */
    public double GetDouble(FieldMeta meta){
        return (long)_data.get(meta);
    }
    
    /**
     * Gets the String value for the given field.
     * @param meta
     * @return 
     */
    public String GetString(FieldMeta meta){
        return (String)_data.get(meta);
    }
    
    /**
     * Gets the data value for the given field as a String regardless of the type.
     * @param meta
     * @return 
     */
    public String GetAsString(FieldMeta meta){
        return _data.get(meta).toString();
    }
    
    /**
     * Gets the full content data for this instance
     * @return 
     */
    public Map<FieldMeta, Object> GetFullData(){
        return _data;
    }
    
    @Override
    public String toString(){
        return RowDataConverter.ConvertToFullJSON(this);
    }
}
