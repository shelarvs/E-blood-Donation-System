<?php

include 'db.php';

	 
	 $e1=$_POST['e1'];
	 $e2=$_POST['e2'];
	  $e3=$_POST['e3'];
	 $e4=$_POST['e4']; 
	 
	 $d=date('d');
	 $m=date('m');
	 $y=date('Y');
	 
	 $m=$m+3;
	 $dt=$d."/".$m."/".$y;
	 
	 
		$sql="UPDATE `donor` SET `Donate`='$dt' where `Username`='$e1' and `Person`='$e2'";
				if ($conn->query($sql) == TRUE) 
				{
				 echo "Donated successfully";
				} 
				else 
				{
   					 echo "Not Added";
				}	
	
?>