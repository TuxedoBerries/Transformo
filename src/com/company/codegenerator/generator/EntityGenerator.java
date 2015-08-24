/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator.generator;

import com.company.codegenerator.data.TableMeta;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class EntityGenerator extends BaseGenerator {
    private static final Logger logger = Logger.getLogger("IdentityGenerator");
    
    private TableMeta _meta;
    
    public EntityGenerator(TableMeta tmeta){
        super();
        _meta = tmeta;
    }
    
    @Override
    public String Generate(String template){
        String result = template;
        result = GenerateFile(result, _meta);
        
        logger.info(result);
        return result;
    }
}
