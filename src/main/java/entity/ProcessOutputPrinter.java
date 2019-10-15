package entity;

import java.awt.TextArea;
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
import seacon.datacollectorweb.process_output_display;

/**
 * Write output from started process into a log file
 * @author Peter
 */
public class ProcessOutputPrinter implements Runnable {
    private AtomicBoolean terminate = new AtomicBoolean(false);

    private Thread t;
    private Process process; // The process whose output will be written
    
    private File log;
    
    /**
     * Set the process
     * @param p The process whose output will be written
     */
    public void setProcess(Process p) {
        this.process = p;
    }
    
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
    
    @Override
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        
        LocalDateTime time = LocalDateTime.now();
        
        log = new File("C:\\Users\\Peter\\Documents\\NetBeansProjects\\DataCollectorWeb\\process_" + time.toString() + ".log");
        
        String line;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(log))) {
            try {
                while (!this.terminate.get() && (line = in.readLine()) != null) {
                    System.out.println(line);
                    bw.append(line);
                    bw.newLine();
                }       
            } catch (IOException ex) {
                Logger.getLogger(process_output_display.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessOutputPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
