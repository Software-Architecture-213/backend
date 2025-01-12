create table if not exists brands
(
    id           varchar(255) not null
        primary key,
    created_at   timestamp(6),
    display_name varchar(255),
    field        varchar(255),
    latitude     double precision,
    longitude    double precision,
    image_url    varchar(255),
    password     varchar(255),
    status       varchar(255)
        constraint brands_status_check
            check ((status)::text = ANY ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying])::text[])),
    updated_at   timestamp(6),
    username     varchar(255)
);

alter table brands
    owner to postgres;

INSERT INTO public.brands (id, created_at, display_name, field, latitude, longitude, image_url, password, status, updated_at, username) VALUES ('acac85c4-1625-4631-aa14-34e87dfc2f57', '2025-01-12 13:46:55.391217', 'Katinat', 'Drink', 1, 1, 'https://res.cloudinary.com/dlirzjnje/image/upload/w8zdqdilifvdyux09e2t?_a=DAGAACAVZAA0', '12345678', 'ACTIVE', '2025-01-12 13:47:52.731077', 'nguyen.thanhtri1221@gmail.com');
INSERT INTO public.brands (id, created_at, display_name, field, latitude, longitude, image_url, password, status, updated_at, username) VALUES ('43f31df6-7a5c-4571-9a5b-671724a6eca1', '2025-01-12 17:27:22.542196', 'Highland Coffee', 'Drink', 1, 1, 'https://res.cloudinary.com/dlirzjnje/image/upload/nxzisyrh0pukpi31s8eg?_a=DAGAACAVZAA0', '123456', 'ACTIVE', '2025-01-12 17:27:40.854878', 'highland@gmail.com');
INSERT INTO public.brands (id, created_at, display_name, field, latitude, longitude, image_url, password, status, updated_at, username) VALUES ('f8d14da3-470a-4207-8884-4d61856d6cc5', '2025-01-12 17:45:19.886772', 'Phe La', 'Drink', 1, 1, 'https://res.cloudinary.com/dlirzjnje/image/upload/svqhz1p7oamhoqcmbgbd?_a=DAGAACAVZAA0', '123456', 'ACTIVE', '2025-01-12 17:45:30.425145', 'phela@gmail.com');
INSERT INTO public.brands (id, created_at, display_name, field, latitude, longitude, image_url, password, status, updated_at, username) VALUES ('c49a7de7-6e8d-4cd1-87f8-8ab8ad0d04fe', '2025-01-12 17:58:45.846221', 'The Coffee House', 'Drink', 1, 1, 'https://res.cloudinary.com/dlirzjnje/image/upload/dpfdbhvq1ft2wtzo7b63?_a=DAGAACAVZAA0', '123456', 'ACTIVE', '2025-01-12 17:59:03.958939', 'tch@gmail.com');


create table if not exists promotions
(
    id               varchar(255) not null
        primary key,
    budget           double precision,
    created_at       timestamp(6),
    description      varchar(255),
    end_date         timestamp(6),
    games            text,
    image_url        varchar(255),
    name             varchar(255),
    remaining_budget double precision,
    start_date       timestamp(6),
    status           varchar(255)
        constraint promotions_status_check
            check ((status)::text = ANY
                   ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying, 'EXPIRED'::character varying, 'PAID'::character varying])::text[])),
    updated_at       timestamp(6),
    brand_id         varchar(255)
        constraint fk6s8a5igo269jqjvlsl2shl005
            references brands
);

alter table promotions
    owner to postgres;

INSERT INTO public.promotions (id, budget, created_at, description, end_date, games, image_url, name, remaining_budget, start_date, status, updated_at, brand_id) VALUES
    ('91bf8e05-d983-4251-9bc3-ba4fbbb5bf0c', 2000, '2025-01-12 13:55:37.417076', 'Welcome the New Year with Katinat and exciting offers everywhere', '2025-02-15 07:00:00.000000', '["1c4bc86f-89e3-40c0-bd85-dcf07fa9fa48"]', 'https://res.cloudinary.com/dlirzjnje/image/upload/cba8jdkwztbrvou6hnpt?_a=DAGAACAVZAA0', 'NEWYEAR2025', 2000, '2025-01-15 07:00:00.000000', 'ACTIVE', null, 'acac85c4-1625-4631-aa14-34e87dfc2f57');

