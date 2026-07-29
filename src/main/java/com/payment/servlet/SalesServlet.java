package com.payment.servlet;

import com.payment.socket.GatewaySocketClient;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SalesServlet implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        System.out.println("Request from Postman :" + requestBody);
        GatewaySocketClient gatewaySocketClient=new GatewaySocketClient();
        String response=gatewaySocketClient.sendRequest(requestBody.toString());

        System.out.println("Response from gateway :" + response);

        exchange.sendResponseHeaders(200, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}





