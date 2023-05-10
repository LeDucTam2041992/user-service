package pro.userservice.config.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.springframework.http.converter.StringHttpMessageConverter.DEFAULT_CHARSET;

public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

  private static final int MAX_LENGTH_BODY = 800;
  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomClientHttpRequestInterceptor.class);

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    traceRequest(request, body);
    var response = execution.execute(request, body);
    traceResponse(response, request);
    return response;
  }

  private void traceRequest(HttpRequest request, byte[] body) {
    String bodyRequest = new String(body, StandardCharsets.UTF_8);
    if (bodyRequest.length() > MAX_LENGTH_BODY) {
      bodyRequest = bodyRequest.substring(0, MAX_LENGTH_BODY);
    }
    LOGGER.info(
        "RestTemplate request logging: URI: {} Method: {} Headers: {} Body: {}",
        request.getURI(),
        request.getMethod(),
        request.getHeaders(),
        bodyRequest);
  }

  private void traceResponse(ClientHttpResponse response, HttpRequest request) throws IOException {
    String bodyResponse =
        new String(StreamUtils.copyToByteArray(response.getBody()), getCharset(response));
    if (bodyResponse.length() > MAX_LENGTH_BODY) {
      bodyResponse = bodyResponse.substring(0, MAX_LENGTH_BODY);
    }
    LOGGER.info(
        "RestTemplate response logging: URI: {} Method: {} Status code: {} Headers: {} Body: {}",
        request.getURI(),
        request.getMethod(),
        response.getStatusCode(),
        response.getHeaders(),
        bodyResponse);
  }

  private Charset getCharset(HttpMessage message) {
    return Optional.ofNullable(message.getHeaders().getContentType())
        .map(MediaType::getCharset)
        .orElse(DEFAULT_CHARSET);
  }
}
