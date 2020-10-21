package config;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.EndpointsSupplier;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.ControllerEndpointHandlerMapping;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
// TODO select Import actuator 
@Import({EndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.amqp.RabbitHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.audit.AuditAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.audit.AuditEventsEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.availability.AvailabilityHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.availability.AvailabilityProbesAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.beans.BeansEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.cache.CachesEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.cassandra.CassandraHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.cassandra.CassandraReactiveHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.cloudfoundry.servlet.CloudFoundryActuatorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.cloudfoundry.reactive.ReactiveCloudFoundryActuatorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.condition.ConditionsReportEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.context.properties.ConfigurationPropertiesReportEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.context.ShutdownEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.couchbase.CouchbaseHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.couchbase.CouchbaseReactiveHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.elasticsearch.ElasticSearchReactiveHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.elasticsearch.ElasticSearchRestHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.endpoint.jmx.JmxEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.env.EnvironmentEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.flyway.FlywayEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.hazelcast.HazelcastHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.influx.InfluxDbHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.info.InfoContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.info.InfoEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.integration.IntegrationGraphEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.jms.JmsHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.jolokia.JolokiaEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ldap.LdapHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.liquibase.LiquibaseEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.logging.LogFileWebEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.logging.LoggersEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.mail.MailHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.management.HeapDumpWebEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.management.ThreadDumpEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.JvmMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.KafkaMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.Log4J2MetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.LogbackMetricsAutoConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.MetricsEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.amqp.RabbitMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.cache.CacheMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.appoptics.AppOpticsMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.atlas.AtlasMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.datadog.DatadogMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace.DynatraceMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.elastic.ElasticMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.ganglia.GangliaMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.graphite.GraphiteMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.humio.HumioMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.influx.InfluxMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.jmx.JmxMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.kairos.KairosMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic.NewRelicMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.signalfx.SignalFxMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.stackdriver.StackdriverMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.statsd.StatsdMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.export.wavefront.WavefrontMetricsExportAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.jdbc.DataSourcePoolMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.jersey.JerseyServerMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.orm.jpa.HibernateMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.r2dbc.ConnectionPoolMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.web.client.HttpClientMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.web.jetty.JettyMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.web.reactive.WebFluxMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.web.servlet.WebMvcMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.metrics.web.tomcat.TomcatMetricsAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.mongo.MongoHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.mongo.MongoReactiveHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.neo4j.Neo4jHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.r2dbc.ConnectionFactoryHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.redis.RedisHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.redis.RedisReactiveHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.scheduling.ScheduledTasksEndpointAutoConfiguration.class,
		// exclude security configuration. 
//		org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.session.SessionsEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.solr.SolrHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.system.DiskSpaceHealthContributorAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.web.mappings.MappingsEndpointAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.web.reactive.ReactiveManagementContextAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.web.servlet.ServletManagementContextAutoConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.endpoint.web.ServletEndpointManagementContextConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.endpoint.web.reactive.WebFluxEndpointManagementContextConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.endpoint.web.servlet.WebMvcEndpointManagementContextConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.endpoint.web.jersey.JerseyWebEndpointManagementContextConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.SecurityRequestMatchersManagementContextConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.web.jersey.JerseySameManagementContextConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.web.jersey.JerseyChildManagementContextConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.web.reactive.ReactiveManagementChildContextConfiguration.class
//		org.springframework.boot.actuate.autoconfigure.web.servlet.ServletManagementChildContextConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.web.servlet.WebMvcEndpointChildContextConfiguration.class
})
@PropertySource("classpath:application.properties")
public class ActuatorConfiguration {

	/*
	 * autoconfiguratrion
	 * 	https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-actuator-autoconfigure/src/main/resources/META-INF/spring.factories
	 * 
	 * spring boot actuator without spring boot
	 * 	https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html
	 * 	https://stackoverflow.com/questions/56336916/spring-boot-2-actuator-without-spring-boot-and-enableautoconfiguration
	 * 	https://stackoverflow.com/questions/29953157/spring-boot-actuator-without-spring-boot
	 * 	https://stackoverflow.com/questions/26913087/use-spring-boot-actuator-without-a-spring-boot-application
	 * 	https://www.javachinna.com/2020/02/12/add-spring-boot-actuator-2-x-support-to-spring-mvc-rest-application-without-using-spring-boot/
	 * 
	 * call
	 * 	http://localhost:8080/actuator.spring/manage
	 */
	
	@Bean
	public DispatcherServletPath dispatcherServletPath() {
		return new DispatcherServletPath() {
			@Override
			public String getPath() {
				return "/";
			}
			
		};
	}
	
//	/* WebMvcEndpointManagementContextConfiguration의 @conditionalXxx의 이유로 로드 되지 않음. 그래서 코드 가져옴.*/
	@Bean
	public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(List<EndpointsSupplier> endpointsSuppliers,
//			EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
			EndpointMediaTypes endpointMediaTypes, 
			WebEndpointProperties webEndpointProperties, Environment environment) {
		List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
		Collection<ExposableWebEndpoint> webEndpoints = new ArrayList<ExposableWebEndpoint>();
		for(EndpointsSupplier supplier:  endpointsSuppliers) {
			if(supplier instanceof WebEndpointsSupplier) {
				webEndpoints.addAll(supplier.getEndpoints());
			}
			allEndpoints.addAll(supplier.getEndpoints());
		}
		
		String basePath = webEndpointProperties.getBasePath();
		EndpointMapping endpointMapping = new EndpointMapping(basePath);
		boolean shouldRegisterLinksMapping = StringUtils.hasText(basePath)
				|| ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT);
		return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
//				corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
				null, new EndpointLinksResolver(allEndpoints, basePath),
				shouldRegisterLinksMapping);
	}
//	@Bean
//	public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
//			ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
////			EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
//			EndpointMediaTypes endpointMediaTypes, 
//			WebEndpointProperties webEndpointProperties, Environment environment) {
//		List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
//		Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//		allEndpoints.addAll(webEndpoints);
//		allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//		allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//		String basePath = webEndpointProperties.getBasePath();
//		EndpointMapping endpointMapping = new EndpointMapping(basePath);
//		boolean shouldRegisterLinksMapping = StringUtils.hasText(basePath)
//				|| ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT);
//		return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
////				corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
//				null, new EndpointLinksResolver(allEndpoints, basePath),
//				shouldRegisterLinksMapping);
//	}

	@Bean
	public ControllerEndpointHandlerMapping controllerEndpointHandlerMapping(
//			ControllerEndpointsSupplier controllerEndpointsSupplier, CorsEndpointProperties corsProperties,
			ControllerEndpointsSupplier controllerEndpointsSupplier, 
			WebEndpointProperties webEndpointProperties) {
		EndpointMapping endpointMapping = new EndpointMapping(webEndpointProperties.getBasePath());
		return new ControllerEndpointHandlerMapping(endpointMapping, controllerEndpointsSupplier.getEndpoints(),
//				corsProperties.toCorsConfiguration());
				null);
	}
	/* WebMvcEndpointManagementContextConfiguration의 @conditionalXxx의 이유로 로드 되지 않음. 그래서 코드 가져옴.*/

}
