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

/**
 *
 * @author Juan
 */
public class FieldMeta {
    
    /**
     * Name of the Field
     */
    public String FieldName;
    
    /**
     * Short Name of the Field
     */
    public String FieldShortName;
    
    /**
     * Data Type of the Field
     */
    public FieldType DataType;
    
    /**
     * Index of the Field
     */
    public int FieldIndex;
    
    /**
     * Initialize this instance
     */
    public FieldMeta(){
        FieldName = "";
        FieldShortName = "";
        DataType = FieldType.NONE;
    }
    
    /**
     * Compares two FieldMeta classes in order to check
     * the equivalence between the given two.
     * @param meta1
     * @param meta2
     * @return 
     */
    public static boolean Equals(FieldMeta meta1, FieldMeta meta2){
        if(meta1 == null && meta2 == null)
            return true;
        
        if(meta1 == null || meta2 == null)
            return false;
        
        if(!meta1.FieldName.equals(meta2.FieldName))
            return false;
        
        if(!meta1.FieldShortName.equals(meta2.FieldShortName))
            return false;
        
        if(meta1.DataType != meta2.DataType)
            return false;
        
        return true;
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"");
        builder.append("FieldName");
        builder.append("\"");
        builder.append(":");
        builder.append("\"");
        builder.append(FieldName);
        builder.append("\"");
        builder.append(",");
        builder.append("\"");
        builder.append("FieldShortName");
        builder.append("\"");
        builder.append(":");
        builder.append("\"");
        builder.append(FieldShortName);
        builder.append("\"");
        builder.append(",");
        builder.append("\"");
        builder.append("DataType");
        builder.append("\"");
        builder.append(":");
        builder.append("\"");
        builder.append(DataType.toString());
        builder.append("\"");
        builder.append("}");
        
        return builder.toString();
    }
}
