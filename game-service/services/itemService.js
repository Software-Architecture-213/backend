const Item = require("../models/Item");
const CustomError = require("../exceptions/CustomError");

class ItemService {
	async createItem(item) {
		const newItem = new Item(item);
		return await newItem.save();
	}

	async createManyItems(items) {
		return await Item.insertMany(items);
	}

	async getItems() {
		return await Item.find({});
	}

	async getItemById(itemId) {
		const item = await Item.findById(itemId);
		if (!item) {
			throw new CustomError(404, "Item not found");
		}
		return item;
	}

	async getItemByPromotionId(promotionId) {
		const item = await Item.find({ promotionId: promotionId });
		console.log(item)
		if (!item) {
			throw new CustomError(404, "Item not found by promotion id");
		}
		return item;
	}

	async updateItem(itemId, item) {
		const updatedItem = await Item.findByIdAndUpdate(itemId, item, {
			new: true,
		});
		if (!updatedItem) {
			throw new CustomError(404, "Item not found");
		}
		return updatedItem;
	}

	async deleteItem(itemId) {
		const item = await Item.findByIdAndDelete(itemId);
		if (!item) {
			throw new CustomError(404, "Item not found");
		}
		return item;
	}

	async getItemsByGameId(gameId) {
		return await Item.find({ gameId: gameId });
	}

	async getRandomItemByGameId(gameId) {
		const items = await Item.find({ gameId: gameId });
		if (items.length === 0) {
			throw new CustomError(404, "No items found");
		}
		const randomIndex = Math.floor(Math.random() * items.length);
		return items[randomIndex];
	}
}

module.exports = new ItemService();
