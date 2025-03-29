INSERT INTO account(username, password, name, created_at, updated_at)
VALUES ('barogo', '$2a$10$dPZoU8KQGiwvEh8laDI4Yu1phVj89mi2ApwbJmiZrtEpfqySC9yPO', '바로고', '2025-03-08 10:00:00',
        '2025-03-08 10:00:00');

INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '서울시 강남구 역삼동 123-1', 'PENDING', '2025-03-25 10:00:00', '2025-03-25 10:00:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '서울시 마포구 상암동 456-2', 'IN_PROGRESS', '2025-03-25 11:15:00', '2025-03-25 11:15:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '부산시 해운대구 우동 789-3', 'DELIVERED', '2025-03-26 12:30:00', '2025-03-26 12:30:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '대구시 수성구 범어동 123-4', 'PENDING', '2025-03-26 09:45:00', '2025-03-26 09:45:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '인천시 송도동 234-5', 'IN_PROGRESS', '2025-03-27 08:00:00', '2025-03-27 08:00:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '광주시 북구 용봉동 345-6', 'DELIVERED', '2025-03-27 10:15:00', '2025-03-27 10:15:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '대전시 유성구 온천동 456-7', 'PENDING', '2025-03-28 11:30:00', '2025-03-28 11:30:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '울산시 남구 삼산동 567-8', 'IN_PROGRESS', '2025-03-28 12:45:00', '2025-03-28 12:45:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '경기도 성남시 분당구 정자일동 678-9', 'DELIVERED', '2025-03-29 14:00:00', '2025-03-29 14:00:00');
INSERT INTO delivery (account_id, address, status, created_at, updated_at)
VALUES (1, '경기도 수원시 영통구 영통동 789-10', 'PENDING', '2025-03-29 15:15:00', '2025-03-29 15:15:00');
