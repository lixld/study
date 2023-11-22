package com.lixl.study.abstractAndInterface.abstractDemo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileLogger extends Logger {
    private Writer fileWriter;

    public FileLogger(String name, boolean enabled, Level minPermittedLevel, String filepath) throws IOException {
        super(name, enabled, minPermittedLevel);
        this.fileWriter = new FileWriter(filepath);
    }

    @Override
    public void doLog(Level level, String mesage) { // 格式化level和message,输出到日志文件
//        fileWriter.write();
    }
}
