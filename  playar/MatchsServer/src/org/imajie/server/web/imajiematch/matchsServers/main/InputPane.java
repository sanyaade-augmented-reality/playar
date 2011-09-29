package org.imajie.server.web.imajiematch.matchsServers.main;

import javax.swing.JButton;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;
import org.imajie.server.web.imajiematch.matchsServers.openwig.EventTable;
import org.imajie.server.web.imajiematch.matchsServers.common.DetailPane;
import java.util.ArrayList;
//import javax.swing.JButton;
//import javax.swing.JTextField;

import org.imajie.server.web.imajiematch.matchsServers.kahlua.vm.LuaTable;

/** GUI for displaying Inputs.
 * <p>
 * Uses <code>DetailPane</code>'s UI to show Wherigo Inputs.
 * @see cz.matejcik.openwig.platform.UI#pushInput(cz.matejcik.openwig.EventTable)
 */
public class InputPane extends DetailPane {

	/** button to confirm answer in text field */
	//private JButton answer = new JButton("Answer");
	/** text field for Text inputs */
	//private JTextField textInput = new JTextField();

	/** pool of buttons for multiple-choice inputs */
	//private ArrayList<JButton> options = new ArrayList<JButton>();

	private EventTable input;

	private DialogWindow parent;

	public InputPane (DialogWindow parent) {
//		this.parent = parent;
//		setTitle("Question");
//		rightPanel.add(textInput);
//		addButton(answer);
	}

	/** Shows an Input.
	 * <p>
	 * See description of <code>pushInput()</code> for explanation about inputs.
	 * @param input the input to show
	 * @see cz.matejcik.openwig.platform.UI#pushInput(cz.matejcik.openwig.EventTable)
	 */
	public void showInput (EventTable input) {
		this.input = input;
		String text = Engine.removeHtml((String)input.rawget("Text"));
		setDescription(text);
		setMedia(input.media);

		String type = (String)input.rawget("InputType");
		if ("Text".equals(type)) {
			// hide buttons
//			for (JButton button : options) button.setVisible(false);
//			// show text/answer
//			textInput.setVisible(true);
//			textInput.setText("");
//			answer.setVisible(true);
		} else if ("MultipleChoice".equals(type)) {
			// hide text/answer
//			textInput.setVisible(false);
//			answer.setVisible(false);
			LuaTable choices = (LuaTable)input.rawget("Choices");
			int n = choices.len();
			// make sure we have enough buttons
//			for (int i = options.size(); i < n; i++) {
//				JButton jb = new JButton();
//				options.add(jb);
//				addButton(jb);
//			}
			// set up choices
			for (int i = 0; i < n; i++) {
				String choice = (String)choices.rawget(new Double(i+1));
				//JButton jb = options.get(i);
				//jb.setText(choice);
				//jb.setVisible(true);
			}
			// hide the rest
//			for (int i = n; i < options.size(); i++) {
//				options.get(i).setVisible(false);
//			}
		}
		//rightPanel.revalidate();
	}

//	@Override
//	protected void buttonClicked (JButton button) {
//		if (button == answer) {
//			Engine.callEvent(input, "OnGetInput", textInput.getText());
//		} else {
//			Engine.callEvent(input, "OnGetInput", button.getText());
//		}
//		parent.disappear();
//	}

	/** Called by parent window to indicate that the Dialog was cancelled.
	 * Calls the OnGetInput callback event with null parameter.
	 */
	public void cancel () {
		Engine.callEvent(input, "OnGetInput", null);
	}

   

    

}
