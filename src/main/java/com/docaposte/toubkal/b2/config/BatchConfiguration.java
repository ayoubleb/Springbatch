package com.docaposte.toubkal.b2.config;

import java.sql.ResultSet;

import javax.sql.DataSource;

import com.docaposte.toubkal.b2.listener.JobCompletionNotificationListener;
import com.docaposte.toubkal.b2.model.RecordSO;
import com.docaposte.toubkal.b2.model.Type000;
import com.docaposte.toubkal.b2.model.WriterSO;
import com.docaposte.toubkal.b2.processor.RecordProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ItemReader<Type000> reader() {
        return new JdbcCursorItemReaderBuilder<Type000>().name("the-reader")
                .sql("select * from type000").dataSource(dataSource)
                .rowMapper((ResultSet resultSet, int rowNum) -> {
                    if (!(resultSet.isAfterLast()) && !(resultSet.isBeforeFirst())) {
                        RecordSO recordSO = new RecordSO();
                        Type000 type0 = new Type000();
                        type0.setTypeEnregistrement(resultSet.getInt("typeEnregistrement"));
                        type0.setTypeEmetteur(resultSet.getString("typeEmetteur"));
                        type0.setNumEmetteur(resultSet.getInt("numEmetteur"));
                        type0.setBlanc(resultSet.getString("blanc"));
                        type0.setTypeDest(resultSet.getString("TypeDest"));
                        type0.setNumDest(resultSet.getInt("numDest"));
                        type0.setDateReceptionFluxFrontal(resultSet.getString("dateReceptionFluxFrontal"));
                        type0.setApplication(resultSet.getString("application"));
                        type0.setIdFichier(resultSet.getString("idFichier"));
                        type0.setDateCreationFichier(resultSet.getInt("dateCreationFichier"));//int
                        type0.setNorme(resultSet.getString("norme"));
                        type0.setVersionNorme(resultSet.getString("versionNorme"));
                        type0.setBlanc2(resultSet.getString("blanc2"));
                        type0.setBlanc3(resultSet.getString("blanc3"));
                        type0.setBlanc4(resultSet.getString("blanc4"));
                        type0.setBlanc5(resultSet.getString("blanc5"));
                        type0.setLongueurEnregistrement(resultSet.getInt("longueurEnregistrement"));//int
                        type0.setBlanc6(resultSet.getString("blanc6"));
                        type0.setZoneMessage(resultSet.getString("zoneMessage"));
                      /*  recordSO.setFirstName(resultSet.getString("firstName"));
                        recordSO.setLastName(resultSet.getString("lastname"));
                        recordSO.setId(resultSet.getInt("Id"));
                        recordSO.setRandomNum(resultSet.getString("random_num"));*/
                        LOGGER.info("RowMapper record : {}", recordSO);
                        return type0;
                    } else {
                        LOGGER.info("Returning null from rowMapper");
                        return null;
                    }
                }).build();
    }

    @Bean
    public ItemProcessor<Type000, WriterSO> processor() {
        return new RecordProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<WriterSO> writer(DataSource dataSource, ItemPreparedStatementSetter<WriterSO> setter) {
        return new JdbcBatchItemWriterBuilder<WriterSO>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .itemPreparedStatementSetter(setter)
                .sql("insert into writer (id, full_name, random_num) values (?,?,?)").dataSource(dataSource).build();
    }

    @Bean
    public ItemPreparedStatementSetter<WriterSO> setter() {
        return (item, ps) -> {
            ps.setLong(1, item.getId());
            ps.setString(2, item.getFullName());
            ps.setString(3, item.getRandomNum());
        };
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1)
                .end().build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<WriterSO> writer, ItemReader<Type000> reader) {
        return stepBuilderFactory.get("step1").<Type000, WriterSO>chunk(5).reader(reader).processor(processor())
                .writer(writer).build();
    }
}
