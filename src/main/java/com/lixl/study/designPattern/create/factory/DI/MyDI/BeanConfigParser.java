package com.lixl.study.designPattern.create.factory.DI.MyDI;

import java.io.InputStream;
import java.util.List;

public interface BeanConfigParser {
    List parse(InputStream inputStream);

    List parse(String configContent);
}
