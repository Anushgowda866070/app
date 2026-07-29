package com.payment.server;

import com.payment.servlet.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/sale", new SalesServlet());
        server.createContext("/refund", new RefundServlet());
        server.createContext("/verify", new VerifyServlet());
        server.createContext("/void", new VoidServlet());

        server.setExecutor(null);

        System.out.println("Server Started on Port 8080");

        server.start();
    }
}
