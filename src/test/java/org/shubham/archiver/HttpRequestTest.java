package org.shubham.archiver;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  static MockMultipartFile[] files = new MockMultipartFile[3];

  @BeforeAll
  static void beforeAll() {
    for (int i = 0; i < files.length; i++) {
      files[i] = new MockMultipartFile("files", "hello-" + i + ".txt",
          TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    }
  }

  @Test
  public void shouldGetSuccessResponse() throws Exception {

    MockMvc mockMvc
        = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    mockMvc.perform(multipart("/zip")
        .file(files[0])
        .file(files[1])
        .file(files[2])
    ).andExpect(status().isOk());

  }

  @Test
  void shouldGetClientError() throws Exception {
    MockMvc mockMvc
        = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    MockMultipartFile file1 = new MockMultipartFile("files", null,
        TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    MockMultipartFile file2 = new MockMultipartFile("files", "file2.txt",
        TEXT_PLAIN_VALUE, new byte[0]);

    mockMvc.perform(multipart("/zip")
        .file(file1)).andExpect(status().is4xxClientError());

    mockMvc.perform(multipart("/zip")
        .file(file2)).andExpect(status().is4xxClientError());
  }
}
