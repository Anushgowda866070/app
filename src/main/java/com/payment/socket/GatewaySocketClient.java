package com.payment.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GatewaySocketClient {

    public String sendRequest(String request) {

        try {

            Socket socket = new Socket("localhost", 4000);

            PrintWriter writer =
                    new PrintWriter(socket.getOutputStream(), true);

            writer.println(request);

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

            String response = reader.readLine();

            socket.close();

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return "Gateway Connection Failed";
        }
    }
}
