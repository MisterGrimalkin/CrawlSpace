var baseUrl = "192.168.0.2";
var port = "8001";

function onLoad() {
    setInterval(function() { updateCurrentScene();}, 250);
}

function updateCurrentScene() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if ( req.readyState==4 ) {
            var panel = document.getElementById("currentTime");
            while (panel.firstChild) {
                panel.removeChild(panel.firstChild);
            }
            var p = document.createElement("p");
            p.style.background = "#00AA99";
            p.style.width = "90%";
            p.style.height = "60px";
            p.style.fontFamily = "courier";
            p.style.color = "white";
            p.style.borderRadius = "10px";
            p.style.paddingTop = "9px";
            p.style.verticalAlign = "middle";
            p.style.fontSize = "28px";
            p.style.textAlign = "center";
            p.style.margin = "auto";
            p.style.marginBottom = "30px";
            if ( req.status==200 ) {
                var message;
                var showTime = parseInt(req.responseText);
                if ( showTime<0 ) {
                    message = "STOPPED";
                    p.style.background = "#FF3333";
                    document.getElementById("scene1").className = "btn-primary";
                    document.getElementById("scene2").className = "btn-primary";
                    document.getElementById("scene3").className = "btn-primary";
                    document.getElementById("scene4").className = "btn-primary";
                } else {
                    message = req.responseText + " sec";
                    p.style.background = "#118811";
                    if ( showTime >= 75 ) {
                        document.getElementById("scene1").className = "btn-primary";
                        document.getElementById("scene2").className = "btn-primary";
                        document.getElementById("scene3").className = "btn-primary";
                        document.getElementById("scene4").className = "btn-success";
                    } else
                    if ( showTime >= 43 ) {
                        document.getElementById("scene1").className = "btn-primary";
                        document.getElementById("scene2").className = "btn-primary";
                        document.getElementById("scene3").className = "btn-success";
                        document.getElementById("scene4").className = "btn-primary";
                    } else
                    if ( showTime >= 24 ) {
                        document.getElementById("scene1").className = "btn-primary";
                        document.getElementById("scene2").className = "btn-success";
                        document.getElementById("scene3").className = "btn-primary";
                        document.getElementById("scene4").className = "btn-primary";
                    } else
                    if ( showTime >= 0 ) {
                        document.getElementById("scene1").className = "btn-success";
                        document.getElementById("scene2").className = "btn-primary";
                        document.getElementById("scene3").className = "btn-primary";
                        document.getElementById("scene4").className = "btn-primary";
                    } else {
                        document.getElementById("scene1").className = "btn-primary";
                        document.getElementById("scene2").className = "btn-primary";
                        document.getElementById("scene3").className = "btn-primary";
                        document.getElementById("scene4").className = "btn-primary";
                    }
                }
                var t = document.createTextNode(message);
                p.appendChild(t);
                panel.appendChild(p);
            } else {
                document.getElementById("scene1").className = "btn-primary";
                document.getElementById("scene2").className = "btn-primary";
                document.getElementById("scene3").className = "btn-primary";
                document.getElementById("scene4").className = "btn-primary";
                var t = document.createTextNode("OFFLINE");
                p.style.background = "#888888";
                p.appendChild(t);
                panel.appendChild(p);
            }
        }
    }
    req.open("GET", url("current-time"), true);
    req.send();
}

function startShow() {
    var req = new XMLHttpRequest();
    req.open("POST", url("start"), true);
    req.send();
}

function stopShow() {
    var req = new XMLHttpRequest();
    req.open("POST", url("stop"), true);
    req.send();
}

function jumpTo(time) {
    var req = new XMLHttpRequest();
    req.open("POST", url("jump-to") + "?time="+time, true);
    req.send();
}

function panic() {
    var req = new XMLHttpRequest();
    req.open("POST", url("panic"), true);
    req.send();
}

function shutdown() {
    var req = new XMLHttpRequest();
    req.open("POST", url("shutdown"), true);
    req.send();
}

function url(path) {
    return "http://" + baseUrl + ":" + port + "/crawlspace/" + path;
}

