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

module.exports = {
	getAllItemUsers,
	createItemUser,
	getItemUserById,
	updateItemUser,
	deleteItemUser,
};
