-- -- departments
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (101, '인사팀', '인사 및 채용 담당', '2010-03-01', '2025-07-06 12:00:00', '2025-07-06 12:00:00');
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (102, '재무팀', '회계 및 자금 관리', '2011-07-15', '2025-07-06 12:00:00', '2025-07-06 12:00:00');
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (103, '개발팀', '소프트웨어 개발 및 유지보수', '2015-01-10', '2025-07-06 12:00:00', '2025-07-06 12:00:00');
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (104, '마케팅팀', '시장 조사 및 광고 전략', '2013-05-22', '2025-07-06 12:00:00', '2025-07-06 12:00:00');
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (105, '운영팀', '일반 운영 및 관리', '2012-09-30', '2025-07-06 12:00:00', '2025-07-06 12:00:00');

-- -- employees (11명)
INSERT INTO employees (id, employee_number, name, email, position, hire_date, status, department_id,
                       created_at, updated_at)
VALUES (101, 'EMP001', '김철수', 'chulsoo.kim@example.com', 'Manager', '2022-01-15', 'ACTIVE', 101, NOW(),
        NOW()),
       (102, 'EMP002', '이영희', 'younghee.lee@example.com', 'Staff', '2021-12-10', 'ON_LEAVE', 102, NOW(),
        NOW()),
       (103, 'EMP003', '박민수', 'minsoo.park@example.com', 'Developer', '2023-03-08', 'RESIGNED', 103,
        NOW(), NOW()),
       (104, 'EMP004', '최지훈', 'jihoon.choi@example.com', 'Manager', '2020-08-25', 'ACTIVE', 104, NOW(),
        NOW()),
       (105, 'EMP005', '정다은', 'daeun.jung@example.com', 'Staff', '2023-01-01', 'ON_LEAVE', 105, NOW(),
        NOW()),
       (106, 'EMP006', '한지민', 'jimin.han@example.com', 'Developer', '2022-04-30', 'ACTIVE', 101, NOW(),
        NOW()),
       (107, 'EMP007', '오세훈', 'sehoon.oh@example.com', 'Manager', '2019-09-14', 'RESIGNED', 102, NOW(),
        NOW()),
       (108, 'EMP008', '장서연', 'seoyeon.jang@example.com', 'Staff', '2021-10-05', 'ACTIVE', 103, NOW(),
        NOW()),
       (109, 'EMP009', '배지훈', 'jihoon.bae@example.com', 'Developer', '2020-11-20', 'ON_LEAVE', 104,
        NOW(), NOW()),
       (110, 'EMP010', '송지수', 'jisu.song@example.com', 'Staff', '2023-06-01', 'ACTIVE', 105, NOW(),
        NOW()),
       (111, 'EMP050', '유하늘', 'haneul.yoo@example.com', 'Manager', '2024-05-03', 'RESIGNED', 103,
        NOW(), NOW());


-- -- file_metadatas
INSERT INTO file_metadatas (id, original_name, saved_name, extension, type, size, path, created_at,
                            updated_at)
