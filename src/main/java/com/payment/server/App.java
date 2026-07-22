package com.payment.server;

import com.payment.servlet.PaymentServlet;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/sale", new PaymentServlet());

        server.setExecutor(null);

        System.out.println("Server Started on Port 8080");

        server.start();
    }
}
