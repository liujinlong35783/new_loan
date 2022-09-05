package com.tkcx.api;

import com.acct.job.config.XxlJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@Import(XxlJobConfig.class)
@EnableAsync
@Slf4j
@PropertySource({"classpath:bank.properties"})
@MapperScan("com.tkcx.api.business.*.dao")
@SpringBootApplication
public class AcctPrintApplication {

//	private final static Logger logger = LoggerFactory.getLogger(AcctPrintApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AcctPrintApplication.class, args);
//		logger.info(AcctPrintApplication.class.getSimpleName() + " is success!");
		log.info(AcctPrintApplication.class.getSimpleName() + " is success!");
	}

}
