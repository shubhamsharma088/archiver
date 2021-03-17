package org.shubham.archiver.util;

import static org.shubham.archiver.util.FileUtils.archiveName;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

@Slf4j
public class HeaderUtils {

  /**
   * Create new headers with unique filenames to avoid name clash at client's machine.
   *
   * @return HttpHeaders as a MultiValueMap
   */
  public static MultiValueMap headers() {
    String fileName = archiveName();
    MultiValueMap<String, String> headers = new HttpHeaders();
    headers.add(CONTENT_TYPE, "application/zip");
    headers.add(CONTENT_DISPOSITION, "attachment;filename=" + fileName);

    log.info("Preparing to send archive {}...", fileName);
    return headers;
  }
}
