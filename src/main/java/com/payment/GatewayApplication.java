package com.payment;

import com.payment.servlet.*;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class GatewayApplication {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/gateway/sale", new Servlet());
        server.createContext("/gateway/refund", new Servlet());
        server.createContext("/gateway/verify", new Servlet());
        server.createContext("/gateway/void", new Servlet());

        server.setExecutor(null);

        System.out.println("Server Started on Port 8080");

        server.start();
    }
}
