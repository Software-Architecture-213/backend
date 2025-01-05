-- Insert vouchers into Voucher table
INSERT INTO Voucher (id, code, type, imageUrl, valueType, value, description, expiredAt, status, promotionId, maxCounts, createdCounts, createdAt, updatedAt)
VALUES
('1f8e0044-5305-4782-a6a0-d3f98a3d106d', 'KATINAT2024', 'online', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290873/voucher/katinat_20_off.png', 'percentage', 20, 'Get 20% off on your entire order! Valid online for the holiday season at Katinat.', '2025-01-05T23:59:59Z', 'active', 'f8d74c87-1d9d-4f8f-9099-4443471e8124', 1000, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('aef28f85-8d2c-4503-a4e2-3ec1d6b5a3fd', 'KATINATGIFT2024', 'offline', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290953/voucher/katinat_50k.png', 'fixed', 50000, 'Receive a 50,000 VND gift voucher for purchases over 300,000 VND at Katinat. Valid for in-store use.', '2025-01-05T23:59:59Z', 'active', 'f8d74c87-1d9d-4f8f-9099-4443471e8124', 500, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('a7c6e3db-53c5-47bb-9d85-50c470dd024f', 'KATINATFREEDRINK2024', 'offline', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290983/voucher/katinat_free_drink.jpg', 'item', 0, 'Get a free drink with any purchase above 100,000 VND at Katinat stores. Valid for offline orders only.', '2025-01-05T23:59:59Z', 'active', 'f8d74c87-1d9d-4f8f-9099-4443471e8124', 300, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('df345b84-d61e-4c4d-b8d3-f9ea24f0f455', 'PHUCLONG2024', 'online', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735291017/voucher/phuclong_15_percent.jpg', 'percentage', 15, 'Get 15% off your online order at PhucLong this New Year season!', '2025-01-10T23:59:59Z', 'active', '9d7e5678-3de1-4bfc-8c60-cd16e1799b24', 800, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('e8c2d7d9-35be-4bfb-9a6b-6045d3e3a56d', 'PHUCLONGGIFT2024', 'offline', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735291048/voucher/phuclong_gift.jpg', 'item', 0, 'Free gift set with every purchase above 500,000 VND at PhucLong stores.', '2025-01-10T23:59:59Z', 'active', '9d7e5678-3de1-4bfc-8c60-cd16e1799b24', 300, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z');

INSERT INTO VoucherUser (id, userId, email, voucherId, qrCode, status, redeemedAt, createdAt, updatedAt)
VALUES
('58c1e1ab-3e2f-4b69-b3f3-a62bcb4f764a', '62f7e567-50b7-49be-8f9a-e43120ad7f24', 'user1@example.com', '1f8e0044-5305-4782-a6a0-d3f98a3d106d', 'qr-62f7e567-1f8e0044', 'active', NULL, '2024-12-24T12:00:00Z', '2024-12-24T12:00:00Z'),
('87453d2f-8f1a-4097-99ab-3136de59fa6d', 'd219c0e1-63da-4162-876a-f9090e2f6a59', 'user2@example.com', 'aef28f85-8d2c-4503-a4e2-3ec1d6b5a3fd', 'qr-d219c0e1-aef28f85', 'active', NULL, '2024-12-24T12:05:00Z', '2024-12-24T12:05:00Z'),
('c198e4bf-9411-4ad8-bc49-2ecdfaeb4ef8', '3c41bc11-7fae-4bb9-97a5-48c35b87cf7e', 'user3@example.com', 'a7c6e3db-53c5-47bb-9d85-50c470dd024f', 'qr-3c41bc11-a7c6e3db', 'active', NULL, '2024-12-24T12:10:00Z', '2024-12-24T12:10:00Z'),
('f74b69b1-09f8-48b8-b5e8-9f2c9f78ad7d', '9a41b8fc-75e9-4888-87a7-d3d01f78f829', 'user4@example.com', 'df345b84-d61e-4c4d-b8d3-f9ea24f0f455', 'qr-9a41b8fc-df345b84', 'active', NULL, '2024-12-24T12:15:00Z', '2024-12-24T12:15:00Z'),
('e95f8ed3-6cf9-4a98-8f9f-d8c3c649fa7e', '62f7e567-50b7-49be-8f9a-e43120ad7f24', 'user1@example.com', 'e8c2d7d9-35be-4bfb-9a6b-6045d3e3a56d', 'qr-62f7e567-e8c2d7d9', 'active', NULL, '2024-12-24T12:20:00Z', '2024-12-24T12:20:00Z');

-- Insert Data into Brand Table

INSERT INTO Brand (id, displayName, imageUrl, username, password, field, gpsLatitude, gpsLongitude, status, createdAt, updatedAt)
VALUES
('b2bba07a-2cfc-44b5-9836-d1567c527a6f', 'Katinat', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735289312/brand/katinat.jpg', 'katinat_official', 'hashed_password_1', 'Café & Bakery', 21.0285, 105.8542, 'active', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('1fa17445-8b76-4fdd-821d-fcb06266d8a2', 'PhucLong', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735289416/brand/phuclong.png', 'phuclong_vietnam', 'hashed_password_2', 'Café & Beverages', 10.762622, 106.660172, 'active', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('dcb5d84b-b235-4d5d-8049-38349e9a9029', 'Highland Coffee', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735261239/brand/highland.png', 'highland_official', 'hashed_password_3', 'Café & Beverages', 21.0285, 105.8542, 'active', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('aa9c7317-77bc-4f79-821f-b2f7e5a2e3c4', 'Pizza Company', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735289466/brand/pizzacompany.jpg', 'pizza_company_vn', 'hashed_password_4', 'Pizza & Fast Food', 10.756, 106.6952, 'active', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z');

-- ConversionRule Table Inserts
INSERT INTO ConversionRule (id, voucherId, gameId, createdAt, updatedAt)
VALUES 
('63a6c58d-87d5-4561-b6f0-bdabfa6c9f9b', '1f8e0044-5305-4782-a6a0-d3f98a3d106d', 'f56f64d9-5cf4-4b62-b87b-bdf9e4d36a67', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('a7d84e1f-2f8d-43e1-9395-4fc689be19d3', 'a7c6e3db-53c5-47bb-9d85-50c470dd024f', '1c4bc86f-89e3-40c0-bd85-dcf07fa9fa48', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('d7c39856-d7d1-4f2e-a8a9-02d05ca8cd28', 'aef28f85-8d2c-4503-a4e2-3ec1d6b5a3fd', '1c4bc86f-89e3-40c0-bd85-dcf07fa9fa48', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('62a798db-2095-47ba-8779-c4d104a5c926', '1f8e0044-5305-4782-a6a0-d3f98a3d106d', '1c4bc86f-89e3-40c0-bd85-dcf07fa9fa48', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('a67f2f74-64b6-45f2-8c27-08055741c746', 'df345b84-d61e-4c4d-b8d3-f9ea24f0f455', '3adad5b4-d9d5-42e6-b9fa-493b7bc2c758', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('48f5777e-e8b6-43f1-937b-74d34ec9b378', 'e8c2d7d9-35be-4bfb-9a6b-6045d3e3a56d', 'b7685e4c-746e-4c52-bb9e-7a9f1e6ad0d5', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z');

-- ConversionRuleItem Table Inserts
INSERT INTO ConversionRuleItem (conversionRuleId, itemId, quantity)
VALUES 
('63a6c58d-87d5-4561-b6f0-bdabfa6c9f9b', 'ed1a908d-d193-4be4-9b07-dc8e1a5a7ac5', 1),
('a7d84e1f-2f8d-43e1-9395-4fc689be19d3', '1bbfd813-08ba-4787-91e4-79cf490d9c29', 1),
('d7c39856-d7d1-4f2e-a8a9-02d05ca8cd28', 'a18fe575-68b1-4535-9d6c-853fa4eaf401', 1),
('62a798db-2095-47ba-8779-c4d104a5c926', '13bf2b6c-b45a-47b4-a68a-3bda5f84094a', 1),
('a67f2f74-64b6-45f2-8c27-08055741c746', 'd97f9b63-81b3-45c5-a5f8-92f8b12b88d1', 1),
('48f5777e-e8b6-43f1-937b-74d34ec9b378', '2f9c3bfa-b9f9-488e-8ff3-604f0fbe7c87', 1);

