const ItemTransactionService = require("../services/itemTransactionService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const getAllItemTransactions = async (req, res) => {
	const filter = convertTimeQueryParamToFilter(req.query);
	const itemTransactions = await ItemTransactionService.getItemTransactions(
		filter
	);
	res.ok(itemTransactions);
};

const createItemTransaction = async (req, res) => {
	const itemTransaction = req.body;
	const newItemTransaction = await ItemTransactionService.createItemTransaction(
		itemTransaction
	);
	res.created(newItemTransaction);
};

const getItemTransactionById = async (req, res) => {
	const itemTransactionId = req.params.id;
	const itemTransaction = await ItemTransactionService.getItemTransactionById(
		itemTransactionId
	);
	res.ok(itemTransaction);
};

const updateItemTransaction = async (req, res) => {
	const itemTransactionId = req.params.id;
	const itemTransaction = req.body;
	const updatedItemTransaction =
		await ItemTransactionService.updateItemTransaction(
			itemTransactionId,
			itemTransaction
		);
	res.ok(updatedItemTransaction);
};

const deleteItemTransaction = async (req, res) => {
	const itemTransactionId = req.params.id;
	const itemTransaction = await ItemTransactionService.deleteItemTransaction(
		itemTransactionId
	);
	res.ok(itemTransaction);
};

module.exports = {
	getAllItemTransactions,
	createItemTransaction,
	getItemTransactionById,
	updateItemTransaction,
	deleteItemTransaction,
};
