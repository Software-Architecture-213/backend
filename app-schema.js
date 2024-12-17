GameSchema({
    _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Game ID  
    name: { type: String, required: true }, // Tên trò chơi  
    type: { 
      type: String, 
      enum: ['quiz', 'shake'], 
      required: true 
    }, // Loại trò chơi  
    imageUrl: { type: String }, // Hình ảnh đại diện trò chơi  
    description: { type: String }, // Giới thiệu trò chơi  
    guideline: { type: String },
    allowItemExchange: { type: Boolean, default: false }, // Cho phép trao đổi vật phẩm hay không  
    createdAt: { type: Date, default: Date.now }, // Ngày tạo trò chơi  
});
  
ItemSchema({
    _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Item ID  
    name: { type: String, required: true }, // Tên vật phẩm  
    description: { type: String }, // Mô tả vật phẩm  
    imageUrl: { type: String }, // Hình ảnh vật phẩm  
    rarity: { 
      type: String, 
      enum: ['common', 'uncommon', 'rare', 'epic', 'legendary'], 
      default: 'common' 
    }, // Hiếm có của vật phẩm  
    tradable: { type: Boolean, default: true }, // Có thể trao đổi hay không  
    gameId: { type: mongoose.Schema.Types.ObjectId, ref: 'Game' }, // Liên kết tới trò chơi  
});
  
PromotionSchema({
    _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Promotion ID  
    name: { type: String, required: true }, // Tên khuyến mãi  
    description: { type: String }, // Mô tả khuyến mãi  
    startDate: { type: Date, required: true }, // Thời gian bắt đầu  
    endDate: { type: Date, required: true }, // Thời gian kết thúc  
    brandId: { type: mongoose.Schema.Types.ObjectId, ref: 'Brand', required: true }, // Liên kết với thương hiệu  
    budget: { type: Number, default: 0 }, // Ngân sách dành cho khuyến mãi  
    status: { 
      type: String, 
      enum: ['active', 'inactive', 'completed'], 
      default: 'active' 
    }, // Trạng thái khuyến mãi  
    vouchers: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Voucher' }], // Danh sách voucher trong khuyến mãi
    games: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Game' }], // Danh sách trò chơi tham gia khuyến mãi  
});
VoucherSchema ({
    _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Voucher ID  
    code: { type: String, required: true, unique: true }, // Mã voucher duy nhất  
    qrCode: { type: String, required: true }, // QR Code  
    image: { type: String }, // Hình ảnh voucher  
    value: { type: Number, required: true }, // Giá trị của voucher  
    description: { type: String }, // Mô tả voucher  
    expirationDate: { type: Date, required: true }, // Ngày hết hạn  
    status: { 
      type: String, 
      enum: ['active', 'redeemed', 'expired'], 
      default: 'active' 
    }, // Trạng thái voucher  
    promotionId: { type: mongoose.Schema.Types.ObjectId, ref: 'Promotion' }, // Liên kết tới Promotion
});
  
