package com.zta.zta.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zta.zta.models.UserRequest;
import com.zta.zta.models.UserResponse;

@Service
public class UserService {
    @Autowired
	private KieSession session;
    
    public ResponseEntity<UserResponse> validation(@RequestBody UserRequest userRequest){     
		String token = userRequest.getToken();
		try {
			trustAllCertificates();
            URL url = new URL("https://localhost:9443/oauth2/userinfo");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

			System.out.println("oli");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                
                ObjectMapper objectMapper = new ObjectMapper();
                UserResponse userInfo = objectMapper.readValue(response.toString(), UserResponse.class);
                session.insert(userInfo);
                session.fireAllRules();
                
                if (userInfo.getIsValid()) return ResponseEntity.ok(userInfo);
                else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
                
            } catch(Exception e) {
				
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}

        } catch (IOException e) {
            e.printStackTrace();
        }
    
		return null;

    }

    private static void trustAllCertificates() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
