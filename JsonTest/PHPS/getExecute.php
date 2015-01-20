<?
	header("Content-Type:application/json");
	include $_SERVER[DOCUMENT_ROOT]."/lib/dbconnection.php";

	$input = file_get_contents('php://input', 1000000);


	$json = @json_decode(stripslashes($input),true);
	$str = $json["EXEC"];


//	$str = iconv("EUC-KR","UTF-8", $str);

	mysql_query("insert into test set contents='$str'");


	if($str=="DELETE ALL"){
		$query = " truncate table test";
		//echo $query;
		$result = mysql_query($query);
		$list["SUCCESS"][] = "TRUE";

		$return = json_encode($list);
		echo $return;
	}
?>