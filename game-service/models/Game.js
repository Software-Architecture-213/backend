const mongoose = require("mongoose");
const { v4: uuidv4 } = require("uuid");

const GameSchema = new mongoose.Schema(
	{
		_id: { type: String, default: uuidv4 }, // UUID
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
		promotionId: { type: String, ref: "Promotion" }, // Liên kết với Promotion
		brandId: { type: String },
	},
	{
		timestamps: true,
	}
);

GameSchema.set("toJSON", {
	virtuals: true,
});

module.exports = mongoose.model("Game", GameSchema);
