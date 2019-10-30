package BE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Write output from started process into a log file
 *
 * @author Peter
 */
public class ProcessOutputPrinter implements Runnable {

    private final AtomicBoolean terminate = new AtomicBoolean(false);

    private Thread t;
    private Process process; // The process whose output will be written
    private File log;
    private String sqlConfId;
    private String opcConfId;

    @Override
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String timestamp = LocalDateTime.now().toString().replace(':', '-').replace('.', '-');

        log = new File(System.getProperty("user.home") + "\\Documents\\SeaconDataCollector\\log\\process_" + timestamp + ".log");
        log.getParentFile().mkdirs();

        String line;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(log))) {
            while (!this.terminate.get() && (line = in.readLine()) != null) {
                System.out.println(line);
                bw.append(line);
                bw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessOutputPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set the process
     *
     * @param p The process whose output will be written
     */
    public void setProcess(Process p) {
        this.process = p;
    }

    /**
     * Starts printing the output of the process.
     *
     * @throws IllegalAccessException
     */
    public void start() throws IllegalAccessException {
        if (process == null) {
            throw new IllegalAccessException("Process is null!");
        }
        t = new Thread(this);
        t.start();
    }

    public void terminate() {
        this.terminate.set(true);
    }

    public String getSqlConfigId() {
        return sqlConfId;
    }

    public void setSqlConfigId(String sqlConfigId) {
        this.sqlConfId = sqlConfigId;
    }

    public String getOpcConfigId() {
        return opcConfId;
    }

    public void setOpcConfigId(String opcConfigId) {
        this.opcConfId = opcConfigId;
    }
}
