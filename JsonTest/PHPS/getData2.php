<?
	header("Content-Type:application/json");
	include $_SERVER[DOCUMENT_ROOT]."/lib/dbconnection.php";

	$input = file_get_contents('php://input', 1000000);


	$json = @json_decode(stripslashes($input),true);
	$str = $json["IDX"];
//	$str = iconv("EUC-KR","UTF-8", $str);



	if($str!=""){
		$query = " select memo from memodb where idx = '$str'";
		//echo $query;
		$result = mysql_query($query);
		
		
		while($row= Mysql_Fetch_Assoc($result)){
			$list["CONTENTS"][] = addslashes($row['memo']);
		}


		$return = json_encode($list);
//		$return = iconv("EUC-KR" , "UTF-8" , $return);
		$content = addslashes($return);
		$str = "SUB".$str;
		mysql_query("insert into test (title , contents) values ('$str' ,'$content')");

		echo $return;
		
	}

?>