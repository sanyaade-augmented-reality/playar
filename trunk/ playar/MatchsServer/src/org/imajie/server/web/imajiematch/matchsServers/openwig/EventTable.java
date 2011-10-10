package org.imajie.server.web.imajiematch.matchsServers.openwig;

import org.imajie.server.web.imajiematch.matchsServers.kahlua.stdlib.BaseLib;
import org.imajie.server.web.imajiematch.matchsServers.kahlua.vm.*;

import java.io.*;
import java.util.Vector;
import org.imajie.server.web.imajiematch.matchsServers.main.GameWindow;

public class EventTable implements LuaTable, Serializable {

    public LuaTable table = new LuaTableImpl();
    private LuaTable metatable = new LuaTableImpl();

    private static class TostringJavaFunc implements JavaFunction {

        public EventTable parent;

        public TostringJavaFunc(EventTable parent) {
            this.parent = parent;
        }

        public int call(LuaCallFrame callFrame, int nArguments) {
            callFrame.push(parent.luaTostring());
            return 1;
        }
    };

    protected String luaTostring() {
        return "a ZObject instance";
    }

    public EventTable() {
        metatable.rawset("__tostring", new TostringJavaFunc(this));
    }

    public void serialize(DataOutputStream out) throws IOException {
        Engine.instance.savegame.storeValue(table, out);
    }

    public void deserialize(DataInputStream in) throws IOException {
        Engine.instance.savegame.restoreValue(in, this);
        //setTable(table);
    }
    public String name, description;
    public ZonePoint position = null;
    protected boolean visible = false;
    public Media media, icon;

    public byte[] getMedia() throws IOException {
        return Engine.mediaFile(media);
    }

    public byte[] getIcon() throws IOException {
        return Engine.mediaFile(icon);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setPosition(ZonePoint location) {
        position = location;
        table.rawset("ObjectLocation", location);
    }

    public boolean isLocated() {
        return position != null;
    }

    protected void setItem(String key, Object value) {


        if ("Name".equals(key)) {
            name = BaseLib.rawTostring(value);

        } else if ("Description".equals(key)) {
            description = Engine.removeHtml(BaseLib.rawTostring(value));

        } else if ("Visible".equals(key)) {
            visible = LuaState.boolEval(value);

        } else if ("ObjectLocation".equals(key)) {

            position = (ZonePoint) value;

        } else if ("Media".equals(key)) {
            media = (Media) value;

            // TODO implements a debug state to see the line below in the logs
            // System.out.println("PROP set to: " + media.name + " is set to " + media.type);


        } else if ("Icon".equals(key)) {
            icon = (Media) value;

        }
    }

    protected Object getItem(String key) {
        if ("CurrentDistance".equals(key)) {
            if (isLocated()) {
                return new Distance(position.distance(Engine.instance.player.position), null);
            } else {
                return new Distance();
            }
        } else {
            return table.rawget(key);
        }
    }

    public void setTable(LuaTable table) {
        Object n = null;
        while ((n = table.next(n)) != null) {
            Object val = table.rawget(n);
            rawset(n, val);
            //if (n instanceof String) setItem((String)n, val);
        }
    }

//    public void callEvent(String name, Object param) {
//        //System.out.println("Call Event.....Name: " +name +"  Params:"+param+"\n");
//
//        try {
//            Object o = table.rawget(name);
//            if (o instanceof LuaClosure) {
//                System.out.println("EVNT: " + toString() + "." + name + (param != null ? " (" + param.toString() + ")" : ""));
//                LuaClosure event = (LuaClosure) o;
//                Engine.state.call(event, this, param, null,request);
//                System.out.println("EEND: " + toString() + "." + name);
//                //return event.toString();
//            }
//        } catch (Throwable t) {
//            Engine.stacktrace(t);
//        }
//        //return null;
//    }
    public void callEvent(String name, Object param) {
        //System.out.println("Call Event.....Name: " +name +"  Params:"+param+"\n");



        try {
            Object o = table.rawget(name);
            if (o instanceof LuaClosure) {
                //System.out.println("Zone visible Before Call event.............." + Engine.instance.cartridge.visibleZones());                

                System.out.println("EVNT: " + toString() + "." + name + (param != null ? " (" + param.toString() + ")" : ""));
                LuaClosure event = (LuaClosure) o;
                Engine.state.call(event, this, param, null);
                System.out.println("EEND: " + toString() + "." + name);
                GameWindow.currentEvent = toString() + "." + name;
                Engine.prepareStateFinish = true;
                //  Engine.tempSession.setAttribute("callEvent", toString() + "." + name.toString());

                System.out.println("Attribute callEvent setted to session: " + name.toString());
                //return event.toString();
                //System.out.println("Zone visible After Call Event.............." + Engine.instance.cartridge.visibleZones());






            }


        } catch (Throwable t) {
            Engine.stacktrace(t);
        }


        //return null;
    }

    public boolean hasEvent(String name) {
        return (table.rawget(name)) instanceof LuaClosure;
    }

    public String toString() {
        return (name == null ? "(unnamed)" : name);
    }

    public void rawset(Object key, Object value) {
        if (key instanceof String) {
            setItem((String) key, value);
        }
        table.rawset(key, value);
        // TODO implements a debug state to see the line below in the logs
        //System.out.println("PROP set to: " + toString() + "." + key + " is set to " + (value == null ? "nil" : value.toString() + "." + key));

    }

    public void setMetatable(LuaTable metatable) {
    }

    public LuaTable getMetatable() {
        return metatable;
    }

    public Object rawget(Object key) {
        if (key instanceof String) {
            return getItem((String) key);
        } else {
            return table.rawget(key);
        }
    }

    public Object next(Object key) {
        return table.next(key);
    }

    public int len() {
        return table.len();
    }
}
