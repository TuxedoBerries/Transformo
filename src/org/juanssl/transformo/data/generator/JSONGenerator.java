/*
 * Copyright (C) 2015 juanssl
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
package org.juanssl.transformo.data.generator;

import org.juanssl.transformo.code.generator.Modifiers;
import org.juanssl.transformo.data.RowData;
import org.juanssl.transformo.data.RowDataConverter;

/**
 *
 * @author juanssl
 */
public class JSONGenerator extends BaseGenerator {

    private StringBuilder _builder;
    
    @Override
    public void Generate() {
        _builder = new StringBuilder();
        
        _builder.append("{");
        for(int i=0; i<_data.size(); ++i){
            DataPack pack = _data.get(i);
            _builder.append("\"");
            _builder.append( Modifiers.ToSneakCase(pack.Meta.TableName) );
            _builder.append("\":");
            _builder.append("[");
            for(int d=0; d<pack.Data.size(); ++d){
                RowData row = pack.Data.get(d);
                _builder.append( RowDataConverter.ConvertToFullJSON(row) );
                
                if(d+1 < pack.Data.size()){
                    _builder.append(",");
                }
            }
            _builder.append("]");
            if(i+1 < _data.size()){
                _builder.append(",");
            }
        }
        _builder.append("}");
    }
    
    @Override
    public String GetData(){
        return _builder.toString();
    }
}
