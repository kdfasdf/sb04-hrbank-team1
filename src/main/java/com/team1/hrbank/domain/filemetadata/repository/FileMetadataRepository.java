package com.team1.hrbank.domain.filemetadata.repository;

import com.team1.hrbank.domain.filemetadata.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
}
