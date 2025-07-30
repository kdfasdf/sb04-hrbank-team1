package com.team1.hrbank.domain.file.entity;

import com.team1.hrbank.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_metadatas")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class FileMetadata extends BaseEntity {

  @Column(name = "original_name", nullable = false, length = 255)
  private String originalName;

  @Column(name = "saved_name", nullable = false, length = 100)
  private String savedName;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, length = 10)
  private FileType fileType;

  @Enumerated(EnumType.STRING)
  @Column(name = "usage_type", nullable = false, length = 10)
  private FileUsageType fileUsageType;

  @Column(name = "size", nullable = false)
  private Long fileSize;

  @Column(name = "path", nullable = false, length = 500)
  private String filePath;
}
