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
package org.juanssl.transformo.app;

/**
 *
 * @author juanssl
 */
public class Logger {
    
    public static void Info(String message){
        System.out.println(message);
    }
    
    public static void Info(String message, Object... params){
        System.out.println(String.format(message, params));
    }
    
    public static void Warning(String message){
        System.out.println("[WARNING] ".concat(message));
    }
    
    public static void Warning(String message, Object... params){
        System.out.println(String.format("[WARNING] ".concat(message), params));
    }
    
    public static void Error(String message){
        System.err.println("[ERROR] ".concat(message));
    }
    
    public static void Error(String message, Object... params){
        System.err.println(String.format("[ERROR] ".concat(message), params));
    }
}
