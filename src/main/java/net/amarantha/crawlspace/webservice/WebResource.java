package net.amarantha.crawlspace.webservice;

import net.amarantha.crawlspace.event.EventManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("crawlspace")
public class WebResource {

    private static EventManager events;

    public static void setEventManager(EventManager events) {
        WebResource.events = events;
    }

    @POST
    @Path("panic")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response panic() {
        events.panic();
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity("Panic Scene Activated")
                .build();
    }

    @POST
    @Path("stop")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response stop() {
        events.stopShow();
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity("Stopped")
                .build();
    }

    @POST
    @Path("start")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response start() {
        events.startShow();
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity("Started")
                .build();
    }

    @GET
    @Path("current-time")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response getCurrentScene() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity(events.isRunning() ? events.getCurrentShowTime() : -1)
                .build();
    }

    @POST
    @Path("jump-to")
    @Produces(MediaType.TEXT_PLAIN)
    public static Response jumpTo(@QueryParam("time") double time) {
        events.jumpTo(time);
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity(events.getCurrentShowTime())
                .build();
    }

    @POST
    @Path("shutdown")
    public static void shutdown() {
        events.stopShow();
        System.exit(0);
    }

}
