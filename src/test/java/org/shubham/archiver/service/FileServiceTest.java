package org.shubham.archiver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.shubham.archiver.exception.ArchiverServerException;
import org.springframework.web.multipart.MultipartFile;

class FileServiceTest {

  FileService fileService = new FileService();
  static MultipartFile[] files;

  @BeforeAll
  static void beforeAll() throws IOException {
    files = createMultiPartFilesArray();
  }


  @Test
  void transformFilesToZipByteStream_validCase() {
    byte[] bytes = fileService.transformFilesToZipByteStream(files);
    assertThat(bytes)
        .isNotEmpty();
  }

  @Test
  void transformFileToZipByteStream_ArchiverServerException_WhenEncountersAnyException()
      throws IOException {
    when(files[0].getBytes()).thenThrow(new IOException());

    assertThatThrownBy(() -> fileService.transformFilesToZipByteStream(files))
        .isInstanceOf(ArchiverServerException.class)
        .hasMessage("Could not zip files.");
  }

  private static MultipartFile[] createMultiPartFilesArray() throws IOException {
    MultipartFile[] files = new MultipartFile[10];
    for (int i = 0; i < 5; i++) {
      files[i] = mock(MultipartFile.class);
      String filename = "mpFile-" + i + ".txt";
      when(files[i].getOriginalFilename()).thenReturn(filename);
      when(files[i].getBytes()).thenReturn(new byte[10]);
      System.out.println("files = " + files[i].getOriginalFilename());
    }
    return files;
  }

}