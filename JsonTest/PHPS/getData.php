<?
header("Content-Type:application/json");
	include $_SERVER[DOCUMENT_ROOT]."/lib/dbconnection.php";

	$input = file_get_contents('php://input', 1000000);


	$json = @json_decode(stripslashes($input),true);
	$str = $json["STR"];
//	$str = iconv("EUC-KR","UTF-8", $str);



	if($str!=""){
		

		$query = " select idx , subject , memo from memodb where subject  like '%$str%' order by idx desc";
		//echo $query;
		$result = mysql_query($query);
		
		
		while($row= Mysql_Fetch_Assoc($result)){
			$list["IDX"][] = $row['idx'];
			$list["SUBJECT"][] = $row['subject'];
			$list["MEMO"][] = $row["memo"];
		}


		$return = json_encode($list);

		$content = addslashes($return);
		$str = "MAIN".$str;
		mysql_query("insert into test (title , contents) values ('$str' ,'$content')");
//		$return = addslashes($return);
		//$return = iconv("EUC-Kr" , "UTF-8" , $return);

		echo $return;
	}
		
?>