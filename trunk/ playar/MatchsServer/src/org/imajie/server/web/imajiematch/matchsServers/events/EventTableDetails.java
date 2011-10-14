package org.imajie.server.web.imajiematch.matchsServers.events;

import org.imajie.server.web.imajiematch.matchsServers.main.TargetPickerWindow;
import org.imajie.server.web.imajiematch.matchsServers.main.GameWindow;
import org.imajie.server.web.imajiematch.matchsServers.common.ZonePointCompass;
import org.imajie.server.web.imajiematch.matchsServers.common.DetailPane;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Action;
import org.imajie.server.web.imajiematch.matchsServers.openwig.EventTable;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Thing;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Zone;
import java.util.ArrayList;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;
//import javax.swing.JButton;


/** DetailPane implementation for any EventTable object.
 * <p>
 * Displays details for Zones, Things and Tasks.
 * It will also show navigation controls where appropriate,
 * and commands that can be invoked on this EventTable.
 */
public class EventTableDetails extends DetailPane  {
	
	private EventTable table;

	/** list of currently active Actions on this EventTable */
	private ArrayList<Action> actions = new ArrayList<Action>();
	/** pool of JButtons to be reused for Actions */
	private ArrayList<String> buttons = new ArrayList<String>();

	ZonePointCompass navpoint = new ZonePointCompass();

	GameWindow parent;
	TargetPickerWindow picker;

	public EventTableDetails (GameWindow parent) {
		this.parent = parent;
		//picker = new TargetPickerWindow(parent);
                picker = new TargetPickerWindow();
	}

	/** Ensures that displayed buttons correspond to available actions.
	 * <p>
	 * First it ensures that there are enough buttons in the button pool,
	 * then labels the appropriate number with Action titles and hides the
	 * rest, if any.
	 */
	private void updateButtons () {
		int nb = Math.max(0, actions.size() - buttons.size());
		 //make sure we have enough buttons in panel
		for (int i = 0; i < nb; i++) {
			String jb = new String();
			//addButton(jb);
			buttons.add(jb);
                        System.out.println("Button JB: " + jb);
		}
		// update their labels/visibility according to actions
		for (int i = 0; i < actions.size(); i++) {
			Action a = actions.get(i);
			String b = buttons.get(i);
			//b.setVisible(a.isEnabled() && a.getActor().visibleToPlayer());
			String label;
			if (a.getActor() == table) {
				label = a.text;
			} else {
				label = a.getActor().name + ": " + a.text;
			}
			//b.setText(label);
                        System.out.println("Label: " + label);
		}
		// hide the rest
		for (int i = actions.size(); i < buttons.size(); i++) {
		System.out.println("Button added : " + buttons.get(i).toString());	
                   // buttons.get(i).setVisible(false);
		}
	}

	/** Updates the display with new information that might have appeared
	 * in the EventTable. Called as part of UI refresh routine.
	 * @see cz.matejcik.openwig.platform.UI#refresh()
	 */
	public void refresh () {
		if (table == null ||
		   !table.isVisible() ||
		   (table instanceof Thing && !((Thing)table).visibleToPlayer())
		   ) {
			parent.hideDetails();
			return;
		}
		setTitle(table.name);
		setDescription(table.description);
		setMedia(table.media);

		if (table instanceof Zone) {
			Zone z = (Zone)table;
			setNavigationPoint(z.nearestPoint);
		} else if (table.isLocated()) {
			setNavigationPoint(table.position);
		} else {
			setNavigationPoint(null);
		}
		updateNavigation();

		if (table instanceof Thing) {
			Thing thing = (Thing)table;
			actions.clear(); actions.ensureCapacity(thing.actions.size());
			for (Object o : thing.actions) {
				actions.add((Action)o);
			}
			updateButtons();
		} 
//                else for (JButton button : buttons) {
//			button.setVisible(false);
//		}
	}

	/** Configures the pane to show details from <code>table</code> */
	public void showDetails (EventTable table) {
		this.table = table;
		refresh();
	}

	
	public void buttonClicked (String button) {
		int id = buttons.indexOf(button);
		if (id < 0) return;
		Action a = actions.get(id);
		if (a.getActor() == table) {
			if (a.hasParameter()) {
				picker.showPicker(a);
			} else {
				Engine.callEvent(table, "On"+a.getName(), null);
			}
		}
		else Engine.callEvent(a.getActor(), "On"+a.getName(), table);
	}

	@Override
	public void updateNavigation () {
		super.updateNavigation();
		if (table instanceof Zone) {
			Zone z = (Zone)table;
			if (z.contain == Zone.INSIDE) {
                            
                            
                            //distance.setText("inside");
                        }
		}
	}

	/** Moves simulated GPS to approximately the right place.
	 * Zones report distance to nearest point on their edge. If simulator
	 * moves to the edge, it will not trigger the zone's Enter event, so
	 * we need to move it inside the zone.
	 * The engine was never designed to do that, so we don't do anything
	 * complex and just jump to the center of zone's bounding box. Sometimes
	 * it works, sometimes it doesn't.
	 */
//	@Override
//	protected void simulatorClicked () {
//		if (table instanceof Zone) {
//			Zone zone = (Zone)table;
//			Simulator.walkTo(zone.bbCenter);
//			updateNavigation();
//		} else super.simulatorClicked();
//	}
}
