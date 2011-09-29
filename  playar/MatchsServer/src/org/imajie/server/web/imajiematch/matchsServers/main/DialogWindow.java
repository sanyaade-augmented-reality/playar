package org.imajie.server.web.imajiematch.matchsServers.main;


import org.imajie.server.web.imajiematch.matchsServers.openwig.EventTable;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Media;
//import java.awt.Container;
//import java.awt.CardLayout;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import javax.swing.JDialog;
//import javax.swing.JFrame;
import org.imajie.server.web.imajiematch.matchsServers.kahlua.vm.LuaClosure;

/** Common dialog window for Wherigo Dialogs and Inputs
 * <p>
 * Handles displaying Dialogs and Inputs in a separate window,
 * takes care of their mutual exclusivity and cancelling.
 * Because much of the functionality is common between
 * Dialogs and Inputs, this window contains panes for both
 * Dialog and Input UI and switches between them as needed.
 */
public class DialogWindow  {

	private DialogPane dialog = new DialogPane(this);
	private InputPane input = new InputPane(this);

	//private CardLayout cards = new CardLayout();

	private static final int NOTHING = 0;
	private static final int DIALOG = 1;
	private static final int INPUT = 2;
	/** tracks which of the panes was displayed last, to know what to cancel */
	private int nowShowing = NOTHING;

	protected DialogWindow () {
//		super(parent);
//
//		Container pane = getContentPane();
//		pane.setLayout(cards);
//		pane.add(dialog, "dialog");
//		pane.add(input, "input");
//		pack();
//
//		setModal(false);
//		setLocationRelativeTo(parent);
//
//		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing (WindowEvent we) {
//				close();
//			}
//		});
	}

	/** Prepares and shows a Wherigo Dialog.
	 * @see cz.matejcik.openwig.platform.UI#pushDialog(java.lang.String[], cz.matejcik.openwig.Media[], java.lang.String, java.lang.String, se.krka.kahlua.vm.LuaClosure)
	 */
	public void showDialog (String[] texts, Media[] media, String button1, String button2, LuaClosure callback)
	{
		cancelRunning();
		nowShowing = DIALOG;
		dialog.showDialog(texts, media, button1, button2, callback);
                System.out.println("Show dialog" + texts+ " " + media.toString() + " " + button1 + " " + button2);
//		cards.show(getContentPane(), "dialog");
//		pack();
//		setVisible(true);
	}

	/** Prepares and shows a Wherigo Input.
	 * @see cz.matejcik.openwig.platform.UI#pushInput(cz.matejcik.openwig.EventTable)
	 * @param input the input to show
	 */
	public void showInput (EventTable input) {
		cancelRunning();
		nowShowing = INPUT;
		this.input.showInput(input);
                System.out.println("Show input" + input.toString());
//		cards.show(getContentPane(), "input");
//		pack();
//		setVisible(true);
	}

	/** Hides the window. Called by Dialog and Input panes to let the window know
	 * that their processing is done.
	 */
	public void disappear () {
		nowShowing = NOTHING;
//		setVisible(false);
	}

	/** Closes the currently open dialog and requests the current pane to cancel.
	 * Called by <code>GameWindow</code> to indicate closing the dialog by external
	 * influence (probably only <code>showScreen</code>)
	 */
	public void close () {
		cancelRunning();
		disappear();
	}

	/** Cancels the appropriate pane. Called before showing new dialog or input. */
	private void cancelRunning () {
		if (nowShowing == DIALOG) {
			dialog.cancel();
		} else if (nowShowing == INPUT) {
			input.cancel();
		}
		nowShowing = NOTHING;
	}
}
