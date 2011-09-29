package org.imajie.server.web.imajiematch.matchsServers.cartridges;

import org.imajie.server.web.imajiematch.matchsServers.common.ZonePointIcon;
import org.imajie.server.web.imajiematch.matchsServers.common.Navigator;
import org.imajie.server.web.imajiematch.matchsServers.openwig.ZonePoint;
import org.imajie.server.web.imajiematch.matchsServers.format.CartridgeFile;
import java.io.File;

//import javax.swing.Icon;
//import javax.swing.ImageIcon;

/** List item that pulls relevant data from CartridgeFile objects */
public class CartridgeListItem {

	/** File object from which this cartridge item was generated */
	private File file;

	private CartridgeFile cartridge;
	/** Generated <code>ZonePoint</code> that represents this cartridge's starting coordinates */
	private ZonePoint startingPoint;
	/** True when cartridge starting point is at (360,360).
	 * This means that the cartridge can be played anywhere in the world.
	 */
	private boolean playAnywhere;
	//private ImageIcon icon;
	private ZonePointIcon arrow;

	/** default icon, in case cartridge does not contain its own */
	//private static final ImageIcon CART_ICON = new ImageIcon(CartridgeListItem.class.getResource("/icons/cartridge.png"));

	/** create new CartridgeListItem instance based on supplied CartridgeFile */
	public CartridgeListItem (File file, CartridgeFile cartridge) {
		this.file = file;
		this.cartridge = cartridge;
                
                
                
                
                
                // TODO IMPLEMENTS PLAY ANYWHERE IN LAYAR
		playAnywhere = cartridge.latitude == 360 && cartridge.longitude == 360;
                
                
                
                
                
                
		if (!playAnywhere) {
			startingPoint = new ZonePoint(cartridge.latitude, cartridge.longitude, 0);
			arrow = new ZonePointIcon(startingPoint);
		}
//		try {
////			byte[] b = cartridge.getFile(cartridge.iconId);
////			if (b == null) icon = CART_ICON;
////			else icon = new ImageIcon(b);
//		} catch (IOException e) {
////			icon = CART_ICON;
//		}
	}

//	public Icon getStatus () {
//		return arrow;
//	}

	public String getName () {
		return cartridge.name;
	}

	public String getSubtitle () {
		if (playAnywhere)
			return "PlayAnywhere";
		else
			return Navigator.getDistanceAndDirection(startingPoint);
	}

//	public Icon getIcon () {
//		return icon;
//	}

	/**
	 * @return the cartridge object
	 */
	public CartridgeFile getCartridge () {
		return cartridge;
	}

	/**
	 * @return the file
	 */
	public File getFile () {
		return file;
	}

}
