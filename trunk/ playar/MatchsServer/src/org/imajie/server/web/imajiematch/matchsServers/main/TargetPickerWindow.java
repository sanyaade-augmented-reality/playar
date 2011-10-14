package org.imajie.server.web.imajiematch.matchsServers.main;

import org.imajie.server.web.imajiematch.matchsServers.events.EventTableListItem;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Action;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;
import org.imajie.server.web.imajiematch.matchsServers.openwig.EventTable;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Thing;
import org.imajie.server.web.imajiematch.matchsServers.common.RefreshableListModel;
import org.imajie.server.web.imajiematch.matchsServers.common.WigList;
//import javax.swing.Icon;
//import javax.swing.JDialog;
//import javax.swing.JFrame;
import org.imajie.server.web.imajiematch.matchsServers.kahlua.vm.LuaTable;

/** Window with list of possible targets for a selected action.
 * <p>
 * When an action requires a parameter (a target), this window
 * is fired by {@link EventTableDetails} to offer a list of
 * available targets. The player then can close this window,
 * or choose the target by clicking on it. The window then initiates
 * the action with the specified target.
 */
public class TargetPickerWindow  {

	private Action action;

	/** List item that does not display distances and arrows,
	 * and instead displays location of each target.
	 */
	private static class TargetItem extends EventTableListItem {

		public TargetItem (EventTable et) {
			super(et);
		}

		@Override
		public String getSubtitle () {
			if (table instanceof Thing) {
				Thing t = (Thing)table;
				if (t.container == Engine.instance.player) return "inventory";
				else if (t.container != null) return t.container.name;
				else return "";
			} else return "";
		}

//		@Override
//		public Icon getStatus () {
//			return null;
//		}
	}

	private RefreshableListModel<TargetItem> model = new RefreshableListModel<TargetItem>();

	/** List component with the targets. */
	private WigList list = new WigList() {
		
            //@Override
            
		protected void onClick (int id, Object item) {
			TargetItem ti = (TargetItem)item;
			Engine.callEvent(action.getActor(), "On" + action.text, ti.table);
			//TargetPickerWindow.this.setVisible(false);
		}
	};

	public TargetPickerWindow () {
		//super(parent);
		//getContentPane().add(list.getScrollable());
		//setLocationRelativeTo(parent);
		//setModal(false);
		//setDefaultCloseOperation(HIDE_ON_CLOSE);
		//setTitle("Pick a target");
		//pack();

		//list.setModel(model);
	}

	/** Shows the picker window and updates it with list of available
	 * targets for the selected action.
	 * @param a the action which needs a target
	 */
	public void showPicker (Action a) {
		action = a;
		model.clear();
		LuaTable t = Engine.instance.cartridge.currentThings();
		Object key = null;
		while ((key = t.next(key)) != null) {
			Thing th = (Thing)t.rawget(key);
			if (th.isVisible() && action.isTarget(th)) model.add(new TargetItem(th));
                        System.out.println("Event Name:"+th.name+" Event Description:"+th.description);
		}
		t = Engine.instance.player.inventory;
		key = null;
		while ((key = t.next(key)) != null) {
			Thing th = (Thing)t.rawget(key);
			if (th.isVisible() && action.isTarget(th)) model.add(new TargetItem(th));
                        System.out.println("Event Name:"+th.name+" Event Description:"+th.description);
		}
		//setVisible(true);
	}
}
