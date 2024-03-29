<?php
	/* Master Database Configuration File for LightRod Software */
	
	//Basic configuration
	$db_host = "127.0.0.1";				//Usually the same machine hosting your database
	$db_username = "gardien";		//Edit this e.g. "peter"
	$db_password = "doudou-07";		//Edit this e.g. "secretpassword"
	$db_name = "imajiematch";			//Change this for each new install -
							//the database name on MySQL given to the LightRod Layar
	$merge_with = "";				//Can be used for merging another layer with this one's results
	$server_name = $_SERVER['SERVER_NAME'];		//The server name
	



	//ARRAY START
	$sources = array("my_source1" => array(
				"db_host" => "127.0.0.1",
				"db_username" => "gardien",
				"db_password" => "doudou-07",
				"db_name" => "imajiematch",
				"merge_with" => ""),
			"my_source2" => array(
				"db_host" => "127.0.0.1",
				"db_username" => "gardien",
				"db_password" => "doudou-07",
				"db_name" => "testdb2",
				"merge_with" => "")
			);	//Repeat out this array above and set 

	//ARRAY END

	
	//More advanced scenario, of multiple layers from one install
	//Use .htaccess to grab the directory name and put it into 'dir_source' GET request
	if(isset($_REQUEST['dir_source'])) {
		

		
		//Set the standard configuration variables with the required setup
		$dir_source = strtolower($_REQUEST['dir_source']);			
		$db_host = $sources[$dir_source]["db_host"];
		$db_username = $sources[$dir_source]["db_username"];
		$db_password = $sources[$dir_source]["db_password"];
		$db_name = $sources[$dir_source]["db_name"];
		$merge_with = $sources[$dir_source]["merge_with"];
		
		
	
	}
	
	
	
	
	//Leave the code below - this connects to the database
	$db = mysql_connect($db_host, $db_username, $db_password);
	mysql_select_db($db_name);
	mysql_query("SET NAMES 'utf8'");
	
	
	//By default the admin panel login is the same as the db password
	$user_username = $db_username;
	$user_password = $db_password;	
	


?>
