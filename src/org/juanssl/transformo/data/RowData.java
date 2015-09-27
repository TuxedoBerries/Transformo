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
    
    public RowData(){
        _data = new HashMap<>();
    }
    
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
    
    public boolean Contains(FieldMeta meta){
        return _data.containsKey(meta);
    }
    
    public boolean GetBoolean(FieldMeta meta){
        return (boolean)_data.get(meta);
    }
    
    public long GetLong(FieldMeta meta){
        return (long)_data.get(meta);
    }
    
    public double GetDouble(FieldMeta meta){
        return (long)_data.get(meta);
    }
    
    public String GetString(FieldMeta meta){
        return (String)_data.get(meta);
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        
        builder.append("{");
        int total = _data.size();
        int count = 0;
        for(FieldMeta meta : _data.keySet()){
            builder.append("\"");
            builder.append(meta.FieldName);
            builder.append("\"");
            builder.append(":");
            
            if(FieldTypeTranslations.isNumeric(meta.DataType)){
                builder.append( _data.get(meta) );
            }
            if(FieldTypeTranslations.isText(meta.DataType)){
                builder.append("\"");
                builder.append( _data.get(meta) );
                builder.append("\"");
            }
            if(FieldTypeTranslations.isBoolean(meta.DataType)){
                builder.append( _data.get(meta) );
            }
            
            if(count + 1 < total)
                builder.append(",");
            count++;
        }
        builder.append("}");
        
        return builder.toString();
    }
}
