<?
// no direct access
defined('_JEXEC') or die;

jimport('joomla.application.component.controller');


class ImajiematchconnectControllerImajiematch extends JController
{

	public function __construct($config = array())
    {
        parent::__construct($config);

        $this->registerTask('imajiematch',        'imajiematch');
    }

   public function connect ()
    {
		echo "SERVER IS WORKING";
		return;
    }

}

?>
