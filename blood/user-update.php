<?php

include 'db.php';

	 
	 $e1=$_POST['e1'];
	 $e2=$_POST['e2'];
	  $e3=$_POST['e3'];
	 $e4=$_POST['e4'];
	 $e5=$_POST['e5'];
	 $e6=$_POST['e6'];
	 $e7=$_POST['e7'];
	 $e8=$_POST['e8'];  

		$sql="UPDATE `register` SET `Name`='$e1',`Mobile`='$e2',`Mobile1`='$e3',`Landline`='$e4',`Address`='$e5',`City`='$e6',`Pincode`='$e7' where `Username`='$e8' ";
				if ($conn->query($sql) == TRUE) 
				{
				 echo "User Updated successfully";
				} 
				else 
				{
   					 echo "Not Added";
				}	
	
	  


?>