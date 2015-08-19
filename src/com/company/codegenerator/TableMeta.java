/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator;

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
    
    public FieldMeta GetOrCreateField(int index){
        AddFieldsUntil(index);
        return Fields.get(index);
    }
    
    private void AddFieldsUntil(int index){
        while(Fields.size() < index+1){
            Fields.add(new FieldMeta());
        }
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("TableName:");
        builder.append(TableName);
        builder.append("TableName:");
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
