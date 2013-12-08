<?php

$query = $_GET['query'];

$host = "localhost:3306"; 
$user = "root"; 
$pass = ""; 

$r = mysql_connect($host, $user, $pass);
mysql_select_db('tourdemuseum', $r);

if (!$r) {
    echo "Could not connect to server\n";
    trigger_error(mysql_error(), E_USER_ERROR);
}

$rs = mysql_query($query);

if (!$rs) {
    echo "Could not execute query: $query\n";
    trigger_error(mysql_error(), E_USER_ERROR);
}

$rows = array();
while($r = mysql_fetch_assoc($rs)) {
    $rows[] = $r;
}

mysql_close();
print json_encode($rows);


?>