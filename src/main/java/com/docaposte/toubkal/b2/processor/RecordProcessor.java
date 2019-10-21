package com.docaposte.toubkal.b2.processor;

import com.docaposte.toubkal.b2.model.Type000;
import com.docaposte.toubkal.b2.model.WriterSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RecordProcessor implements ItemProcessor<Type000, WriterSO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordProcessor.class);

    @Override
    public WriterSO process(final Type000 item) throws Exception {
        LOGGER.info("Processing Record: {}", item);
        WriterSO writerSo = new WriterSO();
        Files.write(Paths.get("b2.txt"), item.toString().getBytes());
        writerSo.setRandomNum(String.valueOf(Math.random()).substring(3, 8));
        LOGGER.info("Processed Writer: {}", writerSo);
        return writerSo;
    }
}

