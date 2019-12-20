package org.freakz.luncher.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@ConfigurationProperties(prefix = "luncher")
@PropertySource("file:runtime.properties")
@Configuration
public class RuntimeConfig {

    @Value("${enabled:true}")
    private boolean publishToTeams;

    private String teamsWebHookUrl;

    @Value("${enabled:true}")
    private boolean useProxy;

    private String httpProxyHost;
    private String httpProxyPort;

    private String httpsProxyHost;
    private String httpsProxyPort;

    private String httpsProxyUser;
    private String httpsProxyPassword;

    public void setProxy() {
        if (useProxy) {
            System.setProperty("http.proxyHost", httpProxyHost);
            System.setProperty("http.proxyPort", httpProxyPort);

            System.setProperty("https.proxyHost", httpsProxyHost);
            System.setProperty("https.proxyPort", httpsProxyPort);
            System.setProperty("https.proxyUser", httpsProxyUser);
            System.setProperty("https.proxyPassword", httpsProxyPassword);
        }
    }
}
