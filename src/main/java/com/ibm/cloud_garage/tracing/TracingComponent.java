package com.ibm.cloud_garage.tracing;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TracingComponent {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${opentracing.jaeger.udp-sender.host}")
    private String agentHost;
    @Value("${opentracing.jaeger.udp-sender.port}")
    private Integer agentPort;

    @Bean
    public JaegerTracer getTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.SenderConfiguration senderConfiguration = Configuration.SenderConfiguration.fromEnv().withAgentHost(agentHost).withAgentPort(agentPort);
        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true).withSender(senderConfiguration);
        Configuration config = new Configuration(appName).withSampler(samplerConfig).withReporter(reporterConfig);
        return config.getTracer();
    }
}
