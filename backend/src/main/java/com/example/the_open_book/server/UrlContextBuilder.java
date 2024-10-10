package com.example.the_open_book.server;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.the_open_book.exception.ApplicationException;
import com.example.the_open_book.token.Token;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * UrlConextBuilder
 */
@Component
@Log4j2
@NoArgsConstructor
public class UrlContextBuilder {

  @Value("${server.servlet.context-path}")
  private String contextPath;
  @Value("${server.port}")
  private Integer serverPort;

  // @Value("${server.address}")
  // private String serverAddress;

  public String emailCofirmation(Token token)  {
    String host;
    try {
      host = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      throw new ApplicationException("Host not found");
    }
    final String clienUrl =  "http://localhost:4200";
    final String serverContext = contextPath + "/auth";
    final String severUrl=  String.format("%s:%s",host,  serverPort)+  serverContext;
    // final String url = severUrl + "/activate-account?code=" +  token.getToken();
    try {
            var url = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(host)
            .port(serverPort)
            .path(contextPath)
            .path("/auth")
            .path("/activate-account")
            .queryParam("code", token.getToken())
            .build()
            .toUri()
            .toURL();
            return url.toString();
    } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

            return "";
    };

}
