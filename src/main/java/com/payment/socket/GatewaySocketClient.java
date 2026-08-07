package com.payment.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GatewaySocketClient {

    private static final Logger LOGGER=LoggerFactory.getLogger(GatewaySocketClient.class);

    public String sendRequest(String request) {

        LOGGER.debug("Sending request to endpoint");

        try {

            Socket socket = new Socket("localhost", 4000);

            LOGGER.info("Connected to endpoint successfully");

            PrintWriter writer =
                    new PrintWriter(socket.getOutputStream(), true);

            writer.println(request);

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

            String response = reader.readLine();

            if(response== null ||response.isEmpty()){
                LOGGER.warn("Received unexpected response from endpoint");
            }

            socket.close();

            return response;

        } catch (Exception e) {
            LOGGER.error("Gateway connection Failed",e);
            return "Gateway Connection Failed";
        }
    }
}
