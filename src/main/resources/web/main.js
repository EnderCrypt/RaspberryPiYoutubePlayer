/*
var element_url_input = null;
var element_url_submit = null;

var element_playlist = null;
var element_log = null;

function init()
{
	element_url_input = Document.getElementById("url_input");
	element_url_submit = Document.getElementById("url_submit");
	
	element_playlist = Document.getElementById("playlist");
	element_log = Document.getElementById("log");
	
	console.log(element_url_input);
	console.log(element_url_submit);
	console.log(element_playlist);
	console.log(element_log);
}
 */

function init() {
	// get all id's
	var all = document.getElementsByTagName("*");
	for (var i = 0, max = all.length; i < max; i++) {
		var element = all[i];
		if (element.id != "") {
			window["element_" + element.id] = element;
		}
	}
	
	// buttons
	$.get("/api/buttons", function(buttons) {
		for (var i = 0, max = buttons.length; i < max; i++) {
			var button = buttons[i];
			element_buttons.innerHTML += "<div class=\"player_button\" onclick=\"sendKey('"+button.code+"')\">"+button.name+"</div>";
		}
		
	});
	
	// submit
	element_url_submit.onclick = onSubmit;
	
	// update
	setInterval(update, 2500);
	update();
}

function sendKey(code)
{
	$.post("/api/console", {
		code : code
	});
}

function onSubmit() {
	var value = element_url_input.value;
	element_url_input.value = "";
	$.post("/api/video/add", {
		url : value
	});
	update();
}

function update() {
	requestPlaylist();
	requestConsole();
}

function requestPlaylist() {
	$.get("/api/video/list", function(data) {
		var result = "";
		for (var i = 0, max = data.length; i < max; i++) {
			var url = data[i].url;
			result = result + url + "\n";
		}
		element_playlist.value = result;
	});
}

function requestConsole() {
	$.get("/api/console", function(data) {
		if (data == undefined) {
			data = "<no data>";
		}
		element_console.value = data;
	});
}

document.addEventListener("DOMContentLoaded", function() {
	init();
});