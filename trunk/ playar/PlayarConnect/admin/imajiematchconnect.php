<?php
/**
 * @author Antonio Duran
 * @license http://www.gnu.org/copyleft/gpl.html GNU Public License
 * @package imajiematchconnect
 */

// no direct access
defined('_JEXEC') or die('Restricted access');


/*
 * Define constants for all pages
 */
define( 'COM_IMAJIEMATCHCONNECT_DIR', 'images'.DS.'imajiematchconnect'.DS );
define( 'COM_IMAJIEMATCHCONNECT_BASE', JPATH_ROOT.DS.COM_IMAJIEMATCHCONNECT_DIR );
define( 'COM_IMAJIEMATCHCONNECT_BASEURL', JURI::root().str_replace( DS, '/', COM_IMAJIEMATCHCONNECT_DIR ));

// Require the base controller
require_once JPATH_COMPONENT.DS.'controller.php';

// Require the base controller
require_once JPATH_COMPONENT.DS.'helpers'.DS.'helper.php';

// Initialize the controller
$controller = new ImajiematchconnectController( );

// Perform the Request task
$controller->execute( JRequest::getCmd('task'));
$controller->redirect();
?>
