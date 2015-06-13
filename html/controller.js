var baseUrl = "192.168.0.18";
var port = "8000";

function onLoad() {
    setInterval(function() { updateCurrentScene();}, 1000);
}

function loadScenes() {
console.log("what");
    var req = new XMLHttpRequest();
    var panel = document.getElementById("sceneList");
    while (panel.firstChild) {
        panel.removeChild(panel.firstChild);
    }
    req.onreadystatechange = function() {
        if (req.readyState==4 ) {
            if ( req.status==200 ) {
                var json = JSON.parse(req.responseText);
                var scenes = json.scenes;
                for ( var i=0; i<scenes.length; i++ ) {
                    var scene = scenes[i];
                    var p = document.createElement("P");
                    var btn = document.createElement("BUTTON");
                    var t = document.createTextNode(scene.sceneName);
                    btn.id="scene"+baseUrl+scene.sceneId;
                    btn.appendChild(t);
     	            btn.className = "btn-primary";
                    btn.style.width = "100%";
                    btn.onclick = createLoadSceneFunction(scene.sceneId);
                    p.appendChild(btn);
                    panel.appendChild(p);
                }
            } else {
                var t = document.createTextNode("ERROR: Cannot Connect!");
                panel.appendChild(t);
            }
        }
    }
    req.open("GET", url("scene"), true);
    req.send();
}

function createLoadSceneFunction(id) {
    return function() {
        loadScene(id);
    }
}

var currentScene = 1;

function updateCurrentScene() {
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if ( req.readyState==4 ) {
            if ( req.status==200 ) {
                var oldBtn = document.getElementById("scene"+currentScene);
                if ( oldBtn != null ) {
                    oldBtn.className = "btn-primary";
                }
                var newBtn = document.getElementById("scene"+req.responseText);
                currentScene = req.responseText;
                if ( newBtn != null ) {
                    newBtn.className = "btn-success";
                }
            }
        }
    }
    req.open("GET", url("current-scene"), true);
    req.send();
}

function loadScene(id) {
    var req = new XMLHttpRequest();
    req.open("POST", url("load-scene") + "?id=" + id, true);
    req.send();
}

function start() {
    var req = new XMLHttpRequest();
    req.open("POST", url("start"), true);
    req.send();
}

function next() {
    var req = new XMLHttpRequest();
    req.open("POST", url("next-scene"), true);
    req.send();
}

function stop() {
    var req = new XMLHttpRequest();
    req.open("POST", url("stop"), true);
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
    return "http://" + baseUrl + ":" + port + "/" + path;
}

