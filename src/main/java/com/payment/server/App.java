package com.payment.server;

import com.payment.servlet.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/sale", new Servlet());
        server.createContext("/refund", new Servlet());
        server.createContext("/verify", new Servlet());
        server.createContext("/void", new Servlet());

        server.setExecutor(null);

        System.out.println("Server Started on Port 8080");

        server.start();
    }
}
