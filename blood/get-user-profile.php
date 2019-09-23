<?php
 include 'db.php';
 
 $url=basename($_SERVER['REQUEST_URI']);
	$parts=parse_url($url);
 	parse_str($parts['query'], $query);
	
	
	$uname=$query['uname'];
 	
$sql = "select * from register where Username='$uname'";
 
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('v1'=>$row[0],
'v2'=>$row[1],
'v3'=>$row[2],
'v4'=>$row[3],
'v5'=>$row[4],
'v6'=>$row[5],
'v7'=>$row[6]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($conn);
 
?>