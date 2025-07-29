package ui.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:test.properties")
public interface ConfigApp extends Config {

    @Key("browser")
    String browser();

    @Key("browserSize")
    String browserSize();

    @Key("timeout")
    long timeout();

    @Key("base.url")
    String baseUrl();
}
