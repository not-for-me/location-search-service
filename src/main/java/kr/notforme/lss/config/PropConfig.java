package kr.notforme.lss.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import kr.notforme.lss.config.props.ExternalServiceProp;

@Configuration
@EnableConfigurationProperties({
        ExternalServiceProp.class
})
public class PropConfig {
}
