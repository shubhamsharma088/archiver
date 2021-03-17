package org.shubham.archiver.util;

import static java.time.Instant.now;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

import lombok.extern.slf4j.Slf4j;
import org.shubham.archiver.exception.ArchiverClientException;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileUtils {

  public static boolean hasName(MultipartFile file) {
    return nonNull(file) && hasText(file.getOriginalFilename());
  }

  /**
   * @return a unique archive name to avoid file name clashes during load tests
   */
  public static String archiveName() {
    return new StringBuffer("archive-")
        .append(now().toEpochMilli())
        .append(".zip")
        .toString();
  }

  /**
   * This method checks for the input file[] validity. Note in case no files are provided by client,
   * array still has length=1
   *
   * @param files provided by the client for archival
   */
  public static void validFiles(MultipartFile[] files) {
    if (files.length < 2 || !hasText(files[0].getOriginalFilename())) {
      throw new ArchiverClientException(
          "Received invalid file list. Terminating archival process");
    }
  }
}