INSERT INTO public.promotions (id, budget, created_at, description, end_date, games, image_url, name, remaining_budget, start_date, status, updated_at, brand_id) VALUES
    ('97ab0b3e-f7a5-4a58-8a26-036aca5ee29c', 300, '2025-01-12 17:35:50.491128', 'KHOE SẮC THĂNG HƯƠNG - GOLDEN LOTUS TEA (NEW) NOW AVAILABLE:
- GOLDEN LOTUS TEA WITH TARO PEARLS
- GOLDEN LOTUS TEA WITH COCONUT PEARLS', '2025-01-31 07:00:00.000000', '["f56f64d9-5cf4-4b62-b87b-bdf9e4d36a67"]', 'https://res.cloudinary.com/dlirzjnje/image/upload/spjzht4x56ebyyqhtulm?_a=DAGAACAVZAA0', 'KHOE SẮC THĂNG HƯƠNG', 300, '2025-01-16 07:00:00.000000', 'ACTIVE', null, '43f31df6-7a5c-4571-9a5b-671724a6eca1');

INSERT INTO public.promotions (id, budget, created_at, description, end_date, games, image_url, name, remaining_budget, start_date, status, updated_at, brand_id) VALUES
    ('aaa617eb-761c-42eb-bfab-b746e155e79c', 300, '2025-01-12 17:39:01.043075', 'You have 30 days from receiving the E-voucher to redeem it for a Highlands Card. After this period, your E-voucher will expire.

The Highlands Card can be used for payments like cash at the store.', '2025-01-31 07:00:00.000000', '["3adad5b4-d9d5-42e6-b9fa-493b7bc2c758"]', 'https://res.cloudinary.com/dlirzjnje/image/upload/p9ny82alzudna22flb1j?_a=DAGAACAVZAA0', 'WELCOME THE NEW YEAR WITH HIGHLAND', 300, '2025-01-18 07:00:00.000000', 'ACTIVE', null, '43f31df6-7a5c-4571-9a5b-671724a6eca1');

INSERT INTO public.promotions (id, budget, created_at, description, end_date, games, image_url, name, remaining_budget, start_date, status, updated_at, brand_id) VALUES
    ('42c52191-c733-45f1-9803-7064200d2baa', 100, '2025-01-12 17:49:53.837409', '31-12-2024
Thank you for making the year enjoyable together with Phê La!', '2025-01-31 07:00:00.000000', '["SHAKE"]', 'https://res.cloudinary.com/dlirzjnje/image/upload/tvoir33ee206drz89zz8?_a=DAGAACAVZAA0', 'GRATITUDE GIFT', 100, '2025-01-19 07:00:00.000000', 'ACTIVE', null, 'f8d14da3-470a-4207-8884-4d61856d6cc5');

INSERT INTO public.promotions (id, budget, created_at, description, end_date, games, image_url, name, remaining_budget, start_date, status, updated_at, brand_id) VALUES
    ('909222cf-0414-458d-b8e9-820616c2d5b3', 150, '2025-01-12 17:52:13.812679', 'Cherish and inherit that beauty, Phê La creates with fresh Gac fruit, fragrant Green Beans, skillfully blended with Special Oolong Tea, telling the story of Tet Chill through “red gift” Gac Pearls.', '2025-01-31 07:00:00.000000', '["SHAKE","QUIZ"]', 'https://res.cloudinary.com/dlirzjnje/image/upload/uslommahsaijugic6ezo?_a=DAGAACAVZAA0', 'Gac Pearls – Chill Tet Very Fresh', 150, '2025-01-20 07:00:00.000000', 'ACTIVE', null, 'f8d14da3-470a-4207-8884-4d61856d6cc5');

INSERT INTO public.promotions (id, budget, created_at, description, end_date, games, image_url, name, remaining_budget, start_date, status, updated_at, brand_id) VALUES
    ('443a3aca-d687-4c19-bbbd-0c09ac9b40c0', 100, '2025-01-12 18:02:06.370141', 'Enjoy the weekend with The Coffee House', '2025-01-31 07:00:00.000000', '["QUIZ"]', 'https://res.cloudinary.com/dlirzjnje/image/upload/ibtxkwtfmca5jyqnua09?_a=DAGAACAVZAA0', 'TCHWEEKEND', 100, '2025-01-22 07:00:00.000000', 'ACTIVE', null, 'c49a7de7-6e8d-4cd1-87f8-8ab8ad0d04fe');


create table if not exists vouchers
(
    id            varchar(255) not null
        primary key,
    code          varchar(255),
    created_at    timestamp(6),
    create_counts integer,
    description   varchar(255),
    expired_at    timestamp(6),
    image_url     varchar(255),
    max_counts    integer,
    status        varchar(255)
        constraint vouchers_status_check
            check ((status)::text = ANY
                   ((ARRAY ['ACTIVE'::character varying, 'EXPIRED'::character varying, 'INACTIVE'::character varying])::text[])),
    type          varchar(255)
        constraint vouchers_type_check
            check ((type)::text = ANY ((ARRAY ['ONLINE'::character varying, 'OFFLINE'::character varying])::text[])),
    updated_at    timestamp(6),
    value         double precision,
    value_type    varchar(255)
        constraint vouchers_value_type_check
            check ((value_type)::text = ANY
                   ((ARRAY ['FIXED'::character varying, 'PERCENTAGE'::character varying, 'ITEM'::character varying, 'FREE'::character varying])::text[])),
    promotion_id  varchar(255)
        constraint fk21n4v3cou386fc2ggisy2pskt
            references promotions
);

alter table vouchers
    owner to postgres;

INSERT INTO public.vouchers (id, code, created_at, create_counts, description, expired_at, image_url, max_counts, status, type, updated_at, value, value_type, promotion_id)
VALUES
    ('1ac4fe81-f750-4b4c-8413-09d197ad5c56', 'NEWYEAR2025_ONL', '2025-01-12 13:58:12.517791', 0, 'Discount up to 20% for orders over 100,000 VND', '2025-01-15 07:00:00.000000', 'https://res.cloudinary.com/dlirzjnje/image/upload/cba8jdkwztbrvou6hnpt?_a=DAGAACAVZAA0', 10, 'ACTIVE', 'ONLINE', null, 18, 'PERCENTAGE', '91bf8e05-d983-4251-9bc3-ba4fbbb5bf0c');

INSERT INTO public.vouchers (id, code, created_at, create_counts, description, expired_at, image_url, max_counts, status, type, updated_at, value, value_type, promotion_id)
VALUES
    ('266f22ff-e187-4830-875e-c947c0478dbb', 'KSTH', '2025-01-12 17:41:35.018290', 0, 'Discount up to 20% when buying the new Golden Lotus Tea at online stores nationwide', '2025-03-01 07:00:00.000000', 'https://res.cloudinary.com/dlirzjnje/image/upload/u1etnb4iczhlxc0re79z?_a=DAGAACAVZAA0', 300, 'ACTIVE', 'ONLINE', null, 20, 'PERCENTAGE', '97ab0b3e-f7a5-4a58-8a26-036aca5ee29c');

INSERT INTO public.vouchers (id, code, created_at, create_counts, description, expired_at, image_url, max_counts, status, type, updated_at, value, value_type, promotion_id)
VALUES
    ('4a5a6ac6-e6a4-4729-921f-1a845ce0274f', 'HIGHLAND2025', '2025-01-12 17:43:12.531942', 0, 'Voucher 100k from Highland at all stores', '2025-02-01 07:00:00.000000', 'https://res.cloudinary.com/dlirzjnje/image/upload/mn9wytwbhwotv34jlvdk?_a=DAGAACAVZAA0', 300, 'ACTIVE', 'OFFLINE', null, 100, 'FIXED', 'aaa617eb-761c-42eb-bfab-b746e155e79c');

INSERT INTO public.vouchers (id, code, created_at, create_counts, description, expired_at, image_url, max_counts, status, type, updated_at, value, value_type, promotion_id)
VALUES
    ('e462d320-b49e-4606-ba7b-2f0d4eeaadb2', 'TRIAN2024', '2025-01-12 17:53:37.048435', 6, '50% discount when buying the Happy Chill Year 2025 Tet Gift Box', '2025-02-01 07:00:00.000000', 'https://res.cloudinary.com/dlirzjnje/image/upload/yz5osw3uqvvsjlac6kre?_a=DAGAACAVZAA0', 150, 'ACTIVE', 'OFFLINE', null, 50, 'PERCENTAGE', '42c52191-c733-45f1-9803-7064200d2baa');

INSERT INTO public.vouchers (id, code, created_at, create_counts, description, expired_at, image_url, max_counts, status, type, updated_at, value, value_type, promotion_id)
VALUES
    ('fb2cabc2-889d-4ac7-a7ae-43298ce4d550', 'TETCHILL', '2025-01-12 17:55:16.396465', -3, 'Free Gac Pearl topping at Phê La', '2025-01-30 07:00:00.000000', 'https://res.cloudinary.com/dlirzjnje/image/upload/s9bfhepewulwlkbswcmp?_a=DAGAACAVZAA0', 150, 'ACTIVE', 'OFFLINE', null, 0, 'FREE', '909222cf-0414-458d-b8e9-820616c2d5b3');

INSERT INTO public.vouchers (id, code, created_at, create_counts, description, expired_at, image_url, max_counts, status, type, updated_at, value, value_type, promotion_id)
VALUES
    ('0c668329-88f0-4ae3-8b1f-45d5dfb5c381', 'WEEKEND', '2025-01-12 18:03:22.539176', 0, 'Free shipping on weekends from Saturday to Sunday, available all hours', '2025-01-22 07:00:00.000000', 'https://res.cloudinary.com/dlirzjnje/image/upload/bks8epszqizp2knvi6v4?_a=DAGAACAVZAA0', 100, 'ACTIVE', 'ONLINE', null, 0, 'FREE', '443a3aca-d687-4c19-bbbd-0c09ac9b40c0');


create table if not exists branches
(
    id       varchar(255) not null
        primary key,
    address  varchar(255),
    brand_id varchar(255),
    lat      double precision,
    lng      double precision,
    is_main  boolean      not null,
    name     varchar(255)
);

alter table branches
    owner to postgres;

INSERT INTO public.branches (id, address, brand_id, lat, lng, is_main, name) VALUES ('8bb851f1-36f5-452b-bbc2-3091e25a0979', '31 Thao Dien, Ward 2', 'acac85c4-1625-4631-aa14-34e87dfc2f57', 10.80446, 106.73702, false, 'Katinat Thao Dien');
INSERT INTO public.branches (id, address, brand_id, lat, lng, is_main, name) VALUES ('87744a46-8540-4424-a446-39fe27ffc2d0', '10B Ton Duc Thang, Ward 1', 'acac85c4-1625-4631-aa14-34e87dfc2f57', 10.77622, 106.7073, true, 'Katinat Ben Bach Dang');
INSERT INTO public.branches (id, address, brand_id, lat, lng, is_main, name) VALUES ('ced8157b-ba2c-4b48-81c2-fe2c8e94a0b0', '89A Xuan Thuy, Ward 2', 'f8d14da3-470a-4207-8884-4d61856d6cc5', 10.80431, 106.73557, true, 'Phê La Thảo Dien');
INSERT INTO public.branches (id, address, brand_id, lat, lng, is_main, name) VALUES ('0e9a7921-f945-472c-960f-70ce4282074d', '130 Thao Dien, Ward 2', '43f31df6-7a5c-4571-9a5b-671724a6eca1', 10.80398, 106.73274, false, 'Highland Thao Dien');
INSERT INTO public.branches (id, address, brand_id, lat, lng, is_main, name) VALUES ('c566670d-afd3-469c-9af1-6c2d9de8ee8c', '57 Xuan Thuy, Ward 2', 'c49a7de7-6e8d-4cd1-87f8-8ab8ad0d04fe', 10.80398, 106.73274, false, 'The Coffee House Xuan Thủy');
