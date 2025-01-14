const Game = require("../models/Game");
const CustomError = require("../exceptions/CustomError");

class GameService {
	async createGame(game) {
		const newGame = new Game(game);
		return await newGame.save();
	}

	async getGames() {
		return await Game.find({});
	}

	async getGameById(gameId) {
		const game = await Game.findById(gameId);
		if (!game) {
			throw new CustomError(404, "Game not found");
		}
		return game;
	}

	async getGamesByBrandId(brandId, filter) {
		const games = await Game.find({ brandId, ...filter });
		return games;
	}

	async getGameCountByBrandId(brandId, filter) {
		const count = await Game.countDocuments({
			brandId,
			...filter,
		});
		return count;
	}

	async updateGame(gameId, game) {
		const updatedGame = await Game.findByIdAndUpdate(gameId, game, {
			new: true,
		});
		if (!updatedGame) {
			throw new CustomError(404, "Game not found");
		}
		return updatedGame;
	}

	async deleteGame(gameId) {
		const game = await Game.findByIdAndDelete(gameId);
		if (!game) {
			throw new CustomError(404, "Game not found");
		}
		return game;
	}
	async getGamesByPromotionId(promotionId) {
		const games = await Game.find({ promotionId: promotionId });
		return games;
	}
}

module.exports = new GameService();
