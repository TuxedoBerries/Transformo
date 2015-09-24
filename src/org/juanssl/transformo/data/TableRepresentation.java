/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juanssl.transformo.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan
 */
public class TableRepresentation {
    
    public TableMeta Meta;
    
    public List<RowData> Data;
    
    public TableRepresentation(){
        Data = new ArrayList<>();
    }
}
