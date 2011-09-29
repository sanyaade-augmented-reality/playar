/*
 * Copyright (C) 2011 Carl Tremblay <carl_tremblay at imajie.tv>
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
package org.imajie.server.web.imajiematch.matchsServers.sockets;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
public class PortsList<T> extends ArrayList<T> { 
 
    public PortsList(int capacity) { 
        super(capacity); 
        for (int i = 0; i < capacity; i++) { 
            super.add(null); 
        } 
    } 
 
    public PortsList(T[] initialElements) { 
        super(initialElements.length); 
        for (T loopElement : initialElements) { 
            super.add(loopElement); 
        } 
    } 
 
    @Override 
    public void clear() { 
        throw new UnsupportedOperationException("Elements may not be cleared from a fixed size List."); 
    } 
 
    @Override 
    public boolean add(T o) { 
        throw new UnsupportedOperationException("Elements may not be added to a fixed size List, use set() instead."); 
    } 
 
    @Override 
    public void add(int index, T element) { 
        throw new UnsupportedOperationException("Elements may not be added to a fixed size List, use set() instead."); 
    } 
 
    @Override 
    public T remove(int index) { 
        throw new UnsupportedOperationException("Elements may not be removed from a fixed size List."); 
    } 
 
    @Override 
    public boolean remove(Object o) { 
        throw new UnsupportedOperationException("Elements may not be removed from a fixed size List."); 
    } 
 
    @Override 
    protected void removeRange(int fromIndex, int toIndex) { 
        throw new UnsupportedOperationException("Elements may not be removed from a fixed size List."); 
    } 
} 
