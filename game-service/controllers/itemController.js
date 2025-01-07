const ItemService = require("../services/itemService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const getAllItems = async (req, res) => {
	const filter = convertTimeQueryParamToFilter(req.query);
	const items = await ItemService.getItems(filter);
	res.ok(items);
};

const createItem = async (req, res) => {
	const item = req.body;
	const newItem = await ItemService.createItem(item);
	res.created(newItem);
};

const getItemById = async (req, res) => {
	const itemId = req.params.id;
	const item = await ItemService.getItemById(itemId);
	res.ok(item);
};

const updateItem = async (req, res) => {
	const itemId = req.params.id;
	const item = req.body;
	const updatedItem = await ItemService.updateItem(itemId, item);
	res.ok(updatedItem);
};

const deleteItem = async (req, res) => {
	const itemId = req.params.id;
	const item = await ItemService.deleteItem(itemId);
	res.ok(item);
};

module.exports = {
	getAllItems,
	createItem,
	getItemById,
	updateItem,
	deleteItem,
};
