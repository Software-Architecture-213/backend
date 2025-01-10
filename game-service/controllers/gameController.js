const GameService = require("../services/gameService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const getAllGames = async (req, res) => {
	const filter = convertTimeQueryParamToFilter(req.query);
	const games = await GameService.getGames(filter);
	res.ok(games);
};

const createGame = async (req, res) => {
	const game = req.body;
	const newGame = await GameService.createGame(game);
	res.created(newGame);
};

const getGameById = async (req, res) => {
	const gameId = req.params.id;
	const game = await GameService.getGameById(gameId);
	res.ok(game);
};

const getGamesByBrandId = async (req, res) => {
	const brandId = req.params.id;
	const games = await GameService.getGamesByBrandId(brandId);
	res.ok(games);
};

const updateGame = async (req, res) => {
	const gameId = req.params.id;
	const game = req.body;
	const updatedGame = await GameService.updateGame(gameId, game);
	res.ok(updatedGame);
};

const deleteGame = async (req, res) => {
	const gameId = req.params.id;
	const game = await GameService.deleteGame(gameId);
	res.ok(game);
};
const getGamesByPromotionID = async (req, res) => {
	const promotionId = req.params.id;
	const games = await GameService.getGamesByPromotionId(promotionId);
	res.ok(games);
};

module.exports = {
	getAllGames,
	createGame,
	getGameById,
	getGamesByBrandId,
	updateGame,
	deleteGame,
	getGamesByPromotionID,
};
