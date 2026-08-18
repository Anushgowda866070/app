package com.payment.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.socket.GatewaySocketClient;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Servlet implements HttpHandler {

    private static final Logger LOGGER =
            Logger.getLogger(Servlet.class.getName());

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        exchange.getRequestBody(),
                        StandardCharsets.UTF_8));
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        LOGGER.info("Request received from client");

        GatewaySocketClient gatewaySocketClient = new GatewaySocketClient();
        String response;
        int statusCode;
        try {
            response = gatewaySocketClient.sendRequest(
                    requestBody.toString());

            LOGGER.info("Response received from endpoint");

            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode responseNode= objectMapper.readTree(response);
            String responseCode=responseNode.path("responseCode")
                    .asText(null);
            if("00".equals(responseCode)){
                statusCode=200;
            }else{
                statusCode=400;
            }

        } catch (Exception e) {
            LOGGER.severe("Internal server error: " + e.getMessage());
            response = "Internal Server Error";
            statusCode = 500;
        }

        byte[] responseBytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json");

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}