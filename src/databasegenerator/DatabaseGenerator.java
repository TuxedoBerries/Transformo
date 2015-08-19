/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasegenerator;

import com.company.codegenerator.TableMeta;
import com.company.codegenerator.excel.XLSXTableMetaReader;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Juan
 */
public class DatabaseGenerator {

    private static final Logger logger = LogManager.getLogger(DatabaseGenerator.class);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String path = "Database.xlsx";
        
        XLSXTableMetaReader reader = new XLSXTableMetaReader(path);
        reader.Read();
        List<TableMeta> tables = reader.GetTables();
        for(int i=0; i<tables.size(); ++i){
            logger.error(tables.get(i).toString());
        }
        
    }
    
}
