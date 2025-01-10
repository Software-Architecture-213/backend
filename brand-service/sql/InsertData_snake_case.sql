INSERT INTO brands (id, display_name, image_url, username, password, field, latitude, longitude, status, created_at, updated_at) VALUES
('b2bba07a-2cfc-44b5-9836-d1567c527a6f', 'Katinat', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735289312/brand/katinat.jpg', 'katinat_official', 'hashed_password_1', 'Café & Bakery', 21.0285, 105.8542, 'ACTIVE', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('1fa17445-8b76-4fdd-821d-fcb06266d8a2', 'PhucLong', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735289416/brand/phuclong.png', 'phuclong_vietnam', 'hashed_password_2', 'Café & Beverages', 10.762622, 106.660172, 'ACTIVE', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('dcb5d84b-b235-4d5d-8049-38349e9a9029', 'Highland Coffee', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735261239/brand/highland.png', 'highland_official', 'hashed_password_3', 'Café & Beverages', 21.0285, 105.8542, 'ACTIVE', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('aa9c7317-77bc-4f79-821f-b2f7e5a2e3c4', 'Pizza Company', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735289466/brand/pizzacompany.jpg', 'pizza_company_vn', 'hashed_password_4', 'Pizza & Fast Food', 10.756, 106.6952, 'ACTIVE', '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z');

INSERT INTO vouchers (id, code, type, image_url, value_type, value, description, expired_at, status, promotion_id, max_counts, create_counts, created_at, updated_at) VALUES
('1f8e0044-5305-4782-a6a0-d3f98a3d106d', 'KATINAT2024', 'ONLINE', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290873/voucher/katinat_20_off.png', 'PERCENTAGE', 20, 'Get 20% off on your entire order! Valid online for the holiday season at Katinat.', '2025-01-05T23:59:59Z', 'ACTIVE', 'f8d74c87-1d9d-4f8f-9099-4443471e8124', 1000, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('aef28f85-8d2c-4503-a4e2-3ec1d6b5a3fd', 'KATINATGIFT2024', 'OFFLINE', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290953/voucher/katinat_50k.png', 'FIXED', 50000, 'Receive a 50,000 VND gift voucher for purchases over 300,000 VND at Katinat. Valid for in-store use.', '2025-01-05T23:59:59Z', 'ACTIVE', 'f8d74c87-1d9d-4f8f-9099-4443471e8124', 500, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('a7c6e3db-53c5-47bb-9d85-50c470dd024f', 'KATINATFREEDRINK2024', 'OFFLINE', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290983/voucher/katinat_free_drink.jpg', 'ITEM', 0, 'Get a free drink with any purchase above 100,000 VND at Katinat stores. Valid for offline orders only.', '2025-01-05T23:59:59Z', 'ACTIVE', 'f8d74c87-1d9d-4f8f-9099-4443471e8124', 300, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('df345b84-d61e-4c4d-b8d3-f9ea24f0f455', 'PHUCLONG2024', 'ONLINE', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735291017/voucher/phuclong_15_percent.jpg', 'PERCENTAGE', 15, 'Get 15% off your online order at PhucLong this New Year season!', '2025-01-10T23:59:59Z', 'ACTIVE', '9d7e5678-3de1-4bfc-8c60-cd16e1799b24', 800, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z'),
('e8c2d7d9-35be-4bfb-9a6b-6045d3e3a56d', 'PHUCLONGGIFT2024', 'OFFLINE', 'https://res.cloudinary.com/dlirzjnje/image/upload/v1735291048/voucher/phuclong_gift.jpg', 'ITEM', 0, 'Free gift set with every purchase above 500,000 VND at PhucLong stores.', '2025-01-10T23:59:59Z', 'ACTIVE', '9d7e5678-3de1-4bfc-8c60-cd16e1799b24', 300, 0, '2024-12-24T10:00:00Z', '2024-12-24T10:00:00Z');

INSERT INTO voucher_user (id, user_id, voucher_id, qr_code, status, redeemed_at, created_at, updated_at) VALUES
('58c1e1ab-3e2f-4b69-b3f3-a62bcb4f764a', '62f7e567-50b7-49be-8f9a-e43120ad7f24', '1f8e0044-5305-4782-a6a0-d3f98a3d106d', 'qr-62f7e567-1f8e0044', 'ACTIVE', NULL, '2024-12-24T12:00:00Z', '2024-12-24T12:00:00Z'),
('87453d2f-8f1a-4097-99ab-3136de59fa6d', 'd219c0e1-63da-4162-876a-f9090e2f6a59', 'aef28f85-8d2c-4503-a4e2-3ec1d6b5a3fd', 'qr-d219c0e1-aef28f85', 'ACTIVE', NULL, '2024-12-24T12:05:00Z', '2024-12-24T12:05:00Z'),
('c198e4bf-9411-4ad8-bc49-2ecdfaeb4ef8', '3c41bc11-7fae-4bb9-97a5-48c35b87cf7e', 'a7c6e3db-53c5-47bb-9d85-50c470dd024f', 'qr-3c41bc11-a7c6e3db', 'ACTIVE', NULL, '2024-12-24T12:10:00Z', '2024-12-24T12:10:00Z'),
('f74b69b1-09f8-48b8-b5e8-9f2c9f78ad7d', '9a41b8fc-75e9-4888-87a7-d3d01f78f829', 'df345b84-d61e-4c4d-b8d3-f9ea24f0f455', 'qr-9a41b8fc-df345b84', 'ACTIVE', NULL, '2024-12-24T12:15:00Z', '2024-12-24T12:15:00Z'),
('e95f8ed3-6cf9-4a98-8f9f-d8c3c649fa7d', '3e9bc0e5-75da-4987-99bc-0f8a12345f7e', 'e8c2d7d9-35be-4bfb-9a6b-6045d3e3a56d', 'qr-3e9bc0e5-e8c2d7d9', 'ACTIVE', NULL, '2024-12-24T12:20:00Z', '2024-12-24T12:20:00Z');

INSERT INTO promotions
(
    id, 
    brand_id, 
    name, 
    description, 
    image_url, 
    start_date, 
    end_date, 
    status, 
    games, 
    created_at, 
    updated_at
)
VALUES
(
    'f8d74c87-1d9d-4f8f-9099-4443471e8124',
    'b2bba07a-2cfc-44b5-9836-d1567c527a6f',
    'Katinat Holiday Special',
    'Enjoy a 20% discount on all items at Katinat this holiday season! Valid for dine-in and takeout.',
    'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290576/promotion/katinat_holiday.jpg',
    '2024-12-25T00:00:00Z',
    '2025-01-05T23:59:59Z',
    'ACTIVE',
    '["f56f64d9-5cf4-4b62-b87b-bdf9e4d36a67", "1c4bc86f-89e3-40c0-bd85-dcf07fa9fa48"]',
    '2024-12-24T10:00:00Z',
    '2024-12-24T10:00:00Z'
),
(
    '9d7e5678-3de1-4bfc-8c60-cd16e1799b24',
    '1fa17445-8b76-4fdd-821d-fcb06266d8a2',
    'PhucLong New Year Gift Set',
    'Celebrate the New Year with PhucLong! Get a free gift set when you spend over 500,000 VND.',
    'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290633/promotion/phuclong_new_year.jpg',
    '2024-12-28T00:00:00Z',
    '2025-01-10T23:59:59Z',
    'ACTIVE',
    '["3adad5b4-d9d5-42e6-b9fa-493b7bc2c758", "b7685e4c-746e-4c52-bb9e-7a9f1e6ad0d5"]',
    '2024-12-24T10:00:00Z',
    '2024-12-24T10:00:00Z'
),
(
    '3dbb654a-b3d4-46fc-b375-71083b90a12d',
    'dcb5d84b-b235-4d5d-8049-38349e9a9029',
    'Highland Coffee Christmas Combo',
    'Get a free Christmas-themed mug with any purchase of a large coffee at Highland Coffee this Christmas!',
    'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290522/promotion/highland_xmas.png',
    '2024-12-20T00:00:00Z',
    '2024-12-30T23:59:59Z',
    'ACTIVE',
    '[]',
    '2024-12-24T10:00:00Z',
    '2024-12-24T10:00:00Z'
),
(
    'a24f2c0d-56ad-4dfe-b5c7-dc8a67994c60',
    'aa9c7317-77bc-4f79-821f-b2f7e5a2e3c4',
    'Pizza Company Family Deal',
    'Enjoy a large pizza and 2 sides for just 299,000 VND at Pizza Company. Perfect for family dinners!',
    'https://res.cloudinary.com/dlirzjnje/image/upload/v1735290683/promotion/pizzacompany_family.jpg',
    '2024-12-25T00:00:00Z',
    '2025-01-15T23:59:59Z',
    'ACTIVE',
    '[]',
    '2024-12-24T10:00:00Z',
    '2024-12-24T10:00:00Z'
);
