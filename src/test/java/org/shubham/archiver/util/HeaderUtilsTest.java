package org.shubham.archiver.util;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;

class HeaderUtilsTest {

  @Test
  void headers_validHeaders() {
    assertThat(HeaderUtils.headers())
        .contains(entry("Content-Type", asList("application/zip")))
        .containsKeys("Content-Disposition");

    assertThat(HeaderUtils.headers()
        .getFirst("Content-Disposition"))
        .asString().isNotBlank();

  }
}