-- departments
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (1, '인사팀', '인사 및 채용 담당', '2010-03-01', '2025-07-06 12:00:00', '2025-07-06 12:00:00');
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (2, '재무팀', '회계 및 자금 관리', '2011-07-15', '2025-07-06 12:00:00', '2025-07-06 12:00:00');
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (3, '개발팀', '소프트웨어 개발 및 유지보수', '2015-01-10', '2025-07-06 12:00:00', '2025-07-06 12:00:00');
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (4, '마케팅팀', '시장 조사 및 광고 전략', '2013-05-22', '2025-07-06 12:00:00', '2025-07-06 12:00:00');
INSERT INTO departments (id, name, description, established_date, created_at, updated_at)
VALUES (5, '운영팀', '일반 운영 및 관리', '2012-09-30', '2025-07-06 12:00:00', '2025-07-06 12:00:00');

-- employees (11명)
INSERT INTO employees (id, employee_number, name, email, position, hire_date, status, department_id,
                       created_at, updated_at)
VALUES (1, 'EMP001', '김철수', 'chulsoo.kim@example.com', 'Manager', '2022-01-15', 'ACTIVE', 1, NOW(),
        NOW()),
       (2, 'EMP002', '이영희', 'younghee.lee@example.com', 'Staff', '2021-12-10', 'ON_LEAVE', 2, NOW(),
        NOW()),
       (3, 'EMP003', '박민수', 'minsoo.park@example.com', 'Developer', '2023-03-08', 'RESIGNED', 3,
        NOW(), NOW()),
       (4, 'EMP004', '최지훈', 'jihoon.choi@example.com', 'Manager', '2020-08-25', 'ACTIVE', 4, NOW(),
        NOW()),
       (5, 'EMP005', '정다은', 'daeun.jung@example.com', 'Staff', '2023-01-01', 'ON_LEAVE', 5, NOW(),
        NOW()),
       (6, 'EMP006', '한지민', 'jimin.han@example.com', 'Developer', '2022-04-30', 'ACTIVE', 1, NOW(),
        NOW()),
       (7, 'EMP007', '오세훈', 'sehoon.oh@example.com', 'Manager', '2019-09-14', 'RESIGNED', 2, NOW(),
        NOW()),
       (8, 'EMP008', '장서연', 'seoyeon.jang@example.com', 'Staff', '2021-10-05', 'ACTIVE', 3, NOW(),
        NOW()),
       (9, 'EMP009', '배지훈', 'jihoon.bae@example.com', 'Developer', '2020-11-20', 'ON_LEAVE', 4,
        NOW(), NOW()),
       (10, 'EMP010', '송지수', 'jisu.song@example.com', 'Staff', '2023-06-01', 'ACTIVE', 5, NOW(),
        NOW()),
       (11, 'EMP050', '유하늘', 'haneul.yoo@example.com', 'Manager', '2024-05-03', 'RESIGNED', 3,
        NOW(), NOW());


-- file_metadatas (profile images)
INSERT INTO file_metadatas (id, original_name, saved_name, extension, type, size, path, created_at,
                            updated_at)
