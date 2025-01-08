const mongoose = require("mongoose");
const { v4: uuidv4 } = require("uuid");

const UserGameSchema = new mongoose.Schema(
	{
		_id: { type: String, default: uuidv4 }, // UUID
		userId: { type: String }, // Người chơi
		gameId: { type: String, ref: "Game" }, // Trò chơi
		score: { type: Number, default: 0 }, // Điểm số
		remainingTurns: { type: Number, default: 10 }, // Lượt chơi còn lại
		earnedTurns: { type: Number, default: 0 }, // Lượt chơi được nhận thêm
	},
	{ timestamps: true }
);

UserGameSchema.index({ gameId: 1, userId: 1 }, { unique: true });

module.exports = mongoose.model("UserGame", UserGameSchema);
