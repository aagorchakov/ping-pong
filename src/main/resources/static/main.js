const players = [
	document.getElementById('player1'),
	document.getElementById('player2')
];
const ball = document.getElementById('ball');

const protocol = location.protocol === 'https:' ? 'wss' : 'ws';
const websocket = new WebSocket(protocol + '://' + location.host + '/ws');

let connected = false;
let waitingResponse = false;
let playersOnline = 1;

websocket.onopen = event => {
	connected = true;
	document.title = 'Connected';
}

websocket.onerror = event => {
	alert('Error');
	console.log(event);
}

websocket.onclose = event => {
	connected = false;
	document.title = 'Disconnected';
}

websocket.onmessage = data => {
	waitingResponse = false;
	const response = JSON.parse(data.data);
	playersOnline = response.players.length;
	for (let i = 0; i < playersOnline; i++) {
		players[i].style.left = response.players[i].x + 'px';
		players[i].style.top = response.players[i].y + 'px';
	}
	ball.style.left = response.ball.x + 'px';
	ball.style.top = response.ball.y + 'px';
	if (playersOnline === 2) {
		document.title = response.players[0].score + '  :  ' + response.players[1].score;
		players[1].style.display = 'block';
		ball.style.display = 'block';
	}
}

window.onmousemove = event => {
	if (connected && !waitingResponse) {
		waitingResponse = true;
		websocket.send(event.pageY);
	}
}