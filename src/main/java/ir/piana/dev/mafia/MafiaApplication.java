package ir.piana.dev.mafia;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.apache.commons.text.CaseUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {})
@EnableTransactionManagement
@EnableCaching
@EnableConfigurationProperties({})
public class MafiaApplication {

	@Bean("jdbcObjectMapper")
	public ObjectMapper getJdbcObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("LowerCaseKeyDeserializer",
				new Version(1,0,0,null));
		module.addKeyDeserializer(Object.class, new LowerCaseKeyDeserializer());
		module.addKeySerializer(Object.class, new LowerCaseKeySerializer());
		objectMapper.registerModule(module);
		return objectMapper;
	}

	@Bean("objectMapper")
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}

//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		return bCryptPasswordEncoder;
//	}

	@Bean
	public CacheManager cacheManager() {
		return new HazelcastCacheManager();
	}

	@Bean
	CommandLineRunner init() {
		return (args) -> System.out.println("Start...");
	}

	public static void main(String[] args) {
		SpringApplication.run(MafiaApplication.class, args);
	}


	public static class LowerCaseKeyDeserializer extends KeyDeserializer {
		@Override
		public Object deserializeKey(String key, DeserializationContext deserializationContext) throws IOException {
			return key.toLowerCase();
		}
	}

	public static class LowerCaseKeySerializer extends StdKeySerializers.StringKeySerializer {

		@Override
		public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
			jsonGenerator.writeFieldName(CaseUtils.toCamelCase(((String)o).toLowerCase(), false, new char[]{'_'}));
		}
	}
}
