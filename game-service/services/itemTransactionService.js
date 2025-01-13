const ItemTransaction = require("../models/ItemTransaction");
const CustomError = require("../exceptions/CustomError");
const Item = require("../models/Item");

class ItemTransactionService {
	async createItemTransaction(itemTransaction) {
		const newItemTransaction = new ItemTransaction(itemTransaction);
		return await newItemTransaction.save();
	}

	async getItemTransactions() {
		return await ItemTransaction.find({});
	}

	async getItemTransactionById(itemTransactionId) {
		const itemTransaction = await ItemTransaction.findById(itemTransactionId);
		if (!itemTransaction) {
			throw new CustomError(404, "ItemTransaction not found");
		}
		return itemTransaction;
	}

	async updateItemTransaction(itemTransactionId, itemTransaction) {
		const updatedItemTransaction = await ItemTransaction.findByIdAndUpdate(
			itemTransactionId,
			itemTransaction,
			{
				new: true,
			}
		);
		if (!updatedItemTransaction) {
			throw new CustomError(404, "ItemTransaction not found");
		}
		return updatedItemTransaction;
	}

	async deleteItemTransaction(itemTransactionId) {
		const itemTransaction = await ItemTransaction.findByIdAndDelete(
			itemTransactionId
		);
		if (!itemTransaction) {
			throw new CustomError(404, "ItemTransaction not found");
		}
		return itemTransaction;
	}

	async getItemTransactionByUserId(userId) {
		const itemTransactions = await ItemTransaction.find({
			$or: [{ senderId: userId }, { receiverId: userId }],
		});

		const itemIds = itemTransactions.map(transaction => transaction.itemId);
		const items = await Item.find({ _id: { $in: itemIds } });

		return itemTransactions.map(transaction => {
			const item = items.find(i => i._id === transaction.itemId);
			return {
				...transaction.toObject(),
				item: item || null,
			};
		});
	}
}

module.exports = new ItemTransactionService();
