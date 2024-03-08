package com.jobs.im.core.utils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: im
 * @ClassName: PropertiesUtil
 * @description:
 * @author: Author
 * @create: 2024-02-22 15:27
 * @Version 1.0
 **/
@Component
public class PropertyUtil {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        try (InputStreamReader baseReader = new InputStreamReader(
            this.getClass().getClassLoader().getResourceAsStream("application-code-base.properties"),
            StandardCharsets.UTF_8)) {
            props.load(baseReader);
        } catch (Exception e) {
            log.error("parse properties error.", e);
        }
        try (InputStreamReader codeReader =
            new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("application-code.properties"),
                StandardCharsets.UTF_8)) {
            props.load(codeReader);
        } catch (Exception e) {
            log.error("parse properties error.", e);
        }
        props.keySet().forEach(key -> {
            if (props.get(key) != null && StringUtils.isNotBlank(props.get(key).toString())) {
                PropertiesUtil.put(key.toString().trim(), props.get(key).toString().trim());
            }
        });
    }
}
