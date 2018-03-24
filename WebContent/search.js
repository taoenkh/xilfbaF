function handleresult(resultData){
	console.log("hi");
	console.log(resultData);
	var body = jQuery("#star_table_body");
	body.append("<h1>shabi</h1>");
	for(var i = 0;i<resultData.length; i++){
		console.log("hi1");
		var ht = "shabi";
		ht+="<tr>";
		ht+="<th>" + resultData[i]["id"]+"</th>";
		ht+="<th>" + resultData[i]["title"]+"</th>";
		ht+="<th>" + resultData[i]["year"]+"</th>";
		ht+="<th>" + resultData[i]["director"]+"</th>";
		ht+="<th>" + resultData[i]["stars"]+"</th>";
		ht+="<th>" + resultData[i]["genre"]+"</th>";
		ht+="</tr>";
		body.append(ht);
	}
	
	
	
}
jQuery.ajax ({
	url:"/cs122b/main",
	method: "GET",
	dataType:"json",
	success:(resultData)=>handleresult(resultData)
	
});