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
public class DataConverter {
    
    /**
     * Force cast an object to a boolean.
     * @param obj
     * @return 
     */
    public static boolean ForceCastToBoolean(Object obj){
        if(obj instanceof Boolean)
            return (boolean) obj;
        
        if(obj instanceof Double){
            double data = (Double) obj;
            return data > 0;
        }
        
        if(obj instanceof String){
            String data = (String) obj;
            return !data.isEmpty();
        }
        
        return obj != null;
    }
    
    /**
     * Force cast an object into a long.
     * @param obj
     * @return 
     */
    public static long ForceCastToLong(Object obj){
        if(obj instanceof Double){
            double data = (Double) obj;
            return Math.round(data);
        }
        
        if(obj instanceof String){
            String data = (String) obj;
            return (long)Double.parseDouble(data);
        }
        
        if(obj instanceof Boolean){
            boolean data = (Boolean)obj;
            if(data)
                return 1;
            else
                return 0;
        }
        
        if(obj != null)
            return 1;
        
        return 0;
    }
    
    /**
     * Force cast an object into a double.
     * @param obj
     * @return 
     */
    public static double ForceCastToDouble(Object obj){
        if(obj instanceof Double){
            return (double)obj;
        }
        
        if(obj instanceof String){
            String data = (String) obj;
            return Double.parseDouble(data);
        }
        
        if(obj instanceof Boolean){
            boolean data = (Boolean)obj;
            if(data)
                return 1;
            else
                return 0;
        }
        
        if(obj != null)
            return 1;
        
        return 0;
    }
    
    /**
     * Force cast an object to String
     * @param obj
     * @return 
     */
    public static String ForceCastToString(Object obj){
        if(obj instanceof String){
            return (String) obj;
        }
        
        if(obj instanceof Double){
            double data = (Double) obj;
            return Double.toString(data);
        }
        
        if(obj instanceof Boolean){
            boolean data = (Boolean) obj;
            return Boolean.toString(data);
        }
        
        return obj.toString();
    }
}
