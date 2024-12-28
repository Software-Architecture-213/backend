const mongoose = require("mongoose");
const { v4: uuidv4 } = require("uuid");

const ItemUserSchema = new mongoose.Schema(
	{
		_id: { type: String, default: uuidv4 }, // UUID
		userId: { type: String, ref: "User" }, // Người sở hữu
		itemId: { type: String, ref: "Item" }, // Vật phẩm
		gameId: { type: String, ref: "Game" }, // Trò chơi (để dễ tiến hành trao đổi trong 1 game)
		quantity: { type: Number, default: 1 }, // Số lượng
	},
	{
		timestamps: true,
	}
);

module.exports = mongoose.model("ItemUser", ItemUserSchema);
