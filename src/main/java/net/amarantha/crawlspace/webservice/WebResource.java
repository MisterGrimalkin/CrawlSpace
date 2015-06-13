package net.amarantha.crawlspace.webservice;

import net.amarantha.crawlspace.scene.Scene;
import net.amarantha.crawlspace.scene.SceneManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class WebResource {

    private static SceneManager sceneManager;

    public static void bindSceneManager(SceneManager sceneManager) {
        WebResource.sceneManager = sceneManager;
    }

    @POST
    @Path("panic")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response panic() {
        sceneManager.panic();
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity("Panic Scene Activated")
                .build();
    }

    @POST
    @Path("stop")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response stop() {
        sceneManager.stop();
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity("Stopped")
                .build();
    }

    @POST
    @Path("start")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response start() {
        sceneManager.start();
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity("Started")
                .build();
    }

    @GET
    @Path("current-scene")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response getCurrentScene() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity(sceneManager.getCurrentScene()+1)
                .build();
    }

    @GET
    @Path("scenes")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response getScenes() {
        JSONObject json = new JSONObject();
        JSONArray ja = new JSONArray();
        List<Scene> scenes = sceneManager.getScenes();
        for ( int i=0; i<scenes.size(); i++ ) {
            Scene scene = scenes.get(i);
            JSONObject obj = new JSONObject();
            obj.put("id", i+1);
            obj.put("name", scene.getName());
            ja.add(obj);
        }
        json.put("scenes", ja);
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity(json.toString())
                .build();
    }

    @POST
    @Path("load-scene")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response loadScene(@QueryParam("id") int id) {
        if ( sceneManager.loadScene(id-1) ) {
            return Response.ok()
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Scene Loaded")
                    .build();
        } else {
            return Response.serverError()
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Scene Not Found")
                    .build();
        }
    }

    @POST
    @Path("next-scene")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response nextScene() {
        sceneManager.next();
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity("Scene Advanced")
                .build();
    }

    @POST
    @Path("shutdown")
    public static void shutdown() {
        System.exit(0);
    }

}
