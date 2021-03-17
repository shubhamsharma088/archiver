package org.shubham.archiver.service;

import static java.util.Arrays.stream;
import static org.shubham.archiver.util.FileUtils.validFiles;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.shubham.archiver.exception.ArchiverServerException;
import org.shubham.archiver.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FileService {

  /**
   * The method takes in {@link MultipartFile} array and validates if the files doesn't contain any
   * valid file. In case of failures it will fail-fast to avoid starting any costly IO streams. In
   * case of valid files, The method adds all the files to the response byte array as {@link
   * ZipOutputStream} without doing any costly write to the disk.
   * <p>
   * This serves 2 benefits first, there is no unnecessary file added on the server which allows
   * user to be confident about their data not be recorded. Secondly, from maintenance perspective,
   * no file rotation policy is required and a compute intensive server becomes most performant for
   * this use case.
   *
   * @param files - Array of files to be zipped
   * @return byte[] of {@link ZipOutputStream} containing all the files with valid file name.
   */
  public byte[] transformFilesToZipByteStream(MultipartFile[] files) {

    //fail fast in case of invalid input.
    validFiles(files);

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

      stream(files)
          .filter(FileUtils::hasName)
          .forEach(file -> addZipEntryToStream(zipOutputStream, file));

    } catch (IOException e) {
      log.error("Zip creation failed", e);
      throw new ArchiverServerException("Could not zip files.", e);
    }
    return byteArrayOutputStream.toByteArray();
  }

  /**
   * Adds file to existing {@link ZipOutputStream}
   *
   * @param zipOS is the {@link ZipOutputStream} to which indivual files need to be added for
   *              creating a zip.
   * @param file  the file to be added
   */
  private void addZipEntryToStream(ZipOutputStream zipOS, MultipartFile file) {
    String filename = file.getOriginalFilename();

    log.info("Adding {} to the zip file...", filename);

    try {
      zipOS.putNextEntry(new ZipEntry(filename));
      zipOS.write(file.getBytes());
      zipOS.closeEntry();
    } catch (IOException e) {
      log.error("Add file to zip stream failed", e);
      throw new ArchiverServerException("Failed to add files to zip.", e);
    }

    log.info("Finished adding {} to the zip file.", filename);
  }
}

