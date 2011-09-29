<?php

/**
 * @author Antonio Duran
 * @license http://www.gnu.org/copyleft/gpl.html GNU Public License
 * @package imajiematchconnect
 */

// no direct access
defined('_JEXEC') or die('Restricted access');

jimport( 'joomla.application.component.controller' );
require_once( JPATH_COMPONENT.DS.'helpers'.DS.'helper.php' );

/**
 * Imajiematchconnect Controller
 *
 * @package Joomla
 * @subpackage Imajiematchconnect
 */
class ImajiematchconnectController extends JController {
    /**
     * Constructor
     * @access private
     * @subpackage Imajiematchconnect
     */
    function __construct() {
        //Get View
        if(JRequest::getCmd('view') == '') {
            JRequest::setVar('view', 'default');
        }
        $this->item_type = 'Default';
        parent::__construct();
    }
}
?>
