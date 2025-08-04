package com.team1.hrbank.domain.file.controller;

import com.team1.hrbank.domain.file.entity.FileMetadata;
import com.team1.hrbank.domain.file.service.FileMetadataService;
import com.team1.hrbank.global.api.FileApi;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileMetadataController implements FileApi {

  private final FileMetadataService fileMetadataService;

  @Override
  @GetMapping("/api/files/{id}/download")
  public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) {
    FileMetadata metadata = fileMetadataService.getFileMetadata(id);
    Resource resource = fileMetadataService.downloadFile(id);

    return ResponseEntity
        .status(HttpStatus.OK)
        .header("Content-Disposition", "attachment; filename=\"" + metadata.getSavedName() + "\"")
        .body(resource);
  }
}