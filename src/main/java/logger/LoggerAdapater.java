package logger;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerAdapater {

    private static final LoggerAdapater loggerAdapater = new LoggerAdapater();
    private final Logger logger;

    private final LinkedList<String> FileNamePool = new LinkedList<>();

    private final LinkedList<String> DirNamePool = new LinkedList<>();


    private final int maxNum = 4;

    private int logNums = 0;

    private LoggerAdapater(){
        this.logger = Logger.getLogger("server");
        File LogsDir = new File("logs");
        File FirstLogDir = new File("logs/first");
        File SecondLogDir = new File("logs/second");
        File ThirdLogDir = new File("logs/third");
        LogsDir.mkdir();
        FirstLogDir.mkdir();
        SecondLogDir.mkdir();
        ThirdLogDir.mkdir();
        DirNamePool.add("logs/first");
        DirNamePool.add("logs/second");
        DirNamePool.add("logs/third");
        FileNamePool.add("logs/first/MyFirstLog");
        FileNamePool.add("logs/second/MySecondLog");
        FileNamePool.add("logs/third/MyThirdLog");
    }


    public static LoggerAdapater getLoggerAdapter(){
        return loggerAdapater;
    }

    public synchronized String getLogDir(){
        logNums++;
        if(logNums > maxNum){
            Handler[] handlers = logger.getHandlers();
            for(var h : handlers){
                h.close();
                logger.removeHandler(h);
            }
            String fullFile = FileNamePool.getFirst();
            String fullDir = DirNamePool.getFirst();
            DirNamePool.removeFirst();
            DirNamePool.add(fullDir);
            FileNamePool.removeFirst();
            FileNamePool.add(fullFile);
            deleteFolder(new File(DirNamePool.getFirst()));
            new File(DirNamePool.getFirst()).mkdir();
            logNums = 1;
            return FileNamePool.getFirst();
        }
        else
            return FileNamePool.getFirst();
    }

    public void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    //System.out.println(f.getName() + f.delete());
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    public void log(String info){
        FileHandler fh;
        try {
            // This block configures the logger with handler and formatter
            fh = new FileHandler(getLogDir());
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            // the following statement is used to log any messages
            logger.info(info);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
