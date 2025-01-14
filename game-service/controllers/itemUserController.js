const ItemUserService = require("../services/itemUserService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const getAllItemUsers = async (req, res) => {
	const filter = convertTimeQueryParamToFilter(req.query);
	const itemUsers = await ItemUserService.getItemUsers(filter);
	res.ok(itemUsers);
};

const createItemUser = async (req, res) => {
	const itemUser = req.body;
	const newItemUser = await ItemUserService.createItemUser(itemUser);
	res.created(newItemUser);
};

const getItemUserById = async (req, res) => {
	const itemUserId = req.params.id;
	const itemUser = await ItemUserService.getItemUserById(itemUserId);
	res.ok(itemUser);
};

const updateItemUser = async (req, res) => {
	const itemUserId = req.params.id;
	const itemUser = req.body;
	const updatedItemUser = await ItemUserService.updateItemUser(
		itemUserId,
		itemUser
	);
	res.ok(updatedItemUser);
};

const deleteItemUser = async (req, res) => {
	const itemUserId = req.params.id;
	const itemUser = await ItemUserService.deleteItemUser(itemUserId);
	res.ok(itemUser);
};

const getItemUserByUserId = async (req, res) => {
	const userId = req.params.id;
	const itemUser = await ItemUserService.getItemUserByUserId(userId);
	res.ok(itemUser);
};

const getItemUserByUserIdAndPromotionId = async (req, res) => {
	const userId = req.params.userId;
	const promotionId = req.params.promotionId;
	const itemUsers = await ItemUserService.getItemUserByUserIdAndPromotionId(userId, promotionId);
	res.ok(itemUsers);
};

const deleteItemUsersByUserIdAndItemIds = async (req, res) => {
	const userId = req.params.userId;
	const items = req.body.items;
	const result = await ItemUserService.deleteItemUsersByUserIdAndItemIds(userId, items);
	res.ok(result);
};

const checkAndCreateOrUpdateItemUser = async (req, res) => {
	const { userId, itemId, gameId, quantity } = req.body;
	const existingItemUser = await ItemUserService.getItemUserByUserIdAndItemId(userId, itemId);

	if (existingItemUser) {
		// Cập nhật quantity
		existingItemUser.quantity += quantity;
		const updatedItemUser = await ItemUserService.updateItemUser(existingItemUser._id, existingItemUser);
		res.ok(updatedItemUser);
	} else {
		// Tạo mới itemUser
		const newItemUser = await ItemUserService.createItemUser({ userId, itemId, gameId, quantity });
		res.created(newItemUser);
	}
};

const updateItemUserQuantities = async (req, res) => {
	const userId = req.params.userId;
	const itemIds = req.body.items; // Lấy items từ body

	// Kiểm tra xem items có tồn tại và là một mảng không
	if (!itemIds || !Array.isArray(itemIds)) {
		return res.status(400).json({ message: "items must be an array." });
	}

	try {
		const updatedItems = await ItemUserService.updateItemUserQuantities(userId, itemIds);
		res.ok(updatedItems);
	} catch (error) {
		res.status(500).json({ message: error.message });
	}
};

module.exports = {
	getAllItemUsers,
	createItemUser,
	getItemUserById,
	updateItemUser,
	deleteItemUser,
	getItemUserByUserId,
	getItemUserByUserIdAndPromotionId,
	deleteItemUsersByUserIdAndItemIds,
	checkAndCreateOrUpdateItemUser,
	updateItemUserQuantities,
};
