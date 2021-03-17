package org.shubham.archiver.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.shubham.archiver.util.FileUtils.hasName;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.shubham.archiver.exception.ArchiverClientException;
import org.springframework.web.multipart.MultipartFile;

class FileUtilsTest {

  private static MultipartFile file;

  @BeforeAll
  static void beforeAll() {
    file = mock(MultipartFile.class);

  }

  @ParameterizedTest(name = "{index} {0} as input fileName.")
  @NullAndEmptySource
  void hasName_invalidOriginalFileName(String input) {
    when(file.getOriginalFilename()).thenReturn(input);
    assertThat(hasName(file)).isFalse();
  }

  @Test
  void hasName_validOriginalFileName() {
    when(file.getOriginalFilename()).thenReturn("valid_file.txt");
    assertThat(hasName(file)).isTrue();
  }

  @Test
  void archiveName_providesValidArchiveName() {
    assertThat(FileUtils.archiveName())
        .isNotEmpty()
        .isNotBlank()
        .startsWith("archive-")
        .endsWith(".zip");
  }

  @Test
  void validFiles_SingleElementArrayInput() {
    assertThatThrownBy(
        () -> FileUtils.validFiles(new MultipartFile[1]))
        .isInstanceOf(ArchiverClientException.class)
        .hasMessage("Received invalid file list. Terminating archival process");
  }

  @Test
  void validFiles_MissingOriginalFileNameForFirstElement() {
    MultipartFile[] multipartFiles = new MultipartFile[1];
    multipartFiles[0] = file;
    when(file.getOriginalFilename()).thenReturn(null);

    assertThatThrownBy(
        () -> FileUtils.validFiles(multipartFiles))
        .isInstanceOf(ArchiverClientException.class)
        .hasMessage("Received invalid file list. Terminating archival process");
  }
}