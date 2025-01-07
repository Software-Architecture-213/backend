const GameService = require("../services/gameService");

const getAllGames = async (req, res) => {
	try {
		const games = await GameService.getGames();
		res.json(games);
	} catch (error) {
		next(error);
	}
};

const createGame = async (req, res) => {
	try {
		const game = req.body;
		const newGame = await GameService.createGame(game);
		res.created(newGame);
	} catch (error) {
		next(error);
	}
};

const getGameById = async (req, res) => {
	try {
		const gameId = req.params.id;
		const game = await GameService.getGameById(gameId);
		res.json(game);
	} catch (error) {
		next(error);
	}
};

const getGamesByBrandId = async (req, res) => {
	try {
		const brandId = req.params.id;
		const games = await GameService.getGamesByBrandId(brandId);
		res.json(games);
	} catch (error) {
		next(error);
	}
};

const updateGame = async (req, res) => {
	try {
		const gameId = req.params.id;
		const game = req.body;
		const updatedGame = await GameService.updateGame(gameId, game);
		res.json(updatedGame);
	} catch (error) {
		next(error);
	}
};

const deleteGame = async (req, res) => {
	try {
		const gameId = req.params.id;
		const game = await GameService.deleteGame(gameId);
		res.json(game);
	} catch (error) {
		next(error);
	}
};

module.exports = {
	getAllGames,
	createGame,
	getGameById,
	getGamesByBrandId,
	updateGame,
	deleteGame,
};
