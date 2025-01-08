const ItemTransaction = require("../models/ItemTransaction");
const CustomError = require("../exceptions/CustomError");

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
			throw new CustomError("ItemTransaction not found", 404);
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
			throw new CustomError("ItemTransaction not found", 404);
		}
		return updatedItemTransaction;
	}

	async deleteItemTransaction(itemTransactionId) {
		const itemTransaction = await ItemTransaction.findByIdAndDelete(
			itemTransactionId
		);
		if (!itemTransaction) {
			throw new CustomError("ItemTransaction not found", 404);
		}
		return itemTransaction;
	}
}

module.exports = new ItemTransactionService();
