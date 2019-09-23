<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Untitled Document</title>
 <link rel="stylesheet" href="bootstrap/bootstrap.min.css">
  <script src="bootstrap/jquery.min.js"></script>
  <script src="bootstrap/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid">

<div class="panel-group">
    <div class="panel panel-primary">
      <div class="panel-heading">Upload Report</div>
      <div class="panel-body">
	
<form action="" method="POST" enctype="multipart/form-data">
 <br />
 		Enter Username: <input type="text" name="uname" placeholder="Ex.abcd" class="form-control" required/><br />
		Person Name:    <input type="text" name="pname" placeholder="Ex.Kedar" class="form-control" required/><br />
          Select Report<input type="file" name="image" /> <br />
         <center><input type="submit" value="Upload" class="btn-primary"/></center>
 </form>
   </div>
    </div>
</div>
 </div>
 <?php
 include 'db.php';
   if(isset($_FILES['image'])){
      $uname=$_POST['uname'];
	  $pname=$_POST['pname'];
      $file_name = $_FILES['image']['name'];// Get File Name
	 
	 
      $file_tmp =$_FILES['image']['tmp_name'];
      //$file_ext=strtolower(end(explode('.',$_FILES['image']['name'])));
      $ext = pathinfo($file_name, PATHINFO_EXTENSION);  // Get File Extension
      
         
		$cnt=0;
		 $sql="UPDATE `donor` SET `Report`='$file_name' where `Username`='$uname' and `Person`='$pname' ";
				if ($conn->query($sql) == TRUE) 
				{
				move_uploaded_file($file_tmp,"uploads/".$file_name);// Upload file to Folder
				 echo "Report Uploaded Successfully";
				} 
				else 
				{
   					 echo "Incorrect Details";
				}	
        
      
   }
?>
</body>
</html>
