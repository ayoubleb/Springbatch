[![Build Status](https://travis-ci.org/geekyjaat/spring-batch.svg?branch=master)](https://travis-ci.org/geekyjaat/spring-batch)

# spring-batch
This is a spring batch demo application using the `org.springframework.boot:spring-boot-starter-batch`. The batch is pulling records from a database, process them and insert the processed records back into a new database table.

## Description
The project aims to provide a simple example of how to use spring batch to read records from a database table,
process them and insert the results into another database table. We will use [gradle](https://gradle.org) as our build tool,
[MySQL](http://www.mysql.com) as our database.

### High-Level Design
1. _Input for the batch_ - the batch will read records from `reader` table - `id`, `firstName`, `lastName` and `random_num`.
2. _Processing_ - the batch will process input and join the `firstName` and `lastName` to generate the `full_name` attribute.
3. _Output of the batch_ - the batch will then output and write the data into `writer` table with attributes - `id`, `full_name` and `random_num` (generated at run time).


## Getting Started
You can clone this project `git clone https://github.com/geekyjaat/spring-batch.git`, run it and expand it further. Or read the tutorial below to start from scratch.

### Setting the build tool
we will set up the `build.gradle` in project directory with plugin `java` and `spring-boot`. Also, we will add following dependencies -

1. `spring-boot-starter-batch` - to pull in spring batch classes.
2. `spring-boot-starter-data-jpa` - to handle the datasource and pull in `spring-jdbc` dependencies.
3. `mysql-connector-java` - java connector for MySQL database.

`spring-boot` plugin that collects all the jars on the classpath and builds a single, runnable archive jar, which makes it more easier to execute the service.

Here is what our build.gradle looks like -
> `build.gradle`

```groovy
    group 'com.geeky.batch'
    version '1.0.0'

    apply plugin: 'java'
    apply plugin: 'org.springframework.boot'
    apply plugin: 'io.spring.dependency-management'

    sourceCompatibility = 1.8
    targetCompatibility = 1.8

    jar {
        baseName = 'spring-batch'
    }

    buildscript {
        repositories {
            mavenCentral()
        }
        dependencies {
            classpath('org.springframework.boot:spring-boot-gradle-plugin:2.0.5.RELEASE')
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        compile('org.springframework.boot:spring-boot-starter-batch')
        compile('org.springframework.boot:spring-boot-starter-data-jpa')
        compile('mysql:mysql-connector-java:5.1.38')
        testCompile('junit:junit')
    }
```

### Model
We will add 2 classes to handle our model i.e. input and output data. Here, our input is `RecordSO` and output is `WriterSO`. See classes below which we created in package `com.barley.batch.model` in `main/java` -

#### RecordSO
> `src/main/java/com/barley/batch/model/RecordSO.java`

```java
    public class RecordSO {

        private long id;
        private String firstName;
        private String lastName;
        private String randomNum;

        ...getter
        ...setter
    }
```

> `src/main/java/com/barley/batch/model/WriterSO.java`

#### WriterSO

```java
    public class WriterSO {

        private long id;
        private String fullName;
        private String randomNum;

        ...getter
        ...setter
    }
```

### Processor

Now we will set up the processor class which will implement spring interface `ItemProcessor<I, O>`. According to it, you receive an incoming `RecordSO` object, after which you transform it to a `WriterSO`.
> `src/main/java/com/barley/batch/processor/RecordProcessor.java`

```java
    public class RecordProcessor implements ItemProcessor<RecordSO, WriterSO> {

        private static final Logger LOGGER = LoggerFactory.getLogger(RecordProcessor.class);

        @Override
        public WriterSO process(RecordSO item) throws Exception {
            LOGGER.info("Processing Record: {}", item);
            WriterSO writerSo = new WriterSO();
            writerSo.setId(item.getId());
            writerSo.setFullName(item.getFirstName() + " " + item.getLastName());
            writerSo.setRandomNum(String.valueOf(Math.random()).substring(3, 8));
            LOGGER.info("Processed Writer: {}", writerSo);
            return writerSo;
        }
    }
```

### Application Properties
Now, we need to set up some spring datasource properties which will help spring configure primary datasource - create `application.properties` in `main/resources` -
> `src/main/resources/application.properties`

```
spring.batch.initialize-schema=ALWAYS
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/batch
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=false
# use below property only if you want spring to use user-configured sql create-drop databases tables. Skip it all tables/data already present.
spring.jpa.hibernate.ddl-auto=create-drop
```

### Primary Database
With spring auto-configuration, the datasource will be configured based `application.properties` and jars on the classpath. if you are using more than 1, you can set up one of datasources as `@Primary` `@Bean`.

### Hooking up batch framework
Now, we hook up spring batch components to link up all pieces together - we create the class `BatchConfiguration` and use this annotations -

1. `@Configuration` - this tell spring that its a configuration class.
2. `@EnableBatchProcessing` - this adds a lot of important beans which will support the batch.


```java
    @Configuration
    @EnableBatchProcessing
    public class BatchConfiguration {
    }
```

#### ItemReader
Below method creates an `ItemReader` which will read records from `reader` table from datasource and turn them into `RecordSO`.

```java
    @Bean
    public ItemReader<RecordSO> reader() {
        return new JdbcCursorItemReaderBuilder<RecordSO>().name("the-reader")
                .sql("select id, firstName, lastname, random_num from reader").dataSource(dataSource)
                .rowMapper((ResultSet resultSet, int rowNum) -> {
                    if (!(resultSet.isAfterLast()) && !(resultSet.isBeforeFirst())) {
                        RecordSO recordSO = new RecordSO();
                        recordSO.setFirstName(resultSet.getString("firstName"));
                        recordSO.setLastName(resultSet.getString("lastname"));
                        recordSO.setId(resultSet.getInt("Id"));
                        recordSO.setRandomNum(resultSet.getString("random_num"));

                        LOGGER.info("RowMapper record : {}", recordSO);
                        return recordSO;
                    } else {
                        LOGGER.info("Returning null from rowMapper");
                        return null;
                    }
                }).build();
    }
```

#### ItemWriter
Below method creates an `ItemWriter` which will be using the datasouce and will insert the `WriterSO` into `writer` table. We are setting up teh sql as well which `ItemWriter` will use to insert data into table.

```java
    @Bean
    public JdbcBatchItemWriter<WriterSO> writer(DataSource dataSource, ItemPreparedStatementSetter<WriterSO> setter) {
        return new JdbcBatchItemWriterBuilder<WriterSO>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .itemPreparedStatementSetter(setter)
                .sql("insert into writer (id, full_name, random_num) values (?,?,?)").dataSource(dataSource).build();
    }
```

##### ItemPreparedStatementSetter
In the writer above, we set a `ItemPreparedStatementSetter` which will help `ItemWriter` interpret the sql statement it is using.

```java
    @Bean
    public ItemPreparedStatementSetter<WriterSO> setter() {
        return (item, ps) -> {
            ps.setLong(1, item.getId());
            ps.setString(2, item.getFullName());
            ps.setString(3, item.getRandomNum());
        };
    }
```

#### ItemProcessor
Hook up our `RecordProcessor` in the batch config -

```java
    @Bean
    public ItemProcessor<RecordSO, WriterSO> processor() {
        return new RecordProcessor();
    }
```

#### Rest of batch config
Now Spring batch works with concept of jobs, and each job consist of steps where each step can involve a `ItemReader`, a `ItemProcessor` and a `ItemWriter`. So we are going to define a job and set up a step -

```java
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1)
                .end().build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<WriterSO> writer, ItemReader<RecordSO> reader) {
        return stepBuilderFactory.get("step1").<RecordSO, WriterSO>chunk(5).reader(reader).processor(processor())
                .writer(writer).build();
    }
```

Complete code -
> `src/main/java/com/barley/batch/config/BatchConfiguration.java`

### Listener
well, we are pretty much ready but we can configure a listener which helps us hook up into spring batch and get to know when the job is finished. This allows us to execute any logic we want to do after completion of batch.
> `src/main/java/com/barley/batch/listener/JobCompletionNotificationListener.java`

```java

    @Component
    public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

        private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        public void afterJob(JobExecution jobExecution) {
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("!!! JOB FINISHED! Time to verify the results");

                List<WriterSO> results = jdbcTemplate.query("SELECT id, full_name, random_num FROM writer", (rs, row) -> {
                    WriterSO writerSO = new WriterSO();
                    writerSO.setId(rs.getLong("id"));
                    writerSO.setFullName(rs.getString("full_name"));
                    writerSO.setRandomNum(rs.getString("random_num"));
                    return writerSO;
                });

                for (WriterSO writerSO : results) {
                    log.info("Found <" + writerSO + "> in the database.");
                }
            }
        }
    }
```

### Setting up our database tables -
Now, we will have spring create our database tables each time it runs. You can skip this step if you manually have created the tables or tables with data are already present.
> `src/main/resources/import.sql`

```sql
DROP TABLE IF EXISTS reader;
DROP TABLE IF EXISTS writer;

CREATE TABLE `reader` (`id` INT  NOT NULL AUTO_INCREMENT,`firstName`  VARCHAR(20) NULL,`lastName`   VARCHAR(20) NULL,`random_num` VARCHAR(20) NULL, PRIMARY KEY (`id`));

CREATE TABLE `writer` (`id` INT  NOT NULL AUTO_INCREMENT,`full_name`  VARCHAR(40) NULL,`random_num` VARCHAR(20) NULL, PRIMARY KEY (`id`));

INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('abc', 'def', '1');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('def', 'zhu', '2');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('dummy', 'name', '3');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('non', 'pay', '4');
INSERT INTO `reader` (`firstName`, `lastName`, `random_num`) VALUES ('spring', 'batch', '5');
```

### Spring Boot
> `src/main/java/com/barley/batch/Application.java`

```java
    @SpringBootApplication
    public class Application {
        public static void main(String[] args) throws Exception {
            SpringApplication.run(Application.class, args);
        }
    }
```

## Running the app
Done. Let us now run our app -

* Go to terminal and use our build tool to run it - run the cmd -

> `./gradlew build bootRun`.

* ALTERNATIVELY, You can build the jar for the app and then run it - run cmd -

>`./gradlew build bootRepackage`

and then run
>`java -jar build/libs/spring-batch-1.0.0.jar`.

Here is the expected output -

```
Job: [FlowJob: [name=importUserJob]] launched with the following parameters: [{run.id=1}]
Executing step: [step1]
RowMapper record : RecordSO{id=1, firstName='abc', lastName='def', randomNum='1'}
RowMapper record : RecordSO{id=2, firstName='def', lastName='zhu', randomNum='2'}
RowMapper record : RecordSO{id=3, firstName='dummy', lastName='name', randomNum='3'}
RowMapper record : RecordSO{id=4, firstName='non', lastName='pay', randomNum='4'}
RowMapper record : RecordSO{id=5, firstName='spring', lastName='batch', randomNum='5'}

Processing Record: RecordSO{id=1, firstName='abc', lastName='def', randomNum='1'}
Processed Writer: WriterSO{id=1, fullName='abc def', randomNum='69388'}
Processing Record: RecordSO{id=2, firstName='def', lastName='zhu', randomNum='2'}
Processed Writer: WriterSO{id=2, fullName='def zhu', randomNum='41825'}
Processing Record: RecordSO{id=3, firstName='dummy', lastName='name', randomNum='3'}
Processed Writer: WriterSO{id=3, fullName='dummy name', randomNum='23697'}
Processing Record: RecordSO{id=4, firstName='non', lastName='pay', randomNum='4'}
Processed Writer: WriterSO{id=4, fullName='non pay', randomNum='89450'}
Processing Record: RecordSO{id=5, firstName='spring', lastName='batch', randomNum='5'}
Processed Writer: WriterSO{id=5, fullName='spring batch', randomNum='87917'}

!!! JOB FINISHED! Time to verify the results
Found <WriterSO{id=1, fullName='abc def', randomNum='69388'}> in the database.
Found <WriterSO{id=2, fullName='def zhu', randomNum='41825'}> in the database.
Found <WriterSO{id=3, fullName='dummy name', randomNum='23697'}> in the database.
Found <WriterSO{id=4, fullName='non pay', randomNum='89450'}> in the database.
Found <WriterSO{id=5, fullName='spring batch', randomNum='87917'}> in the database.

Job: [FlowJob: [name=importUserJob]] completed with the following parameters: [{run.id=1}] and the following status: [COMPLETED]
```

> Thank you.
