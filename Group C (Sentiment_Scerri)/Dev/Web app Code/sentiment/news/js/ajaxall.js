function politics(){
	$.ajax({
		type: "GET",
		url: "server/functions.php",
		async:true,
		data: { action: "political"}
		}).done(function( msg ) {
				//alert(msg);			
			$("#wresults").html(msg);							
	});
}

function sports(){
	$.ajax({
		type: "GET",
		url: "server/functions.php",
		async:true,
		data: { action: "sport"}
		}).done(function( msg ) {
				//alert(msg);			
			$("#wresults1").html(msg);							
	});
}

function social(){
	$.ajax({
		type: "GET",
		url: "server/functions.php",
		async:true,
		data: { action: "social"}
		}).done(function( msg ) {
				//alert(msg);			
			$("#wresults2").html(msg);							
	});
}

function economic(){
	$.ajax({
		type: "GET",
		url: "server/functions.php",
		async:true,
		data: { action: "economic"}
		}).done(function( msg ) {
				//alert(msg);			
			$("#wresults3").html(msg);							
	});
}

function show_news(cat){
	$.ajax({
		type: "GET",
		url: "server/functions.php",
		async:true,
		data: { action: "show_news",cat:cat}
		}).done(function( msg ) {
				//alert(msg);			
			$("#results").html(msg);							
	});
}

