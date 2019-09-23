<?php

include 'db.php';

	 
	 $e1=$_POST['e1'];
	 $e2=$_POST['e2'];
	  $e3=$_POST['e3'];
	 $e4=$_POST['e4']; 

		$sql="delete from donor where `Username`='$e1' and `Person`='$e2'";
				if ($conn->query($sql) == TRUE) 
				{
				 echo "Deleted Successfully";
				} 
				else 
				{
   					 echo "Not Added";
				}	
	
	  


?>