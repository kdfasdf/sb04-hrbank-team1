package com.team1.hrbank.domain.file.controller;

import com.team1.hrbank.domain.file.service.BasicFileMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileMetadataController {

  private final BasicFileMetadataService basicFileMetadataService;

}
