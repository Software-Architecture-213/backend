

-- 1. Brand Table
CREATE TABLE Brand (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    display_name VARCHAR(255) NOT NULL UNIQUE,
    image_url TEXT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    field VARCHAR(255) NOT NULL,
    gps_latitude DOUBLE PRECISION NOT NULL,
    gps_longitude DOUBLE PRECISION NOT NULL,
    status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'inactive')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Notification Table
CREATE TABLE Notification (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(10) NOT NULL CHECK (type IN ('promotion', 'game', 'system')),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Voucher Table
CREATE TABLE Voucher (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(10) NOT NULL CHECK (type IN ('online', 'offline')),
    image_url TEXT,
    value_type VARCHAR(10) NOT NULL CHECK (value_type IN ('fixed', 'percentage', 'item', 'free')),
    value NUMERIC(10, 2) NOT NULL,
    description TEXT,
    expired_at TIMESTAMP NOT NULL,
    status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'expired')),
    promotion_id UUID NOT NULL,
    max_counts INTEGER DEFAULT 1,
    created_counts INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. VoucherUser Table
CREATE TABLE VoucherUser (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID NOT NULL,
    email VARCHAR(255),
    voucher_id UUID NOT NULL,
    qr_code VARCHAR(255),
    status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'redeemed', 'expired')),
    redeemed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (voucher_id) REFERENCES Voucher (id)
);

-- 5. ConversionRule Table
CREATE TABLE ConversionRule (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    voucher_id UUID NOT NULL,
    game_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (voucher_id) REFERENCES Voucher (id),
);

-- 6. ConversionRuleItem Table
CREATE TABLE ConversionRuleItem (
  conversion_rule_id UUID NOT NULL,
  item_id UUID NOT NULL,
  quantity INTEGER NOT NULL,

  PRIMARY KEY (conversion_rule_id, item_id),
)
