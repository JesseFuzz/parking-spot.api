package configs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class DateConfig { //fiz essa classe para settar minhas datas e horas para UTC em nível global, poderia ter sido a nível de classe ou de atributo também. 
	
	public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:SS'Z'";
	public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT))
			
	@Bean
	@Primary
	public ObjectMapper objectmapper() {
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LOCAL_DATETIME_SERIALZER);
		return new ObjectMapper().registerModule(module);
	}

}
