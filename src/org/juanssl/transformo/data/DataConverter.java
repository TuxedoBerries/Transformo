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
package org.juanssl.transformo.data;

/**
 *
 * @author Juan Silva
 */
public class DataConverter {

    /**
     * Force cast an object to a boolean.
     *
     * @param obj
     * @return
     */
    public static boolean forceCastToBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (boolean) obj;
        }

        if (obj instanceof Double) {
            double data = (Double) obj;
            return data > 0;
        }

        if (obj instanceof String) {
            String data = (String) obj;
            return !data.isEmpty();
        }

        return obj != null;
    }

    /**
     * Force cast an object into a long.
     *
     * @param obj
     * @return
     */
    public static long forceCastToLong(Object obj) {
        if (obj instanceof Double) {
            double data = (Double) obj;
            return Math.round(data);
        }

        if (obj instanceof String) {
            String data = (String) obj;
            return (long) Double.parseDouble(data);
        }

        if (obj instanceof Boolean) {
            boolean data = (Boolean) obj;
            if (data) {
                return 1;
            } else {
                return 0;
            }
        }

        if (obj != null) {
            return 1;
        }

        return 0;
    }

    /**
     * Force cast an object into a double.
     *
     * @param obj
     * @return
     */
    public static double forceCastToDouble(Object obj) {
        if (obj instanceof Double) {
            return (double) obj;
        }

        if (obj instanceof String) {
            String data = (String) obj;
            return Double.parseDouble(data);
        }

        if (obj instanceof Boolean) {
            boolean data = (Boolean) obj;
            if (data) {
                return 1;
            } else {
                return 0;
            }
        }

        if (obj != null) {
            return 1;
        }

        return 0;
    }

    /**
     * Force cast an object to String
     *
     * @param obj
     * @return
     */
    public static String forceCastToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }

        if (obj instanceof Double) {
            double data = (Double) obj;
            return Double.toString(data);
        }

        if (obj instanceof Boolean) {
            boolean data = (Boolean) obj;
            return Boolean.toString(data);
        }

        return obj.toString();
    }
}
