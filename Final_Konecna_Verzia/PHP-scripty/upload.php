<?php

$dbServername = 'localhost';
$dbUsername = "id7888732_kristian";
$password = "Polak1";

$connect = mysqli_connect($dbServername,$dbUsername,$password,$dbUsername);

if (isset($_FILES['file']) || $_POST['action'] || isset($_POST['action'])) {
    $destination = 'Images/'.$_FILES['file']['name'];
    if(!move_uploaded_file($_FILES['file']['tmp_name'],$destination)){
        echo "nenahralo subor";
    }
}

$sql = "INSERT INTO zazitky(Images) VALUES ('"."https://kristiancoolrex.000webhostapp.com/".$destination."');";

if ($connect->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $connect->error;
}
$connect->close();

?>

<a href="index.html" role="button">Nahraj dalsie</a>
