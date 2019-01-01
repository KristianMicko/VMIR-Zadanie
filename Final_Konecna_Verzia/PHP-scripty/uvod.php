<?php

header("Content-type:application/json");
$dbServername = 'localhost';
$dbUsername = "id7888732_kristian";
$password = "Polak1";

$connect = mysqli_connect($dbServername,$dbUsername,$password,$dbUsername);

$sql = "select * from zazitky;";
if($_SERVER["REQUEST_METHOD"] == "GET"){
    if(!empty($_GET['ID']) && !empty($_GET['Images'])){
        $GLOBALS['sql']="select * from zazitky where id=".$_GET['id']."and Images like '%".$_GET['Images']."%';";
    }else if(!empty($_GET['id'])){
        $GLOBALS['sql']="select * from zazitky where id=".$_GET['id'].";";
    }else if(!empty($_GET['Images'])){
        $GLOBALS['sql']="select * from zazitky where login like '%".$_GET['Images']."%';";
    }

}

$result = mysqli_query($connect,$sql);
$resultcheck = mysqli_num_rows($result);
if ($resultcheck>0){
    while ($row = mysqli_fetch_assoc($result)){
        $jsondata[]=$row;
    }
}
$jsondata = json_encode($jsondata);
print_r($jsondata);

$connect->close();
?>

