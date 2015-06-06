

function sent_positive(id){
	var IDs = $('#IDs').val();
	$.ajax({
		type: "GET",
		url: "server/function.php",
		async:true,
		data: { action: "pos", IDs:id}
		}).done(function( msg ) {
				//alert(msg)					
			$("#message").html(msg);
			$("#sent"+id).delay(1000).fadeOut();	
			$('#message').delay(3000).fadeOut();
			//location.reload();	
							
	});
}

function adj_opinion(id,sent){
	//alert(sent);
	$.ajax({
		type: "GET",
		url: "server/function.php",
		async:true,
		data: { action: "insert_opinion_adj", id:id, sent:sent}
		}).done(function( msg ) {
				//alert(msg)					
			$("#message").html(msg);
			$("#message").show();
			$("#sent"+id).delay(500).fadeOut();	
			$('#message').delay(10000).fadeOut();
			//location.reload();	
							
	});
}

function sentiment_text(){
	var text = $('#textArea').val();
	$.ajax({
		type: "GET",
		url: "server/function.php",
		async:true,
		data: { action: "sentiment_text", text:text}
		}).done(function( msg ) {
				//alert(msg)					
			$("#message").html(msg);
			$("#message").show();
		});
}


function sent_neutral(id){
	var IDs = $('#IDs').val();
	$.ajax({
		type: "GET",
		url: "server/function.php",
		async:true,
		data: { action: "neu", IDs:id}
		}).done(function( msg ) {
				//alert(msg)					
			$("#message").html(msg);
			$("#sent"+id).delay(1000).fadeOut();	
			$('#message').delay(3000).fadeOut();
			//location.reload();	
							
	});
}

function sent_negative(id){
	var IDs = $('#IDs').val();
	$.ajax({
		type: "GET",
		url: "server/function.php",
		async:true,
		data: { action: "neg", IDs:id}
		}).done(function( msg ) {
				//alert(msg)					
			$("#message").html(msg);
			$("#sent"+id).delay(1000).fadeOut();	
			$('#message').delay(3000).fadeOut();
			//location.reload();	
							
	});
}

function Sdelete(id){
	
	$.ajax({
		type: "GET",
		url: "function.php",
		async:true,
		data: { action: "Sdelete", id:id}
		}).done(function( msg ) {
				//alert(msg)					
			$("#message").html(msg);
			$("#sent"+id).delay(1000).fadeOut();	
			$('#message').delay(3000).fadeOut();
							
	});
}

function word_delete(id){
	
	$.ajax({
		type: "GET",
		url: "function.php",
		async:true,
		data: { action: "word_delete", id:id}
		}).done(function( msg ) {
				//alert(msg)					
		
			location.reload();				
	});
}

function Next_sentences(){
	
	
		location.reload();
		
}

function adjdelete(id){
	
	$.ajax({
		type: "GET",
		url: "server/function.php",
		async:true,
		data: { action: "adj_delete", id:id}
		}).done(function( msg ) {
				//alert(msg)					
			$("#message").html(msg);
			$("#sent"+id).delay(1000).fadeOut();	
			$('#message').delay(3000).fadeOut();
			//location.reload();					
	});
}

function SENdelete(id){
	
	$.ajax({
		type: "GET",
		url: "server/function.php",
		async:true,
		data: { action: "Sdelete", id:id}
		}).done(function( msg ) {
				//alert(msg)					
			$("#message").html(msg);
			$("#sent"+id).delay(1000).fadeOut();	
			$('#message').delay(3000).fadeOut();
			//location.reload();					
	});
}

function findtweets(){
	var Tuser = $('#Tuser').val();
	var Tamount = $('#Tamount').val();
	$.ajax({
		type: "GET",
		url: "tweet.php",
		async:true,
		data: { action: "insert", Tuser:Tuser, Tamount:Tamount}
		}).done(function( msg ) {
				//alert(msg)					
			$("#Tresults").html(msg);
			
							
	});
}

function findword(){
	var word = $('#word').val();
	
	$.ajax({
		type: "GET",
		url: "server/function.php",
		async:true,
		data: { action: "find-word", word:word}
		}).done(function( msg ) {
				alert(msg);					
			$("#wresults").html(msg);
			
							
	});
}