VALUES (101, 'file1.csv', 'file1_20250805.csv', 'csv', 'BACKUP', 1024, '/files/file1_20250805.csv',
        NOW(), NOW()),
       (102, 'file2.csv', 'file2_20250805.csv', 'csv', 'BACKUP', 2048, '/files/file2_20250805.csv',
        NOW(), NOW()),
       (103, 'file3.csv', 'file3_20250805.csv', 'csv', 'BACKUP', 4096, '/files/file3_20250805.csv',
        NOW(), NOW()),
       (104, 'file2.log', 'file4_20250805.log', 'log', 'ERROR_LOG', 2048, '/files/file4_20250805.log',
        NOW(), NOW()),
       (105, 'file5.csv', 'file5_20250805.csv', 'csv', 'BACKUP', 4096, '/files/file5_20250805.csv',
        NOW(), NOW()),
       (106, 'file1.csv', 'file1_20250805.csv', 'csv', 'BACKUP', 1024, '/files/file6_20250805.csv',
        NOW(), NOW()),
       (107, 'file2.csv', 'file2_20250805.csv', 'csv', 'BACKUP', 2048, '/files/file7_20250805.csv',
        NOW(), NOW()),
       (108, 'file3.csv', 'file3_20250805.csv', 'csv', 'BACKUP', 4096, '/files/file8_20250805.csv',
        NOW(), NOW()),
       (109, 'file2.log', 'file4_20250805.log', 'log', 'ERROR_LOG', 2048, '/files/file9_20250805.log',
        NOW(), NOW()),
       (110, 'file5.csv', 'file5_20250805.csv', 'csv', 'BACKUP', 4096, '/files/file10_20250805.csv',
        NOW(), NOW()),
       (111, 'file2.csv', 'file2_20250805.csv', 'csv', 'BACKUP', 2048, '/files/file11_20250805.csv',
        NOW(), NOW()),
       (112, 'file5.csv', 'file5_20250805.csv', 'csv', 'BACKUP', 4096, '/files/file12_20250805.csv',
        NOW(), NOW()),
       (113, 'file3.csv', 'file3_20250805.csv', 'csv', 'BACKUP', 4096, '/files/file13_20250805.csv',
        NOW(), NOW()),
       (114, 'file2.log', 'file4_20250805.log', 'log', 'ERROR_LOG', 2048, '/files/file14_20250805.log',
        NOW(), NOW()),
       (115, 'file5.csv', 'file5_20250805.csv', 'csv', 'BACKUP', 4096, '/files/file15_20250805.csv',
        NOW(), NOW()),
       (116, 'file1.csv', 'file1_20250805.csv', 'csv', 'BACKUP', 1024, '/files/file16_20250805.csv',
        NOW(), NOW()),
       (117, 'file2.csv', 'file2_20250805.csv', 'csv', 'BACKUP', 2048, '/files/file17_20250805.csv',
        NOW(), NOW()),
       (118, 'file3.csv', 'file3_20250805.csv', 'csv', 'BACKUP', 4096, '/files/file18_20250805.csv',
        NOW(), NOW()),
       (119, 'file2.log', 'file4_20250805.log', 'log', 'ERROR_LOG', 2048, '/files/file19_20250805.log',
        NOW(), NOW()),
       (120, 'file20.csv', 'file20_20250805.csv', 'csv', 'BACKUP', 5120,
        '/files/file20_20250805.csv', NOW(), NOW());
-- -- change_logs (10개)
INSERT INTO change_logs (id, employee_number, type, memo, ip_address, created_at, updated_at)
VALUES (101, 'EMP001', 'CREATED', '신규 입사 등록', '192.168.0.11', NOW(), NOW()),
       (102, 'EMP002', 'UPDATED', '이메일 주소 수정', '192.168.0.12', NOW(), NOW()),
       (103, 'EMP003', 'UPDATED', '부서 이동 처리', '10.0.0.1', NOW(), NOW()),
       (104, 'EMP004', 'DELETED', '퇴사 처리', '172.16.1.1', NOW(), NOW()),
       (105, 'EMP005', 'CREATED', '직책 신규 등록', '192.168.1.20', NOW(), NOW()),
       (106, 'EMP006', 'UPDATED', '프로필 이미지 변경', '10.10.10.10', NOW(), NOW()),
       (107, 'EMP007', 'UPDATED', '메모 업데이트', '192.168.100.100', NOW(), NOW()),
       (108, 'EMP008', 'DELETED', '계정 비활성화', '203.0.113.5', NOW(), NOW()),
       (109, 'EMP010', 'CREATED', '이력 정보 등록', '198.51.100.22', NOW(), NOW()),
       (110, 'EMP010', 'UPDATED', '직책 수정', '172.31.255.254', NOW(), NOW());

