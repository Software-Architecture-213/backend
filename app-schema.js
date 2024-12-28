// Trò Chơi
GameSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Game ID
	name: { type: String, required: true }, // Tên trò chơi
	type: {
		type: String,
		enum: ["quiz", "shake"],
		required: true,
	}, // Loại trò chơi
	imageUrl: { type: String }, // Hình ảnh đại diện trò chơi
	description: { type: String }, // Giới thiệu trò chơi
	guideline: { type: String },
	allowItemExchange: { type: Boolean, default: false }, // Cho phép trao đổi vật phẩm hay không
	difficulty: {
		type: String,
		enum: ["easy", "medium", "hard"],
		default: "medium",
	}, // Mức độ khó của trò chơi
	promotionId: { type: mongoose.Schema.Types.ObjectId, ref: "Promotion" }, // Liên kết với Promotion
	createdAt: { type: Date, default: Date.now }, // Ngày tạo trò chơi
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật trò chơi
});

// Vật Phẩm
ItemSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Item ID
	name: { type: String, required: true }, // Tên vật phẩm
	description: { type: String }, // Mô tả vật phẩm
	imageUrl: { type: String }, // Hình ảnh vật phẩm
	rarity: {
		type: String,
		enum: ["common", "uncommon", "rare", "epic", "legendary"],
		default: "common",
	}, // Hiếm có của vật phẩm
	tradable: { type: Boolean, default: true }, // Có thể trao đổi hay không
	maxQuantity: { type: Number, default: 1 }, // Số lượng tối đa mà người dùng có thể sở hữu
	gameId: { type: mongoose.Schema.Types.ObjectId, ref: "Game" }, // Liên kết tới trò chơi
	createdAt: { type: Date, default: Date.now }, // Ngày tạo vật phẩm
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật vật phẩm
});

ItemUserSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Item_User ID
	userId: { type: mongoose.Schema.Types.ObjectId, ref: "User" }, // Người sở hữu
	itemId: { type: mongoose.Schema.Types.ObjectId, ref: "Item" }, // Vật phẩm
	gameId: { type: mongoose.Schema.Types.ObjectId, ref: "Game" }, // Trò chơi (để dễ tiến hành trao đổi trong 1 game)
	quantity: { type: Number, default: 1 }, // Số lượng
	createdAt: { type: Date, default: Date.now }, // Ngày sở hữu
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật
});

// Chiến Dịch Khuyến Mãi
PromotionSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Promotion ID
	name: { type: String, required: true }, // Tên khuyến mãi
	description: { type: String }, // Mô tả khuyến mãi
	imageUrl: { type: String }, // Hình ảnh khuyến mãi
	startDate: { type: Date, required: true }, // Thời gian bắt đầu
	endDate: { type: Date, required: true }, // Thời gian kết thúc
	brandId: {
		type: mongoose.Schema.Types.ObjectId,
		ref: "Brand",
		required: true,
	}, // Liên kết với thương hiệu
	budget: { type: Number, default: 0 }, // Ngân sách dành cho khuyến mãi
	remainingBudget: { type: Number }, // Ngân sách còn lại
	status: {
		type: String,
		enum: ["active", "inactive", "completed"],
		default: "active",
	}, // Trạng thái khuyến mãi
	vouchers: [{ type: mongoose.Schema.Types.ObjectId, ref: "Voucher" }], // Danh sách voucher trong khuyến mãi
	games: [{ type: mongoose.Schema.Types.ObjectId, ref: "Game" }], // Danh sách trò chơi tham gia khuyến mãi
	createdAt: { type: Date, default: Date.now }, // Ngày tạo khuyến mãi
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật khuyến mãi
});

// Voucher Giảm Giá (Schema này sẽ là base để tạo các voucher cho người dùng)
VoucherSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Voucher ID
	code: { type: String, required: true, unique: true }, // Tên mã voucher chung của sự kiện (VD: KATINAT20)
	type: {
		type: String,
		enum: ["online", "offline"],
		required: true,
	}, // Loại voucher
	imageUrl: { type: String }, // Hình ảnh voucher
	valueType: {
		type: String,
		enum: ["fixed", "percentage", "item", "free"],
		required: true,
	},
	value: {
		type: Number,
		required: true,
	},
	description: { type: String }, // Mô tả voucher
	expiredAt: { type: Date, required: true }, // Ngày hết hạn
	status: {
		type: String,
		enum: ["active", "expired"],
		default: "active",
	}, // Trạng thái voucher
	promotionId: { type: mongoose.Schema.Types.ObjectId, ref: "Promotion" }, // Liên kết tới Promotion
	maxCounts: { type: Number, default: 1 }, // Số voucher tối đa từ sự kiện
	createdCounts: { type: Number, default: 0 }, // Số voucher đã phát hành
	createdAt: { type: Date, default: Date.now }, // Ngày tạo voucher
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật voucher
});

VoucherUserSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // UserVoucher ID
	userId: { type: mongoose.Schema.Types.ObjectId, ref: "User" }, // Người sở hữu
	email: { type: String }, // Email người sở hữu
	voucherId: { type: mongoose.Schema.Types.ObjectId, ref: "Voucher" }, // Voucher
	qrCode: { type: String }, // QR Code
	status: {
		type: String,
		enum: ["active", "redeemed", "expired"],
		default: "active",
	},
	redeemedAt: { type: Date }, // Ngày sử dụng voucher
	createdAt: { type: Date, default: Date.now }, // Ngày sở hữu
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật
});

// A schema for defining the rules of converting items to vouchers
ConversionRuleSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Rule ID
	requiredItems: [
		{
			itemId: { type: mongoose.Schema.Types.ObjectId, ref: "ItemSchema" },
			quantity: { type: Number, required: true },
		},
	], // List of required items with their quantities
	voucherId: { type: mongoose.Schema.Types.ObjectId, ref: "VoucherSchema" }, // Voucher to be rewarded
	gameId: { type: mongoose.Schema.Types.ObjectId, ref: "GameSchema" },
	createdAt: { type: Date, default: Date.now }, // Ngày tạo rule
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật rule
});

// Giao Dịch (Tặng vật phẩm)
ItemTransactionSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Transaction ID
	senderId: { type: mongoose.Schema.Types.ObjectId, ref: "User" }, // Người gửi
	receiverId: { type: mongoose.Schema.Types.ObjectId, ref: "User" }, // Người nhận
	itemId: { type: mongoose.Schema.Types.ObjectId, ref: "Item" }, // Vật phẩm được gửi
	expiredAt: { type: Date }, // Ngày hết hạn
	status: {
		type: String,
		enum: ["pending", "accepted", "rejected"],
		default: "pending",
	},
	createdAt: { type: Date, default: Date.now }, // Ngày gửi
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật
});

// Thương hiệu (Web)
BrandSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Brand ID
	displayName: { type: String, required: true, unique: true }, // Tên thương hiệu
	imageUrl: { type: String }, // Hình ảnh thương hiệu
	username: { type: String, required: true, unique: true }, // Tên đăng nhập
	password: { type: String, required: true }, // Mật khẩu
	field: { type: String, required: true }, // Lĩnh vực hoạt động
	gps: {
		latitude: { type: Number, required: true },
		longitude: { type: Number, required: true },
	}, // Tọa độ GPS
	status: {
		type: String,
		enum: ["active", "inactive"],
		default: "active",
	}, // Trạng thái thương hiệu
	createdAt: { type: Date, default: Date.now }, // Ngày tạo
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật
});

// Người dùng (Mobile)
UserSchema({
	_id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // ID định danh duy nhất
	username: { type: String, required: true, unique: true }, // Tên đăng nhập
	password: { type: String, required: true }, // Mật khẩu
	email: { type: String, unique: true }, // Email
	role: {
		type: String,
		enum: ["user", "admin"],
	}, // Vai trò người dùng
	displayName: { type: String, required: true }, // Tên hiển thị
	avatarUrl: { type: String }, // Ảnh đại diện
	dateOfBirth: { type: Date }, // Ngày sinh
	phoneNumber: { type: String, unique: true, required: true }, // Số điện thoại (để xác thực OTP)
	gender: { type: String, enum: ["male", "female", "other"] }, // Giới tính
	facebookAccount: { type: String }, // Tài khoản Facebook (nếu có)
	status: {
		type: String,
		enum: ["active", "inactive"],
		default: "active",
	}, // Trạng thái người dùng
	// Consider deleting inventory field and using ItemUserSchema instead
	//inventory: [{ type: mongoose.Schema.Types.ObjectId, ref: "Item" }], // Danh sách vật phẩm người dùng sở hữu
	configs: { type: Number, default: 10 }, // Lượt chơi của người dùng
	favoritePromotions: [
		{ type: mongoose.Schema.Types.ObjectId, ref: "Promotion" },
	], // Danh sách khuyến mãi yêu thích
	createdAt: { type: Date, default: Date.now }, // Ngày tạo
	updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật
});

UserGameSchema({
	userId: { type: mongoose.Schema.Types.ObjectId, ref: "User" },
	gameId: { type: mongoose.Schema.Types.ObjectId, ref: "Game" },
	score: { type: Number, default: 0 },
	remainingTurns: { type: Number, default: 10 },
	earnedTurns: { type: Number, default: 0 },
	createdAt: { type: Date, default: Date.now },
	updatedAt: { type: Date, default: Date.now },
});

NotificationSchema({
	userId: { type: mongoose.Schema.Types.ObjectId, ref: "User" },
	message: { type: String, required: true },
	type: {
		type: String,
		enum: ["promotion", "game", "system"],
		required: true,
	},
	isRead: { type: Boolean, default: false },
	createdAt: { type: Date, default: Date.now },
	updatedAt: { type: Date, default: Date.now },
});
