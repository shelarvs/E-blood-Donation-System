<?php
 include 'db.php';
 
 $url=basename($_SERVER['REQUEST_URI']);
	$parts=parse_url($url);
 	parse_str($parts['query'], $query);
	
	
	$uname=$query['uname'];
 	
$sql = "select * from donor where Username='$uname'";
 
$res = mysqli_query($conn,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('v1'=>$row[8],
'v2'=>$row[9],
'v3'=>$row[10]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($conn);
 
?>