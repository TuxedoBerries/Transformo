/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasegenerator;

import com.company.codegenerator.data.FieldMeta;
import com.company.codegenerator.data.TableMeta;
import com.company.codegenerator.excel.XLSXTableMetaReader;
import com.company.codegenerator.generator.EntityGenerator;
import com.company.codegenerator.generator.FieldGenerator;
import com.company.codegenerator.template.TemplateReader;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class DatabaseGenerator {

    private static final Logger logger = Logger.getLogger("DatabaseGenerator");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String path = "Database.xlsx";
        
        XLSXTableMetaReader reader = new XLSXTableMetaReader(path);
        reader.Read();
        List<TableMeta> tables = reader.GetTables();
        TemplateReader templateReader = new TemplateReader("template.txt");
        TemplateReader interfaceTemplateReader = new TemplateReader("interface.txt");
        String template = templateReader.ReadTemplate();
        String interfaceTemplate = interfaceTemplateReader.ReadTemplate();
        for(int i=0; i<tables.size(); ++i){
            logger.info(tables.get(i).toString());
            TableMeta tmeta = tables.get(i);
            EntityGenerator generator = new EntityGenerator(tmeta);
            generator.Generate(template);
            for(FieldMeta fmeta : tmeta.Fields){
                FieldGenerator igenerator = new FieldGenerator(fmeta);
                igenerator.Generate(interfaceTemplate);
            }
        }
    }
}
