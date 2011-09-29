package org.imajie.server.web.imajiematch.matchsServers.openwig;

import java.io.*;

import org.imajie.server.web.imajiematch.matchsServers.kahlua.vm.*;

public class Media extends EventTable {

    private static int media_no;

    public static void reset() {
        media_no = 1;

    }
    public int id;
    public String altText = null;
    public String type = null;

    public Media() {
        id = media_no++;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(id);
        super.serialize(out);
    }

    public void deserialize(DataInputStream in) throws IOException {
        media_no--; // deserialize must be called directly after construction
        id = in.readInt();
        if (id >= media_no) {
            media_no = id + 1;
        }
        super.deserialize(in);
    }

    protected void setItem(String key, Object value) {
        if ("AltText".equals(key)) {
            altText = (String) value;
        
        } else if ("Resources".equals(key)) {
            LuaTable lt = (LuaTable) value;
            int n = lt.len();
            for (int i = 1; i <= n; i++) {
                LuaTable res = (LuaTable) lt.rawget(new Double(i));
                String t = (String) res.rawget("Type");

                if ("fdl".equals(t)) {
                    continue;
                }
                type = t.toLowerCase();

                if (type.equals("mp3") || type.equals("wav")) {

                   // TODO implements a debug state to see the line below in the logs
                   // System.out.println("PROP set to: " + value.toString() + " is set to " + type);

                }

            }
        } else {

            super.setItem(key, value);

        }
    }

    public String jarFilename() {
        return String.valueOf(id) + "." + (type == null ? "" : type);
    }

    public void play() {
 
            Engine.ui.playSound(this);       
    }
}
