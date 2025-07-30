package com.team1.hrbank.domain.file.service;

import com.team1.hrbank.domain.file.dto.FileMetadataDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileMetadataService {

  FileMetadataDto uploadProfile(MultipartFile file);
}
