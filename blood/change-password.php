<?php

include 'db.php';

 
	 $e1=$_POST['e1'];
	 $e2=$_POST['e2'];
	  $e3=$_POST['e3']; 
	 
	  	 
	 			$sql="update register set Password='$e2' where Username='$e1' and Password='$e1'";
				if ($conn->query($sql) == TRUE) 
				{
				
				echo "successfully";
				} 
				else 
				{
   					 echo "incorrect password";
				}	
	 

?>