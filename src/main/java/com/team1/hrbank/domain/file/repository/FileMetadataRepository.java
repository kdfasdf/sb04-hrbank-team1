package com.team1.hrbank.domain.file.repository;

import com.team1.hrbank.domain.file.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

}
