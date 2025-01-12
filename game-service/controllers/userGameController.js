const UserGameService = require("../services/userGameService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const getAllUserGames = async (req, res) => {
	const filter = convertTimeQueryParamToFilter(req.query);
	const userGames = await UserGameService.getUserGames(filter);
	res.ok(userGames);
};

const createUserGame = async (req, res) => {
	const userGame = req.body;
	const newUserGame = await UserGameService.createUserGame(userGame);
	res.created(newUserGame);
};

const getUserGameById = async (req, res) => {
	const userGameId = req.params.id;
	const userGame = await UserGameService.getUserGameById(userGameId);
	res.ok(userGame);
};

const getUserGameByUserIdAndGameId = async (req, res) => {
	const userId = req.params.userId;
	const gameId = req.params.gameId;
	console.log(userId, gameId);
	const userGame = await UserGameService.getUserGameByUserIdAndGameId(
		userId,
		gameId
	);
	res.ok(userGame);
};

const updateUserGame = async (req, res) => {
	const userGameId = req.params.id;
	const userGame = req.body;
	const updatedUserGame = await UserGameService.updateUserGame(
		userGameId,
		userGame
	);
	res.ok(updatedUserGame);
};

const deleteUserGame = async (req, res) => {
	const userGameId = req.params.id;
	const userGame = await UserGameService.deleteUserGame(userGameId);
	res.ok(userGame);
};

const decreaseUserGameTurn = async (req, res) => {
	const userGameId = req.params.id;
	const userGame = await UserGameService.decreaseUserGameTurn(userGameId);
	res.ok(userGame);
};

const increaseUserGameTurn = async (req, res) => {
	const userGameId = req.params.id;
	const userGame = await UserGameService.increaseUserGameTurn(userGameId);
	res.ok(userGame);
};

module.exports = {
	getAllUserGames,
	createUserGame,
	getUserGameById,
	updateUserGame,
	deleteUserGame,
	getUserGameByUserIdAndGameId,
	decreaseUserGameTurn,
	increaseUserGameTurn,
};
