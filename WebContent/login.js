
function handleLoginResult(resultDataString) {
	console.log(resultDataString);
	resultDataJson = JSON.parse(resultDataString);
	
	console.log("handle login response");
	console.log(resultDataJson);
	console.log(resultDataJson["status"]);

	// if login success, redirect to index.html page
	if (resultDataJson["status"] == "success") {
		window.location.replace("./index.html");
	}
	else if (resultDataJson["status"] == "employee"){
		window.location.replace("/cs122b/_dashboard")
	}
		
		
	
	else {
		console.log("show error message");
		console.log(resultDataJson["message"]);
		jQuery("d").text(resultDataJson["message"]);
	}
}


function submitLoginForm(formSubmitEvent) {
	console.log("submit login form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	formSubmitEvent.preventDefault();
		
	jQuery.post(
		"/cs122b/Login", 
		// serialize the login form to the data sent by POST request
		jQuery("#login_form").serialize(),
		(resultDataString) => handleLoginResult(resultDataString));

}

// bind the submit action of the form to a handler function
jQuery("#login_form").submit((event) => submitLoginForm(event));

