package com.example.stock_api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public final class TestUtil {
  private static final ObjectMapper OBJECT_MAPPER =
      new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  public static <T> T toObject(final Resource resource, final Class<T> clazz) {
    try (InputStream in = resource.getInputStream()) {
      return OBJECT_MAPPER.readValue(in, clazz);
    } catch (IOException ex) {
      log.warn("Converting file to Object failed", ex);
      return null;
    }
  }
}
