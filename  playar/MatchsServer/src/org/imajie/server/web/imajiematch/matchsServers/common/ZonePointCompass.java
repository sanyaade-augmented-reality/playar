package org.imajie.server.web.imajiematch.matchsServers.common;

import org.imajie.server.web.imajiematch.matchsServers.openwig.ZonePoint;
import java.awt.Component;
import java.awt.Graphics;

/** An Icon that draws a compass pointing towards a specified <code>ZonePoint</code> */
public class ZonePointCompass extends ZonePointIcon {

	public ZonePointCompass () {
		width = height = 200;
	}

	public ZonePointCompass (ZonePoint zp) {
		super(zp);
		width = height = 200;
	}

	@Override
	public void paintIcon (Component c, Graphics g, int x, int y) {
		Navigator.paintCompass(g, x, y, width, height, getPoint());
	}
}
