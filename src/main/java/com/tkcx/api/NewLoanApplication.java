package com.tkcx.api;

import com.acct.job.config.XxlJobConfig;
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
@PropertySource({"classpath:bank.properties"})
@MapperScan("com.tkcx.api.business.*.dao")
@SpringBootApplication
public class NewLoanApplication {

	private final static Logger logger = LoggerFactory.getLogger(NewLoanApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NewLoanApplication.class, args);
		logger.info(NewLoanApplication.class.getSimpleName() + " is success!");
	}

}
