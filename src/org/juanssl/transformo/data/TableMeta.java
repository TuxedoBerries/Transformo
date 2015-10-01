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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan
 */
public class TableMeta {
    
    /**
     * Name of the Table
     */
    public String TableName;
    
    /**
     * List of Fields
     */
    public List<FieldMeta> Fields;
    
    /**
     * Empty Constructor
     */
    public TableMeta(){
        Fields = new ArrayList<>();
    }
    
    /**
     * Gets or create the specific element at the given index
     * @param index
     * @return 
     */
    public FieldMeta GetOrCreateField(int index){
        AddFieldsUntil(index);
        return Fields.get(index);
    }
    
    /**
     * Add Fields until the given index
     * @param index 
     */
    private void AddFieldsUntil(int index){
        while(Fields.size() < index+1){
            Fields.add(new FieldMeta());
        }
    }
    
    /**
     * Check if two TableMeta instances are equals
     * @param meta1
     * @param meta2
     * @return 
     */
    public static boolean Equals(TableMeta meta1, TableMeta meta2){
        if(meta1 == meta2)
            return true;
        
        // Check Null
        if(meta1 == null && meta2 == null)
            return true;
        if(meta1 == null)
            return false;
        if(meta2 == null)
            return false;
            
        // Check Name
        if(!meta1.TableName.equals(meta2.TableName))
            return false;
        // Check Fields Size
        if(meta1.Fields.size() != meta2.Fields.size())
            return false;
        // Check Same Fields
        int total = meta1.Fields.size();
        for(int t1index=0; t1index<total; ++t1index){
            FieldMeta field1 = meta1.Fields.get(t1index);
            boolean found = false;
            
            // Check First Field inside Meta 2 Fields
            for(int t2index=0; t2index<total; ++t2index){
                FieldMeta field2 = meta2.Fields.get(t2index);
                if(FieldMeta.Equals(field1, field2)){
                    found = true;
                    break;
                }
            }
            
            if(!found)
                return false;
        }
        
        return true;
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"");
        builder.append("TableName");
        builder.append("\"");
        builder.append(":");
        builder.append("\"");
        builder.append(TableName);
        builder.append("\"");
        builder.append(",");
        builder.append("\"");
        builder.append("Fields");
        builder.append("\"");
        builder.append(":");
        builder.append("[");
        for(int i=0; i<Fields.size(); ++i){
            builder.append(Fields.get(i).toString());
            if(i+1 < Fields.size()){
                builder.append(",");
            }
        }
        builder.append("]");
        builder.append("}");
        
        return builder.toString();
    }
}
