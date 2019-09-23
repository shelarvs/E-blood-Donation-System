<?php

include 'db.php';

	$sql="";
	$e1=$_POST['e1'];
	$e2=$_POST['e2']; 
		   
$sql = "select * from register where Username='$e1' ";		 	
$result=$conn->query($sql);
if($result->num_rows>0)
{
	while($row=$result->fetch_assoc())
	{
		if($row["Username"]==$e1 && $row["Password"]==$e2)
		{	
 				echo "Login Successful";
		}
	}
}
	
?>