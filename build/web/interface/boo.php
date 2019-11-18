<?php
header("Cache-Control: no-cache");
sleep(2);

$p = $_GET['page'];

switch($p) {
case "portfolio":
echo '<h2>My Portfolio</h2>This could be a portfolio page !<br style="clear:both;" />';
break;
			  
case "contact":
echo 'Perhaps a contact page ?<br style="clear:both;" />';
break;

case "about": default:
echo 'Default Page, About page<br style="clear:both;" />';
break;
}


?>