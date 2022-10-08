package cn.liaocp.project.handler;

import cn.liaocp.project.App;
import cn.liaocp.project.event.EventFactory;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * @author Kent Liao
 */
public class WebHookHandler implements HttpHandler {

    private static final Logger LOGGER = Logger.getLogger(WebHookHandler.class.getName());

    private static final String HEADER_SIGNATURE_KEY = "X-hub-signature-256";

    private static final String GITHUB_SIGNATURE_PREFIX = "sha256=";

    private static final String ALGORITHM = "HmacSHA256";

    private static final String HEADER_EVENT_KEY = "X-GitHub-Event";

    public void handle(HttpExchange exchange) {
        try {
            if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
                throw new RuntimeException("非POST请求");
            }
            if (verifySignature(exchange)) {
                handleSuccessResponse(exchange, "ok");
                handleEvent(exchange);
            } else {
                throw new RuntimeException("签名验证失败");
            }
        } catch (Exception e) {
            handleErrorResponse(exchange, "签名验证失败");
        }
    }

    private void handleEvent(HttpExchange exchange) throws IOException {
        String event = exchange.getRequestHeaders().getFirst(HEADER_EVENT_KEY);
        new EventFactory().getEvent(event).run(exchange);
    }

    private Boolean verifySignature(HttpExchange exchange) throws Exception {
        String requestBody = inputStream2String(exchange.getRequestBody());
        Headers header = exchange.getRequestHeaders();
        String githubSignature = header.getFirst(HEADER_SIGNATURE_KEY);
        if (githubSignature == null || githubSignature.length() == 0) {
            throw new RuntimeException("未找到请求头 X-Hub-Signature-256");
        }
        Mac sha256_HMAC = Mac.getInstance(ALGORITHM);
        sha256_HMAC.init(new SecretKeySpec(App.secret.getBytes(), ALGORITHM));
        byte[] result = sha256_HMAC.doFinal(requestBody.getBytes());
        return githubSignature.equalsIgnoreCase(GITHUB_SIGNATURE_PREFIX + DatatypeConverter.printHexBinary(result));
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (int result = bis.read(); result != -1; result = bis.read()) {
            buf.write((byte) result);
        }
        return buf.toString(StandardCharsets.UTF_8.name());
    }

    private void handleErrorResponse(HttpExchange exchange, String message) {
        try (OutputStream outputStream = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(500, message.getBytes(StandardCharsets.UTF_8).length);
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSuccessResponse(HttpExchange exchange, String responseBody) {
        try (OutputStream outputStream = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(200, responseBody.getBytes(StandardCharsets.UTF_8).length);
            outputStream.write(responseBody.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOGGER.warning(e.getLocalizedMessage());
        }
    }

}
