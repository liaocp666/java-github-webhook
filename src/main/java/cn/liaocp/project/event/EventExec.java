package cn.liaocp.project.event;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

/**
 * @author LiaoChuning
 */
public class EventExec {

    private static final Logger LOGGER = Logger.getLogger(EventExec.class.getName());

    public void exec (String event) throws IOException {
        File file = new File(String.format("sh/%s.sh", event));
        if (!file.exists()) {
            LOGGER.warning("文件不存在");
            return;
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("sh", "-c", file.getAbsolutePath());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        File outputFile =  new File(String.format("log/%s.log", event));
        Files.copy(inputStream, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

}
