const mongoose = require("mongoose");
const { v4: uuidv4 } = require("uuid");

const PromotionSchema = new mongoose.Schema(
	{
		_id: { type: String, default: uuidv4 }, // UUID
		name: { type: String, required: true }, // Tên khuyến mãi
		description: { type: String }, // Mô tả khuyến mãi
		imageUrl: { type: String }, // Hình ảnh khuyến mãi
		startDate: { type: Date, required: true }, // Thời gian bắt đầu
		endDate: { type: Date, required: true }, // Thời gian kết thúc
		brandId: { type: String, required: true }, // Thương hiệu liên kết
		budget: { type: Number, default: 0 }, // Ngân sách dành cho khuyến mãi
		remainingBudget: { type: Number }, // Ngân sách còn lại
		status: {
			type: String,
			enum: ["active", "inactive", "completed"],
			default: "active",
		}, // Trạng thái khuyến mãi
		vouchers: [{ type: String }], // Danh sách voucher
		games: [{ type: String, ref: "Game" }], // Danh sách trò chơi
	},
	{ timestamps: true }
);

PromotionSchema.set("toJSON", {
	virtuals: true,
});

module.exports = mongoose.model("Promotion", PromotionSchema);
