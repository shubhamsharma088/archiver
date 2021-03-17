package org.shubham.archiver.endpoint;


import static org.shubham.archiver.util.HeaderUtils.headers;
import static org.springframework.http.HttpStatus.OK;

import lombok.extern.slf4j.Slf4j;
import org.shubham.archiver.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class FileController {

  private FileService fileService;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping(value = "/zip")
  public ResponseEntity<byte[]> zipFile(@RequestParam("files") MultipartFile[] files) {

    return new ResponseEntity<>(fileService.transformFilesToZipByteStream(files), headers(), OK);

  }


}
