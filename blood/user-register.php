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
	  $e9=$_POST['e9']; 
	  	 $cnt=0;
	  
	$sql="select * from register where Username='$e8'";
	$result=$conn->query($sql);

	if($result->num_rows>0)
	{
		while($row=$result->fetch_assoc())
		{
			if($e8==$row["Username"])
			{
				$cnt=1;
			}
		}
	}
	if($cnt==0)
	{
	 			$sql="insert into register values ('$e1','$e2','$e3','$e4','$e5','$e6','$e7','$e8','$e9')";
				if ($conn->query($sql) == TRUE) 
				{
				
				echo "User Added successfully";
				} 
				else 
				{
   					 echo "Not Added";
				}	
	}
	else
	{
		echo "Username Already exists";
	}
		 
	  


?>