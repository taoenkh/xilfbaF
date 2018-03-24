function handleresult(resultData){
		console.log("hi");
		console.log(resultData);
		var body = jQuery("#genrebrows");
		body.append("<h3>Browse Movie by Genre</h3>");
		for(var i = 0;i<resultData.length; i++){
			console.log("hi1");
			var ht = "<a href = './bygenre?genre="+ resultData[i]["genre"]+"&param="+"'>";
			ht+=resultData[i]["genre"];
			ht+="</a><br/>";

			body.append(ht);
		}
		
		
		
	}
	jQuery.ajax ({
		url:"/cs122b/genre",
		method: "GET",
		dataType:"json",
		success:(resultData)=>handleresult(resultData)
		
	});


$(document).ready(function(){
    console.log("Hello WOrld");

    $("#filter-button").click(function(e){
      e.preventDefault();
      console.log("FIlter click syoyfo");
      $("#hidden-detail").toggleClass("hidden");
      var currentHTML = e.target.innerHTML;
      if (currentHTML == "Advanced"){
        e.target.innerHTML = "Hide";
      } else {
        e.target.innerHTML = "Advanced";
      }
    });

})