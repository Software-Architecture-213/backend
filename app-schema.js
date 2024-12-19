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
  createdAt: { type: Date, default: Date.now }, // Ngày tạo trò chơi
  difficulty: {
    type: String,
    enum: ["easy", "medium", "hard"],
    default: "medium",
  }, // Mức độ khó của trò chơi
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
  gameId: { type: mongoose.Schema.Types.ObjectId, ref: "Game" }, // Liên kết tới trò chơi
  ownerId: { type: mongoose.Schema.Types.ObjectId, ref: "User" }, // Liên kết đến người sở hữu vật phẩm
});

// Chiến Dịch Khuyến Mãi
PromotionSchema({
  _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Promotion ID
  name: { type: String, required: true }, // Tên khuyến mãi
  description: { type: String }, // Mô tả khuyến mãi
  startDate: { type: Date, required: true }, // Thời gian bắt đầu
  endDate: { type: Date, required: true }, // Thời gian kết thúc
  brandId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Brand",
    required: true,
  }, // Liên kết với thương hiệu
  budget: { type: Number, default: 0 }, // Ngân sách dành cho khuyến mãi
  status: {
    type: String,
    enum: ["active", "inactive", "completed"],
    default: "active",
  }, // Trạng thái khuyến mãi
  vouchers: [{ type: mongoose.Schema.Types.ObjectId, ref: "Voucher" }], // Danh sách voucher trong khuyến mãi
  games: [{ type: mongoose.Schema.Types.ObjectId, ref: "Game" }], // Danh sách trò chơi tham gia khuyến mãi
});

// Voucher Giảm Giá
VoucherSchema({
  _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Voucher ID
  code: { type: String, required: true, unique: true }, // Mã voucher duy nhất
  qrCode: { type: String, required: true }, // QR Code
  image: { type: String }, // Hình ảnh voucher
  value: { type: Number, required: true }, // Giá trị của voucher
  description: { type: String }, // Mô tả voucher
  expirationDate: { type: Date, required: true }, // Ngày hết hạn
  status: {
    type: String,
    enum: ["active", "redeemed", "expired"],
    default: "active",
  }, // Trạng thái voucher
  promotionId: { type: mongoose.Schema.Types.ObjectId, ref: "Promotion" }, // Liên kết tới Promotion
  usageLimit: { type: Number, default: 1 }, // Số lần tối đa voucher có thể được sử dụng
  usedCount: { type: Number, default: 0 }, // Số lần voucher đã được sử dụng
});

// Giao Dịch (Tặng vật phẩm)
TransactionSchema({
  _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Transaction ID
  senderId: { type: mongoose.Schema.Types.ObjectId, ref: "User" }, // Người gửi
  receiverId: { type: mongoose.Schema.Types.ObjectId, ref: "User" }, // Người nhận
  itemId: { type: mongoose.Schema.Types.ObjectId, ref: "Item" }, // Vật phẩm được gửi
  gameId: { type: mongoose.Schema.Types.ObjectId, ref: "Game" }, // Trò chơi liên quan
  time: { type: Date, default: Date.now }, // Thời gian giao dịch
});

// Admin (Web)
AdminSchema({
  _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Admin ID
  username: { type: String, required: true, unique: true }, // Tên đăng nhập
  email: { type: String, required: true, unique: true }, // Email
  password: { type: String, required: true }, // Mật khẩu
  phoneNumber: { type: String, required: true, unique: true }, // Số điện thoại
  displayName: { type: String, required: true }, // Tên hiển thị
  createdAt: { type: Date, default: Date.now }, // Ngày tạo
  isActive: { type: Boolean, default: true }, // Trạng thái tài khoản
});

// Thương hiệu (Web)
BrandSchema({
  _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Brand ID
  displayName: { type: String, required: true, unique: true }, // Tên thương hiệu
  username: { type: String, required: true, unique: true }, // Tên đăng nhập
  password: { type: String, required: true }, // Mật khẩu
  field: { type: String, required: true }, // Lĩnh vực hoạt động
  latitude: { type: Number }, // Vĩ độ
  longitude: { type: Number }, // Kinh độ
  isActive: { type: Boolean, default: true }, // Trạng thái hoạt động
  createdAt: { type: Date, default: Date.now }, // Ngày tạo
  updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật
});

// Người dùng (Mobile)
UserSchema({
  _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // ID định danh duy nhất
  username: { type: String, required: true, unique: true }, // Tên đăng nhập
  password: { type: String, required: true }, // Mật khẩu
  displayName: { type: String, required: true }, // Tên hiển thị
  avatarUrl: { type: String }, // Ảnh đại diện
  dateOfBirth: { type: Date }, // Ngày sinh
  email: { type: String, unique: true }, // Email
  phoneNumber: { type: String, unique: true, required: true }, // Số điện thoại (để xác thực OTP)
  gender: { type: String, enum: ["male", "female", "other"] }, // Giới tính
  facebookAccount: { type: String }, // Tài khoản Facebook (nếu có)
  isActive: { type: Boolean, default: true }, // Trạng thái tài khoản
  createdAt: { type: Date, default: Date.now }, // Ngày tạo
  updatedAt: { type: Date, default: Date.now }, // Ngày cập nhật
  inventory: [{ type: mongoose.Schema.Types.ObjectId, ref: "Item" }], // Danh sách vật phẩm người dùng sở hữu
  configs: { type: Number, default: 10 }, // Lượt chơi của người dùng
});
