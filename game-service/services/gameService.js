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
			throw new CustomError("Game not found", 404);
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
			throw new CustomError("Game not found", 404);
		}
		return updatedGame;
	}

	async deleteGame(gameId) {
		const game = await Game.findByIdAndDelete(gameId);
		if (!game) {
			throw new CustomError("Game not found", 404);
		}
		return game;
	}
}

module.exports = new GameService();