VALUES (1, 'chulsoo.png', 'profile_1_3a2f7c98-4b2a-4fdd-9b29-bcbd1234abcd.png', 'png', 'PROFILE',
        234512, 'uploads/profile/profile_1_3a2f7c98-4b2a-4fdd-9b29-bcbd1234abcd.png', NOW(), NOW()),
       (2, 'younghee.jpg', 'profile_2_7e15b7d2-9f44-4567-8264-f14d5678dcba.jpg', 'jpg', 'PROFILE',
        185233, 'uploads/profile/profile_2_7e15b7d2-9f44-4567-8264-f14d5678dcba.jpg', NOW(), NOW()),
       (3, 'minsoo.jpeg', 'profile_3_ba18f7a4-139b-40df-a5fa-abcde9876543.jpeg', 'jpeg', 'PROFILE',
        279844, 'uploads/profile/profile_3_ba18f7a4-139b-40df-a5fa-abcde9876543.jpeg', NOW(),
        NOW()),
       (4, 'jihoon.jpg', 'profile_4_9a4dfc91-32c3-4d4e-9f7c-c1c2344def12.jpg', 'jpg', 'PROFILE',
        198745, 'uploads/profile/profile_4_9a4dfc91-32c3-4d4e-9f7c-c1c2344def12.jpg', NOW(), NOW()),
       (5, 'daeun.png', 'profile_5_e3a47aa3-6ab1-44df-bb99-cc7766acb711.png', 'png', 'PROFILE',
        300152, 'uploads/profile/profile_5_e3a47aa3-6ab1-44df-bb99-cc7766acb711.png', NOW(), NOW()),
       (6, 'jimin.jpeg', 'profile_6_44b5f3d2-6f78-4e1e-98b0-aaa222be7788.jpeg', 'jpeg', 'PROFILE',
        412333, 'uploads/profile/profile_6_44b5f3d2-6f78-4e1e-98b0-aaa222be7788.jpeg', NOW(),
        NOW()),
       (7, 'sehoon.jpg', 'profile_7_8fcba27c-1111-4a9d-b220-556688abc123.jpg', 'jpg', 'PROFILE',
        255123, 'uploads/profile/profile_7_8fcba27c-1111-4a9d-b220-556688abc123.jpg', NOW(), NOW()),
       (8, 'seoyeon.png', 'profile_8_91b224ee-22e2-4dfd-82e2-fbd1119988de.png', 'png', 'PROFILE',
        199232, 'uploads/profile/profile_8_91b224ee-22e2-4dfd-82e2-fbd1119988de.png', NOW(), NOW()),
       (9, 'jihoon2.jpg', 'profile_9_3cffa011-a44a-4a1c-a12e-bddc2222be34.jpg', 'jpg', 'PROFILE',
        300324, 'uploads/profile/profile_9_3cffa011-a44a-4a1c-a12e-bddc2222be34.jpg', NOW(), NOW()),
       (10, 'jisu.jpeg', 'profile_10_5ddf3a89-5567-49b3-92dd-ccbb33ff4499.jpeg', 'jpeg', 'PROFILE',
        278234, 'uploads/profile/profile_10_5ddf3a89-5567-49b3-92dd-ccbb33ff4499.jpeg', NOW(),
        NOW());

-- change_logs (10개)
INSERT INTO change_logs (id, employee_number, type, memo, ip_address, created_at, updated_at)
VALUES (1, 'EMP001', 'CREATED', '신규 입사 등록', '192.168.0.11', NOW(), NOW()),
       (2, 'EMP002', 'UPDATED', '이메일 주소 수정', '192.168.0.12', NOW(), NOW()),
       (3, 'EMP003', 'UPDATED', '부서 이동 처리', '10.0.0.1', NOW(), NOW()),
       (4, 'EMP004', 'DELETED', '퇴사 처리', '172.16.1.1', NOW(), NOW()),
       (5, 'EMP005', 'CREATED', '직책 신규 등록', '192.168.1.20', NOW(), NOW()),
       (6, 'EMP006', 'UPDATED', '프로필 이미지 변경', '10.10.10.10', NOW(), NOW()),
       (7, 'EMP007', 'UPDATED', '메모 업데이트', '192.168.100.100', NOW(), NOW()),
       (8, 'EMP008', 'DELETED', '계정 비활성화', '203.0.113.5', NOW(), NOW()),
       (9, 'EMP010', 'CREATED', '이력 정보 등록', '198.51.100.22', NOW(), NOW()),
       (10, 'EMP010', 'UPDATED', '직책 수정', '172.31.255.254', NOW(), NOW());

