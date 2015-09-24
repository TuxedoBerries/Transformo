/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
