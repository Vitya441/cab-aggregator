package by.modsen.ridesservice.config;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class RideIntegrationConfig extends DatabaseContainerConfig {

    @RegisterExtension
    protected static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void wireMockProperties(DynamicPropertyRegistry registry) {
        registry.add("app.feign-clients.rating-service.url", wireMockServer::baseUrl);
        registry.add("app.feign-clients.payment-service.url", wireMockServer::baseUrl);
        registry.add("app.feign-clients.passenger-service.url", wireMockServer::baseUrl);
        registry.add("app.feign-clients.driver-service.url", wireMockServer::baseUrl);
        registry.add("app.feign-clients.price-service.url", wireMockServer::baseUrl);
    }
}