-- change_log_diffs
INSERT INTO change_log_diffs (id, change_log_id, property_name, before_value, after_value)
VALUES (1, 1, 'position', '', 'Manager'),                                        -- CREATED
       (2, 2, 'email', 'younghee.lee@example.com', 'younghee_2024@example.com'), -- UPDATED
       (3, 2, 'status', 'ON_LEAVE', 'ACTIVE'),
       (4, 3, 'department_id', '3', '2'),                                        -- UPDATED
       (5, 4, 'status', 'ACTIVE', 'RESIGNED'),                                   -- DELETED
       (6, 5, 'position', '', 'Staff'),                                          -- CREATED
       (7, 6, 'position', 'Developer', 'Senior Developer'),                      -- UPDATED
       (8, 7, 'memo', '열심히 일함', '프로젝트 리더 승격'),                                   -- UPDATED
       (9, 8, 'status', 'ACTIVE', 'RESIGNED'),                                   -- DELETED
       (10, 9, 'hire_date', '', '2023-06-01'),                                   -- CREATED
       (11, 9, 'name', '', '송지수'),
       (12, 10, 'position', 'Staff', 'Team Lead'),                               -- UPDATED
       (13, 10, 'email', 'jisu.song@example.com', 'jisu.song@newdomain.com'),
       (14, 6, 'memo', '기존 메모 없음', '성실함 강조'),                                    -- UPDATED
       (15, 3, 'position', 'Developer', 'Manager');
-- UPDATED

-- backups
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (2, '2025-07-18T00:00:00', '2025-07-18T00:03:00', 2, 'IN_PROGRESS', '192.168.0.2', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (3, '2025-07-19T00:00:00', '2025-07-19T00:04:00', 3, 'IN_PROGRESS', '192.168.0.3', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (4, '2025-07-20T00:00:00', '2025-07-20T00:02:00', 4, 'SKIPPED', '192.168.0.4', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (5, '2025-07-21T00:00:00', '2025-07-21T00:05:00', 5, 'FAIL', '192.168.0.5', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (6, '2025-07-22T00:00:00', '2025-07-22T00:02:00', 6, 'IN_PROGRESS', '192.168.0.6', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (7, '2025-07-23T00:00:00', '2025-07-23T00:05:00', 7, 'COMPLETED', '192.168.0.7', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (8, '2025-07-24T00:00:00', '2025-07-24T00:03:00', 8, 'COMPLETED', '192.168.0.8', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (9, '2025-07-25T00:00:00', '2025-07-25T00:05:00', 9, 'IN_PROGRESS', '192.168.0.9', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (10, '2025-07-26T00:00:00', '2025-07-26T00:05:00', 10, 'FAIL', '192.168.0.10', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (11, '2025-07-27T00:00:00', '2025-07-27T00:02:00', 11, 'FAIL', '192.168.0.11', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (12, '2025-07-28T00:00:00', '2025-07-28T00:02:00', 12, 'COMPLETED', '192.168.0.12', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (13, '2025-07-29T00:00:00', '2025-07-29T00:05:00', 13, 'SKIPPED', '192.168.0.13', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (14, '2025-07-30T00:00:00', '2025-07-30T00:05:00', 14, 'FAIL', '192.168.0.14', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (15, '2025-07-31T00:00:00', '2025-07-31T00:02:00', 15, 'COMPLETED', '192.168.0.15', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (16, '2025-08-01T00:00:00', '2025-08-01T00:03:00', 16, 'SKIPPED', '192.168.0.16', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (17, '2025-08-02T00:00:00', '2025-08-02T00:04:00', 17, 'SKIPPED', '192.168.0.17', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (18, '2025-08-03T00:00:00', '2025-08-03T00:05:00', 18, 'IN_PROGRESS', '192.168.0.18', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (19, '2025-08-04T00:00:00', '2025-08-04T00:05:00', 19, 'FAIL', '192.168.0.19', NOW());
INSERT INTO backups (id, created_at, ended_at, metadata_id, status, worker, updated_at)
VALUES (20, '2025-08-05T00:00:00', '2025-08-05T00:02:00', 20, 'SKIPPED', '192.168.0.20', NOW());