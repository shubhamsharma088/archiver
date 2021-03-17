package org.shubham.archiver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.shubham.archiver.endpoint.FileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

  @Autowired
  private FileController fileController;

  @Test
  void contextLoads() {
    assertThat(fileController).isNotNull();
  }

}
