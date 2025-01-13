const mongoose = require("mongoose");
const { v4: uuidv4 } = require("uuid");

const ItemTransactionSchema = new mongoose.Schema(
	{
		_id: { type: String, default: uuidv4 }, // UUID
		senderId: { type: String }, // Người gửi
		receiverId: { type: String }, // Người nhận
		itemId: { type: String, ref: "Item" }, // Vật phẩm được gửi
		promotionId: { type: String, ref: "Promotion" }, // Khuyến mãi được gửi
		expiredAt: { type: Date }, // Ngày hết hạn
		status: {
			type: String,
			enum: ["pending", "accepted", "rejected"],
			default: "pending",
		}, // Trạng thái giao dịch
	},
	{ timestamps: true }
);

ItemTransactionSchema.set("toJSON", {
	virtuals: true,
});

module.exports = mongoose.model("ItemTransaction", ItemTransactionSchema);
