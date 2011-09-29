<?php
/**
 * @author Antonio Duran
 * @license http://www.gnu.org/copyleft/gpl.html GNU Public License
 * @package xmlrpctest
 */

// no direct access
defined('_JEXEC') or die('Restricted access');

jimport('joomla.application.component.controller');

/**
 * Xmlrpctest Component Controller
 */
class ImajiematchconnectController extends JController {
	function display() {
        // Make sure we have a default view
        if( !JRequest::getVar( 'view' )) {
		    JRequest::setVar('view', 'imajiematchconnect' );
        } else {
		$view = JRequest::getVar( 'view' );
		JRequest::setVar('view', $view );
    }


		parent::display();

		return $this;
	}

public function connect ()
{
	echo "XX";
	exit ();
}

}
?>
