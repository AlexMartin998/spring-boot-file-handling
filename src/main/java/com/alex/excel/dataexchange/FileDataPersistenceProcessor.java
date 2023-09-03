package com.alex.excel.dataexchange;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


public interface FileDataPersistenceProcessor<T> {

    boolean isValidFile(MultipartFile file);

    List<T> processData(InputStream inputStream);

}
