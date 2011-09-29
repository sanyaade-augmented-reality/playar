<?
// no direct access
defined('_JEXEC') or die;

jimport('joomla.application.component.controller');


class ImajiematchconnectControllerImajiematch extends JController
{
   public function connect ()
    {
	
	
	//global $xmlrpcStruct;

	JRequest::getVar('username');
        
        $params = xmlrpc_decode ($_POST['params']);
		

		// if(func_num_args() < 3){
			// return $this->response('PLG_XMLRPC_JOOMLA_ILLEGAL_REQUEST');
		// }

                if (!JRequest::getVar('username'))
		{
                    $username	= strval( $params[0] );
		$password	= strval( $params[1] );
                    
                } else {
                    
                $username	= JRequest::getVar('username');
		$password	= JRequest::getVar('password');;
                    
                }
        
                //$username	= strval( $params[0] );
		//$password	= strval( $params[1] );
                
		//$username	= JRequest::getVar('username');
		//$password	= JRequest::getVar('password');;

		$user = $this->authenticateUser($username, $password);

		if (!$user)
		{
			//array_push($params,"BAD_LOGIN", "ERROR");
			$params = "BAD_LOGIN";
			return $this->response($params );
		}

		$name = $user->name;
		if(function_exists('mb_convert_kana')){
			$name = mb_convert_kana($user->name, 's');
		}

		$names = explode(' ', $name);
		$firstname = $names[0];
		$lastname = trim(str_replace($firstname, '', $name));

	
		
                $params = array ( "status"=>"OK","username"=>$user->username,"id"=>$user->id,"lastname"=>$lastname );
 
               // = array ( "OK", $user->username, $user->id, $lastname);

	
	
	
	return $this->response($params );
		
    }
	protected function response($params)
	{
	
            
        echo xmlrpc_encode ($params);
		
		//echo xmlrpc_encode ($params);
		
	}
	
	protected function authenticateUser($username, $password)
	{
		jimport( 'joomla.user.authentication');
		$auth = & JAuthentication::getInstance();
		$credentials['username'] = $username;
		$credentials['password'] = $password;
		$authuser = $auth->authenticate($credentials, null);

		if($authuser->status == JAUTHENTICATE_STATUS_FAILURE || empty($authuser->username) || empty($authuser->password) || empty($authuser->email)){
			return false;
		}

		$user =& JUser::getInstance($authuser->username);
		//Check Status
		if(empty($user->id) || $user->block || !empty($user->activation)){
			return false;
		}

		JFactory::getSession()->set('user', $user);

		return $user;
	}

}

?>