-- -- change_log_diffs
INSERT INTO change_log_diffs (id, change_log_id, property_name, before_value, after_value,
                              created_at, updated_at)
VALUES (101, 101, 'CREATED', NULL, '신규 입사 등록', NOW(), NOW()),
       (102, 102, 'UPDATED', '이메일 주소 이전 값', '이메일 주소 수정 후 값', NOW(), NOW()),
       (103, 103, 'UPDATED', '이전 부서', '이동된 부서', NOW(), NOW()),
       (104, 104, 'DELETED', '재직 중', '퇴사 처리됨', NOW(), NOW()),
       (105, 105, 'CREATED', NULL, '직책 신규 등록', NOW(), NOW()),
       (106, 106, 'UPDATED', '기존 프로필 이미지', '새 프로필 이미지', NOW(), NOW()),
       (107, 107, 'UPDATED', '이전 메모', '업데이트된 메모', NOW(), NOW()),
       (108, 108, 'DELETED', '계정 활성 상태', '계정 비활성화 처리', NOW(), NOW()),
       (109, 109, 'CREATED', NULL, '이력 정보 신규 등록', NOW(), NOW()),
       (110, 110, 'UPDATED', '이전 직책', '수정된 직책', NOW(), NOW());

-- backups
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (101, '2025-07-18T00:00:00', '2025-07-18T00:03:00', 102, 'IN_PROGRESS', '192.168.0.2', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (103, '2025-07-19T00:00:00', '2025-07-19T00:04:00', 103, 'IN_PROGRESS', '192.168.0.3', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (104, '2025-07-20T00:00:00', '2025-07-20T00:02:00', 104, 'SKIPPED', '192.168.0.4', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (105, '2025-07-21T00:00:00', '2025-07-21T00:05:00', 105, 'FAIL', '192.168.0.5', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (106, '2025-07-22T00:00:00', '2025-07-22T00:02:00', 106, 'IN_PROGRESS', '192.168.0.6', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (107, '2025-07-23T00:00:00', '2025-07-23T00:05:00', 107, 'COMPLETED', '192.168.0.7', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (108, '2025-07-24T00:00:00', '2025-07-24T00:03:00', 108, 'COMPLETED', '192.168.0.8', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (109, '2025-07-25T00:00:00', '2025-07-25T00:05:00', 109, 'IN_PROGRESS', '192.168.0.9', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (110, '2025-07-26T00:00:00', '2025-07-26T00:05:00', 110, 'FAIL', '192.168.0.10', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (111, '2025-07-27T00:00:00', '2025-07-27T00:02:00', 111, 'FAIL', '192.168.0.11', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (112, '2025-07-28T00:00:00', '2025-07-28T00:02:00', 112, 'COMPLETED', '192.168.0.12', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (113, '2025-07-29T00:00:00', '2025-07-29T00:05:00', 113, 'SKIPPED', '192.168.0.13', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (114, '2025-07-30T00:00:00', '2025-07-30T00:05:00', 114, 'FAIL', '192.168.0.14', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (115, '2025-07-31T00:00:00', '2025-07-31T00:02:00', 115, 'COMPLETED', '192.168.0.15', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (116, '2025-08-01T00:00:00', '2025-08-01T00:03:00', 116, 'SKIPPED', '192.168.0.16', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (117, '2025-08-02T00:00:00', '2025-08-02T00:04:00', 117, 'SKIPPED', '192.168.0.17', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (118, '2025-08-03T00:00:00', '2025-08-03T00:05:00', 118, 'IN_PROGRESS', '192.168.0.18', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (119, '2025-08-04T00:00:00', '2025-08-04T00:05:00', 119, 'FAIL', '192.168.0.19', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (120, '2025-08-05T00:00:00', '2025-08-05T00:02:00', 120, 'SKIPPED', '192.168.0.20', NOW());