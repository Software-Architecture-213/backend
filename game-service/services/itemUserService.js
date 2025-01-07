const ItemUser = require("../models/ItemUser");
const CustomError = require("../exceptions/CustomError");

class ItemUserService {
	async createItemUser(itemUser) {
		const newItemUser = new ItemUser(itemUser);
		return await newItemUser.save();
	}

	async getItemUsers() {
		return await ItemUser.find({});
	}

	async getItemUserById(itemUserId) {
		const itemUser = await ItemUser.findById(itemUserId);
		if (!itemUser) {
			throw new CustomError("ItemUser not found", 404);
		}
		return itemUser;
	}

	async updateItemUser(itemUserId, itemUser) {
		const updatedItemUser = await ItemUser.findByIdAndUpdate(
			itemUserId,
			itemUser,
			{
				new: true,
			}
		);
		if (!updatedItemUser) {
			throw new CustomError("ItemUser not found", 404);
		}
		return updatedItemUser;
	}

	async deleteItemUser(itemUserId) {
		const itemUser = await ItemUser.findByIdAndDelete(itemUserId);
		if (!itemUser) {
			throw new CustomError("ItemUser not found", 404);
		}
		return itemUser;
	}
}

module.exports = new ItemUserService();
