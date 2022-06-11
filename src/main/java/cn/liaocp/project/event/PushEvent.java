package cn.liaocp.project.event;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

/**
 * @author Kent Liao
 */
public class PushEvent {

    private static final Logger LOGGER = Logger.getLogger(PushEvent.class.getName());

    public void run() throws IOException {
        File file = new File("sh/push.sh");
        if (!file.exists()) {
            LOGGER.warning("文件不存在");
            return;
        }
        
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("sh", "-c", file.getAbsolutePath());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        File outputFile =  new File("log/push.log");
        Files.copy(inputStream, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

}
