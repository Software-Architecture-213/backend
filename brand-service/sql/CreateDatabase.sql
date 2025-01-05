

-- 1. Brand Table
CREATE TABLE Brand (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    displayName VARCHAR(255) NOT NULL UNIQUE,
    imageUrl TEXT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    field VARCHAR(255) NOT NULL,
    gpsLatitude DOUBLE PRECISION NOT NULL,
    gpsLongitude DOUBLE PRECISION NOT NULL,
    status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'inactive')),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Notification Table
CREATE TABLE Notification (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    userId UUID NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(10) NOT NULL CHECK (type IN ('promotion', 'game', 'system')),
    isRead BOOLEAN DEFAULT FALSE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Voucher Table
CREATE TABLE Voucher (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(10) NOT NULL CHECK (type IN ('online', 'offline')),
    imageUrl TEXT,
    valueType VARCHAR(10) NOT NULL CHECK (valueType IN ('fixed', 'percentage', 'item', 'free')),
    value NUMERIC(10, 2) NOT NULL,
    description TEXT,
    expiredAt TIMESTAMP NOT NULL,
    status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'expired')),
    promotionId UUID NOT NULL,
    maxCounts INTEGER DEFAULT 1,
    createdCounts INTEGER DEFAULT 0,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. VoucherUser Table
CREATE TABLE VoucherUser (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    userId UUID NOT NULL,
    email VARCHAR(255),
    voucherId UUID NOT NULL,
    qrCode VARCHAR(255),
    status VARCHAR(10) DEFAULT 'active' CHECK (status IN ('active', 'redeemed', 'expired')),
    redeemedAt TIMESTAMP,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (voucherId) REFERENCES Voucher (id)
);

-- 5. ConversionRule Table
CREATE TABLE ConversionRule (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    voucherId UUID NOT NULL,
    gameId UUID,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (voucherId) REFERENCES Voucher (id)
);

-- 6. ConversionRuleItem Table
CREATE TABLE ConversionRuleItem (
  conversionRuleId UUID NOT NULL,
  itemId UUID NOT NULL,
  quantity INTEGER NOT NULL,

  PRIMARY KEY (conversionRuleId, itemId)
)
