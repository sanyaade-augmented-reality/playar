/* Copyright (c) 2010 Imajie project owners (see http://www.imajie.org)
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
package org.imajie.server.web.imajiematch.matchsServers.layar;

/**
 *
 * @author Pierre Levy
 */
public class LayarParamsService
{
    public int DEFAULT_DISTANCE = 100;
    public int DEFAULT_TYPE = 0;
    public int DEFAULT_DIMENSION = 2;
    public int DEFAULT_ANGLE = 0;
    public double DEFAULT_SCALE = 0.5;
    public int DEFAULT_SIZE = 100;
    public int DEFAULT_MAX_POI = 20;
    
    private int distance = DEFAULT_DISTANCE;
    private int type = DEFAULT_TYPE;
    private int dimension = DEFAULT_DIMENSION;
    private boolean rel =  true;
    private int angle = DEFAULT_ANGLE;
    private double scale = DEFAULT_SCALE;
    private int maxPOIs = DEFAULT_MAX_POI;
    private int size = DEFAULT_SIZE;

    private static LayarParamsService instance = new LayarParamsService();
    
    private LayarParamsService()
    {
        
    }
    
    public static LayarParamsService instance()
    {
        return instance;
    }


    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the dimension
     */
    public int getDimension()
    {
        return dimension;
    }

    /**
     * @param dimension the dimension to set
     */
    public void setDimension(int dimension)
    {
        this.dimension = dimension;
    }

    /**
     * @return the rel
     */
    public boolean getRel()
    {
        return rel;
    }

    /**
     * @param rel the rel to set
     */
    public void setRel(boolean rel)
    {
        this.rel = rel;
    }

    /**
     * @return the angle
     */
    public int getAngle()
    {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(int angle)
    {
        this.angle = angle;
    }

    /**
     * @return the scale
     */
    public double getScale()
    {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(double scale)
    {
        this.scale = scale;
    }

    /**
     * @return the maxPOIs
     */
    public int getMaxPOIs()
    {
        return maxPOIs;
    }

    /**
     * @param maxPOIs the maxPOIs to set
     */
    public void setMaxPOIs(int maxPOIs)
    {
        this.maxPOIs = maxPOIs;
    }

    /**
     * @return the size
     */
    public int getSize()
    {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size)
    {
        this.size = size;
    }
    
    

}
