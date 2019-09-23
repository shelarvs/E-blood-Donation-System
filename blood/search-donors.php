<?php
 include 'db.php';
 
 $url=basename($_SERVER['REQUEST_URI']);
	$parts=parse_url($url);
 	parse_str($parts['query'], $query);
	
	
	$blood=$query['blood'];
	$location=$query['loc'];
	$sign=$query['sign'];
	
	if($sign=="plus")
	{
		$blood=$blood."+";
	}
	else if($sign=="minus")
	{
		$blood=$blood."-";
	}
	 
 
//$sql = "SELECT t1.*, t2.* FROM register t1, donor t2 where t2.Blood_Type='$blood' and t2.Location Like '%$location' or t2.Location Like '%$location%' or t2.Location Like '$location%'";
$dn="";
//$sql = "SELECT * FROM donor where Blood_Type='$blood' and Location Like '%$location' or Location Like '%$location%' or Location Like '$location%' and Donate='$dn'";
$sql = "SELECT * FROM donor where Blood_Type='$blood' and Donate='$dn'";
$res = mysqli_query($conn,$sql);
$result = array();
while($row = mysqli_fetch_array($res))
{
if($row[8]==$blood)
{
array_push($result,
array('v1'=>$row[0],
'v2'=>$row[1],
'v3'=>$row[2],
'v4'=>$row[3],
'v5'=>$row[4],
'v6'=>$row[5],
'v7'=>$row[6],
'v8'=>$row[8],
'v9'=>$row[9],
'v10'=>$row[10]
));
}
}
echo json_encode(array("result"=>$result));
mysqli_close($conn);
?>