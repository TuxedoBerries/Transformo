/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juanssl.transformo.generator;

import org.juanssl.transformo.data.FieldMeta;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class FieldGenerator extends BaseGenerator {
    private static final Logger logger = Logger.getLogger("IdentityGenerator");
    private FieldMeta _fmeta;
    
    public FieldGenerator(FieldMeta field){
        super();
        _fmeta = field;
    }

    @Override
    public String Generate(String template) {
        String result = GenerateData(template, _fmeta, 0, 0);
        logger.info(result);
        return result;
    }
}
