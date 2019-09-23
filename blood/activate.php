<?php

include 'db.php';

	 $e1="";
	 $d=date('d');
	 $m=date('m');
	 $y=date('Y');
	 
	 $dt=$d."/".$m."/".$y;
	 
	$dt=date('d/m/Y');
	$sql="UPDATE `donor` SET `Donate`='$e1' where `Donate`='$dt'";
	$conn->query($sql) == TRUE;
				
?>