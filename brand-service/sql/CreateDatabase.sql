create table branches
(
    id      varchar(255) not null
        primary key,
    address varchar(255),
    lat     double precision,
    lng     double precision,
    is_main boolean      not null,
    name    varchar(255)
);

alter table branches
    owner to postgres;

create table brands
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

create table orders
(
    id        varchar(255)     not null
        primary key,
    amount    double precision not null,
    brand_id  varchar(255)     not null,
    create_at timestamp(6),
    currency  varchar(255),
    order_id  varchar(255)
);

alter table orders
    owner to postgres;

create table promotions
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

create table vouchers
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

create table voucher_user
(
    id          varchar(255) not null
        primary key,
    created_at  timestamp(6),
    qr_code     varchar(255),
    redeemed_at timestamp(6),
    status      varchar(255)
        constraint voucher_user_status_check
            check ((status)::text = ANY
        ((ARRAY ['ACTIVE'::character varying, 'REDEEMED'::character varying, 'EXPIRED'::character varying])::text[])),
    updated_at  timestamp(6),
    user_id     bytea,
    voucher_id  varchar(255)
        constraint fk1jr8kf44d8ukrnhrn497meo52
            references vouchers
);

alter table voucher_user
    owner to postgres;

-- CREATE TABLE Notification (
--     id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
--     userId UUID NOT NULL,
--     message TEXT NOT NULL,
--     type VARCHAR(10) NOT NULL CHECK (type IN ('PROMOTION', 'GAME', 'SYSTEM')),
--     isRead BOOLEAN DEFAULT FALSE,
--     createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );
--
--
-- CREATE TABLE ConversionRule (
--     id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
--     voucherId UUID NOT NULL,
--     gameId UUID,
--     createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY (voucherId) REFERENCES Voucher (id)
-- );
--
-- CREATE TABLE ConversionRuleItem (
--   conversionRuleId UUID NOT NULL,
--   itemId UUID NOT NULL,
--   quantity INTEGER NOT NULL,
--
--   PRIMARY KEY (conversionRuleId, itemId)
-- )

