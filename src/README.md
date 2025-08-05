
```
src
├─ main
│  ├─ java
│  │  └─ com
│  │     └─ team1
│  │        └─ hrbank
│  │           ├─ domain
│  │           │  ├─ backup
│  │           │  │  ├─ controller
│  │           │  │  │  └─ BackupController.java
│  │           │  │  ├─ dto
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ BackupDto.java
│  │           │  │  │     └─ CursorPageResponseBackupDto.java
│  │           │  │  ├─ entity
│  │           │  │  │  ├─ Backup.java
│  │           │  │  │  └─ BackupStatus.java
│  │           │  │  ├─ exception
│  │           │  │  │  └─ BackupException.java
│  │           │  │  ├─ mapper
│  │           │  │  │  └─ BackupMapper.java
│  │           │  │  ├─ repository
│  │           │  │  │  └─ BackupRepository.java
│  │           │  │  ├─ scheduler
│  │           │  │  │  └─ BackupScheduler.java
│  │           │  │  └─ service
│  │           │  │     └─ BackupService.java
│  │           │  ├─ base
│  │           │  │  └─ BaseEntity.java
│  │           │  ├─ changelog
│  │           │  │  ├─ controller
│  │           │  │  │  └─ ChangeLogController.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ data
│  │           │  │  │  │  └─ ChangeLogDto.java
│  │           │  │  │  ├─ request
│  │           │  │  │  │  └─ ChangeLogSearchRequest.java
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ ChangeLogDiffResponse.java
│  │           │  │  │     └─ ChangeLogSearchResponse.java
│  │           │  │  ├─ entity
│  │           │  │  │  ├─ ChangeLog.java
│  │           │  │  │  ├─ ChangeLogDiff.java
│  │           │  │  │  └─ ChangeLogType.java
│  │           │  │  ├─ exception
│  │           │  │  │  └─ ChangeLogException.java
│  │           │  │  ├─ mapper
│  │           │  │  │  ├─ ChangeLogDiffMapper.java
│  │           │  │  │  └─ ChangeLogMapper.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ ChangeLogDiffRepository.java
│  │           │  │  │  └─ ChangeLogRepository.java
│  │           │  │  └─ service
│  │           │  │     ├─ ChangeLogDiffService.java
│  │           │  │     └─ ChangeLogService.java
│  │           │  ├─ department
│  │           │  │  ├─ controller
│  │           │  │  │  └─ DepartmentController.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ request
│  │           │  │  │  │  ├─ DepartmentCreateRequestDto.java
│  │           │  │  │  │  ├─ DepartmentSearchRequestDto.java
│  │           │  │  │  │  └─ DepartmentUpdateRequestDto.java
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ DepartmentDto.java
│  │           │  │  │     └─ DepartmentPageResponseDto.java
│  │           │  │  ├─ entity
│  │           │  │  │  └─ Department.java
│  │           │  │  ├─ exception
│  │           │  │  │  └─ DepartmentException.java
│  │           │  │  ├─ mapper
│  │           │  │  │  └─ DepartmentMapper.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ DepartmentCustomRepository.java
│  │           │  │  │  ├─ DepartmentCustomRepositoryImpl.java
│  │           │  │  │  └─ DepartmentRepository.java
│  │           │  │  └─ service
│  │           │  │     └─ DepartmentService.java
│  │           │  ├─ employee
│  │           │  │  ├─ controller
│  │           │  │  │  └─ EmployeeController.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ EmployeeDto.java
│  │           │  │  │  ├─ request
│  │           │  │  │  │  ├─ CursorPageRequestDto.java
│  │           │  │  │  │  ├─ EmployeeCreateRequestDto.java
│  │           │  │  │  │  └─ EmployeeUpdateRequestDto.java
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ CursorPageResponseEmployeeDto.java
│  │           │  │  │     ├─ EmployeeDistributionDto.java
│  │           │  │  │     └─ EmployeeTrendDto.java
│  │           │  │  ├─ entity
│  │           │  │  │  ├─ Employee.java
│  │           │  │  │  ├─ EmployeeStatus.java
│  │           │  │  │  └─ TimeUnitType.java
│  │           │  │  ├─ exception
│  │           │  │  │  └─ EmployeeException.java
│  │           │  │  ├─ mapper
│  │           │  │  │  └─ EmployeeMapper.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ EmployeeRepository.java
│  │           │  │  │  ├─ EmployeeRepositoryCustom.java
│  │           │  │  │  └─ EmployeeRepositoryCustomImpl.java
│  │           │  │  └─ service
│  │           │  │     └─ EmployeeService.java
│  │           │  └─ file
│  │           │     ├─ controller
│  │           │     │  └─ FileMetadataController.java
│  │           │     ├─ dto
│  │           │     │  └─ StoredFileInfo.java
│  │           │     ├─ entity
│  │           │     │  ├─ FileMetadata.java
│  │           │     │  └─ FileType.java
│  │           │     ├─ exception
│  │           │     │  └─ FileException.java
│  │           │     ├─ repository
│  │           │     │  └─ FileMetadataRepository.java
│  │           │     └─ service
│  │           │        ├─ FileMetadataService.java
│  │           │        └─ FileStorageManager.java
│  │           ├─ global
│  │           │  ├─ api
│  │           │  │  ├─ BackupApi.java
│  │           │  │  ├─ ChangeLogApi.java
│  │           │  │  ├─ DepartmentApi.java
│  │           │  │  ├─ EmployeeApi.java
│  │           │  │  └─ FileApi.java
│  │           │  ├─ config
│  │           │  │  └─ JpaAuditingConfig.java
│  │           │  ├─ constant
│  │           │  │  ├─ BakcupErrorCode.java
│  │           │  │  ├─ ChangeLogErrorCode.java
│  │           │  │  ├─ DepartmentErrorCode.java
│  │           │  │  ├─ EmployeeErrorCode.java
│  │           │  │  ├─ ErrorCode.java
│  │           │  │  └─ FileErrorCode.java
│  │           │  └─ error
│  │           │     ├─ BusinessException.java
│  │           │     ├─ ErrorResponse.java
│  │           │     └─ GlobalExceptionHandler.java
│  │           └─ HrbankApplication.java
│  └─ resources
│     ├─ application-dev.yaml
│     ├─ application-prod.yaml
│     ├─ application.yaml
│     └─ static
│        ├─ assets
│        │  ├─ images
│        │  │  └─ default-profile.svg
│        │  └─ index-aNksrdbr.js
│        ├─ favicon.ico
│        └─ index.html
└─ test
   └─ java
      └─ com
         └─ team1
            └─ hrbank
               └─ HrbankApplicationTests.java

```
```
src
├─ main
│  ├─ java
│  │  └─ com
│  │     └─ team1
│  │        └─ hrbank
│  │           ├─ domain
│  │           │  ├─ backup
│  │           │  │  ├─ controller
│  │           │  │  │  └─ BackupController.java
│  │           │  │  ├─ dto
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ BackupDto.java
│  │           │  │  │     └─ CursorPageResponseBackupDto.java
│  │           │  │  ├─ entity
│  │           │  │  │  ├─ Backup.java
│  │           │  │  │  └─ BackupStatus.java
│  │           │  │  ├─ exception
│  │           │  │  │  └─ BackupException.java
│  │           │  │  ├─ mapper
│  │           │  │  │  └─ BackupMapper.java
│  │           │  │  ├─ repository
│  │           │  │  │  └─ BackupRepository.java
│  │           │  │  ├─ scheduler
│  │           │  │  │  └─ BackupScheduler.java
│  │           │  │  └─ service
│  │           │  │     └─ BackupService.java
│  │           │  ├─ base
│  │           │  │  └─ BaseEntity.java
│  │           │  ├─ changelog
│  │           │  │  ├─ controller
│  │           │  │  │  └─ ChangeLogController.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ data
│  │           │  │  │  │  └─ ChangeLogDto.java
│  │           │  │  │  ├─ request
│  │           │  │  │  │  └─ ChangeLogSearchRequest.java
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ ChangeLogDiffResponse.java
│  │           │  │  │     └─ ChangeLogSearchResponse.java
│  │           │  │  ├─ entity
│  │           │  │  │  ├─ ChangeLog.java
│  │           │  │  │  ├─ ChangeLogDiff.java
│  │           │  │  │  └─ ChangeLogType.java
│  │           │  │  ├─ exception
│  │           │  │  │  └─ ChangeLogException.java
│  │           │  │  ├─ mapper
│  │           │  │  │  ├─ ChangeLogDiffMapper.java
│  │           │  │  │  └─ ChangeLogMapper.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ ChangeLogDiffRepository.java
│  │           │  │  │  └─ ChangeLogRepository.java
│  │           │  │  └─ service
│  │           │  │     ├─ ChangeLogDiffService.java
│  │           │  │     └─ ChangeLogService.java
│  │           │  ├─ department
│  │           │  │  ├─ controller
│  │           │  │  │  └─ DepartmentController.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ request
│  │           │  │  │  │  ├─ DepartmentCreateRequestDto.java
│  │           │  │  │  │  ├─ DepartmentSearchRequestDto.java
│  │           │  │  │  │  └─ DepartmentUpdateRequestDto.java
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ DepartmentDto.java
│  │           │  │  │     └─ DepartmentPageResponseDto.java
│  │           │  │  ├─ entity
│  │           │  │  │  └─ Department.java
│  │           │  │  ├─ exception
│  │           │  │  │  └─ DepartmentException.java
│  │           │  │  ├─ mapper
│  │           │  │  │  └─ DepartmentMapper.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ DepartmentCustomRepository.java
│  │           │  │  │  ├─ DepartmentCustomRepositoryImpl.java
│  │           │  │  │  └─ DepartmentRepository.java
│  │           │  │  └─ service
│  │           │  │     └─ DepartmentService.java
│  │           │  ├─ employee
│  │           │  │  ├─ controller
│  │           │  │  │  └─ EmployeeController.java
│  │           │  │  ├─ dto
│  │           │  │  │  ├─ EmployeeDto.java
│  │           │  │  │  ├─ request
│  │           │  │  │  │  ├─ CursorPageRequestDto.java
│  │           │  │  │  │  ├─ EmployeeCreateRequestDto.java
│  │           │  │  │  │  └─ EmployeeUpdateRequestDto.java
│  │           │  │  │  └─ response
│  │           │  │  │     ├─ CursorPageResponseEmployeeDto.java
│  │           │  │  │     ├─ EmployeeDistributionDto.java
│  │           │  │  │     └─ EmployeeTrendDto.java
│  │           │  │  ├─ entity
│  │           │  │  │  ├─ Employee.java
│  │           │  │  │  ├─ EmployeeStatus.java
│  │           │  │  │  └─ TimeUnitType.java
│  │           │  │  ├─ exception
│  │           │  │  │  └─ EmployeeException.java
│  │           │  │  ├─ mapper
│  │           │  │  │  └─ EmployeeMapper.java
│  │           │  │  ├─ repository
│  │           │  │  │  ├─ EmployeeRepository.java
│  │           │  │  │  ├─ EmployeeRepositoryCustom.java
│  │           │  │  │  └─ EmployeeRepositoryCustomImpl.java
│  │           │  │  └─ service
│  │           │  │     └─ EmployeeService.java
│  │           │  └─ file
│  │           │     ├─ controller
│  │           │     │  └─ FileMetadataController.java
│  │           │     ├─ dto
│  │           │     │  └─ StoredFileInfo.java
│  │           │     ├─ entity
│  │           │     │  ├─ FileMetadata.java
│  │           │     │  └─ FileType.java
│  │           │     ├─ exception
│  │           │     │  └─ FileException.java
│  │           │     ├─ repository
│  │           │     │  └─ FileMetadataRepository.java
│  │           │     └─ service
│  │           │        ├─ FileMetadataService.java
│  │           │        └─ FileStorageManager.java
│  │           ├─ global
│  │           │  ├─ api
│  │           │  │  ├─ BackupApi.java
│  │           │  │  ├─ ChangeLogApi.java
│  │           │  │  ├─ DepartmentApi.java
│  │           │  │  ├─ EmployeeApi.java
│  │           │  │  └─ FileApi.java
│  │           │  ├─ config
│  │           │  │  └─ JpaAuditingConfig.java
│  │           │  ├─ constant
│  │           │  │  ├─ BakcupErrorCode.java
│  │           │  │  ├─ ChangeLogErrorCode.java
│  │           │  │  ├─ DepartmentErrorCode.java
│  │           │  │  ├─ EmployeeErrorCode.java
│  │           │  │  ├─ ErrorCode.java
│  │           │  │  └─ FileErrorCode.java
│  │           │  └─ error
│  │           │     ├─ BusinessException.java
│  │           │     ├─ ErrorResponse.java
│  │           │     └─ GlobalExceptionHandler.java
│  │           └─ HrbankApplication.java
│  └─ resources
│     ├─ application-dev.yaml
│     ├─ application-prod.yaml
│     ├─ application.yaml
│     └─ static
│        ├─ assets
│        │  ├─ images
│        │  │  └─ default-profile.svg
│        │  └─ index-aNksrdbr.js
│        ├─ favicon.ico
│        └─ index.html
├─ README.md
└─ test
   └─ java
      └─ com
         └─ team1
            └─ hrbank
               └─ HrbankApplicationTests.java

```