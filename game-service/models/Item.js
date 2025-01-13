const mongoose = require("mongoose");
const { v4: uuidv4 } = require("uuid");

const ItemSchema = new mongoose.Schema(
	{
		_id: { type: String, default: uuidv4 }, // UUID
		name: { type: String, required: true }, // Tên vật phẩm
		description: { type: String }, // Mô tả vật phẩm
		imageUrl: { type: String }, // Hình ảnh vật phẩm
		promotionId: { type: String, ref: "Promotion" },
		rarity: {
			type: String,
			enum: ["common", "uncommon", "rare", "epic", "legendary"],
			default: "common",
		}, // Hiếm có của vật phẩm
		tradable: { type: Boolean, default: true }, // Có thể trao đổi hay không
		maxQuantity: { type: Number, default: 1 }, // Số lượng tối đa mà người dùng có thể sở hữu
		gameId: { type: String, ref: "Game" }, // Liên kết tới trò chơi
	},
	{
		timestamps: true,
	}
);

ItemSchema.set("toJSON", {
	virtuals: true,
});

module.exports = mongoose.model("Item", ItemSchema);
