<?php

include 'db.php';

 
	 $e1=$_POST['e1'];
	 $e2=$_POST['e2'];
	 $e3=$_POST['e3'];
	 $e4=$_POST['e4']; 
	 
	
	 $name="";
	 $mob="";
	 $mob1="";
	 $ll="";
	 $add="";
	 $city="";
	 $pin="";
	 	  	 
		 		$sql="select * from register where Username='$e1'";
				$result=$conn->query($sql);
				if($result->num_rows>0)
				{
					while($row=$result->fetch_assoc())
					{
						  $name=$row['Name'];
						 $mob=$row['Mobile'];
						 $mob1=$row['Mobile1'];
						 $ll=$row['Landline'];
						 $add=$row['Address'];
						 $city=$row['City'];
						 $pin=$row['Pincode'];
					}
				}
				$emt=$e4.".jpg";
				$dt="";
	 			$sql="insert into Donor values ('$name','$mob','$mob1','$ll','$add','$city','$pin','$e1','$e2','$e3','$e4','$emt','$dt')";
				if ($conn->query($sql) == TRUE) 
				{
					echo "Added successfully";
				} 
				else 
				{
   					echo "Not Added";
				}	
?>