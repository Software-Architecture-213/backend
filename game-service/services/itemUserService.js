const ItemUser = require("../models/ItemUser");
const CustomError = require("../exceptions/CustomError");
const Item = require("../models/Item");

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
			throw new CustomError(404, "ItemUser not found");
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
			throw new CustomError(404, "ItemUser not found");
		}
		return updatedItemUser;
	}

	async deleteItemUser(itemUserId) {
		const itemUser = await ItemUser.findByIdAndDelete(itemUserId);
		if (!itemUser) {
			throw new CustomError(404, "ItemUser not found");
		}
		return itemUser;
	}
	async getItemUserByUserId(userId) {
		const itemUsers = await ItemUser.find({ userId: userId, quantity: { $gte: 0 } });
		const itemIds = itemUsers.map((itemUser) => itemUser.itemId);
		const items = await Item.find({ _id: { $in: itemIds } });

		const itemUserWithItems = itemUsers.map((itemUser) => {
			const item = items.find((item) => item._id === itemUser.itemId);
			return {
				...itemUser.toObject(),
				itemId: item,
			};
		});
		return itemUserWithItems;
	}

	async getItemUserByUserIdAndPromotionId(userId, promotionId) {
		const itemUsers = await ItemUser.find({ userId: userId, promotionId: promotionId });
		const itemIds = itemUsers.map((itemUser) => itemUser.itemId);
		const items = await Item.find({ _id: { $in: itemIds } });

		const itemUserWithItems = itemUsers.map((itemUser) => {
			const item = items.find((item) => item._id === itemUser.itemId);
			return {
				...itemUser.toObject(),
				item: item,
			};
		});
		return itemUserWithItems;
	}

	async deleteItemUsersByUserIdAndItemIds(userId, items) {
		const result = await ItemUser.deleteMany({ userId: userId, itemId: { $in: items } });
		return result;
	}

	async getItemUserByUserIdAndItemId(userId, itemId) {
		const itemUser = await ItemUser.findOne({ userId: userId, itemId: itemId });
		return itemUser;
	}

	async updateItemUserQuantities(userId, itemIds) {
		const updatePromises = itemIds.map(itemId => {
			return ItemUser.findOneAndUpdate(
				{ userId: userId, itemId: itemId, quantity: { $gt: 0 } },
				{ $inc: { quantity: -1 } },
				{ new: true }
			);
		});

		return await Promise.all(updatePromises);
	}
}

module.exports = new ItemUserService();
