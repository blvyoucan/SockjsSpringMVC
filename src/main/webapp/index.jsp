<!DOCTYPE>
<%@ page import="com.sucsoft.fivewater2.utils.PathUtil" %>
<html lang="zh">
<head>
<base href="<%=PathUtil.httpPath(request) %>" />
<meta charset="UTF-8">
<script src="js/sockjs-1.1.1.min.js"></script>
</head>
<body>
<h2>Hello World!</h2>
<script>
<%session.setAttribute("CURRENT_USERKEY_CONSTANT","admin");%>
var ws;
if( typeof window.WebSocket !='undefined'){
	ws = new WebSocket( '<%=PathUtil.websocketPath(request)%>socketService');
}else if( typeof window.MozWebSocket != 'undefined' ){
	ws = new MozSocket('<%=PathUtil.websocketPath(request)%>socketService');
}else{
	ws = new SockJS('<%=PathUtil.httpPath(request) %>sockjs/socketService');
}
ws.onopen=function(evt){
	
};
ws.onmessage=function(evt){
	console.log(evt);
	window.alert(evt.data);
};
ws.onerror=function(evt){
	
};
ws.onclose=function(evt){
	window.alert('closed');
};
window.onunload=function(){
	if( ws != null ){
		ws.close();
		ws = null;
	}
}
</script>
</body>
</html>
