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
package org.imajie.server.web.log;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
import java.util.*;
import java.text.*;
public final class TimeStamp {
private String str;
final static private SimpleDateFormat format
= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
public TimeStamp() {
str = format.format(new java.util.Date());
}
public String toString() {
return str;
}
public boolean equals(Object o) {
return o instanceof TimeStamp && str.equals(""+o);
}
public int hashCode() {
return str.hashCode();
}
public static String GetTime() {
return ""+new TimeStamp();
}
}


