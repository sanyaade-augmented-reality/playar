/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imajie.server.web;



/**
 *
 * @author Pierre Levy
 */
public class Utils
{

    public static double getDouble( String parameter, double def)
    {
        double value = def;
        String sValue = parameter;
        if (sValue != null)
        {
            try
            {
                value = Double.parseDouble(sValue);
            } catch (NumberFormatException e)
            {
            }
        }
        return value;

    }

    public static int getInt( String parameter, int def)
    {
        int value = def;
        String sValue = parameter;
        if (sValue != null)
        {
            try
            {
                value = Integer.parseInt(sValue);
            } catch (NumberFormatException e)
            {
            }
        }
        return value;

    }
}
