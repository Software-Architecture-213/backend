const UserGame = require("../models/UserGame");
const GameService = require("./gameService");
const CustomError = require("../exceptions/CustomError");

class UserGameService {
	async createUserGame(userGame) {
		const newUserGame = new UserGame(userGame);
		return await newUserGame.save();
	}

	async getUserGames() {
		return await UserGame.find({});
	}

	async getUserGameById(userGameId) {
		const userGame = await UserGame.findById(userGameId);
		if (!userGame) {
			throw new CustomError(404, "UserGame not found");
		}
		return userGame;
	}

	async getUserCountByBrandId(brandId, filter) {
		const gamesByBrandId = await GameService.getGamesByBrandId(brandId);
		const gameIds = gamesByBrandId.map((game) => game._id);
		const userCount = await UserGame.countDocuments({
			gameId: { $in: gameIds },
			...filter,
		});

		return userCount;
	}

	async getUserGameByUserIdAndGameId(userId, gameId) {
		const userGame = await UserGame.findOne({ userId, gameId });
		if (!userGame) {
			throw new CustomError(404, "UserGame not found");
		}
		return userGame;
	}

	async getUserCountByGameId(gameId, filter) {
		const userCount = await UserGame.countDocuments({
			gameId,
			...filter,
		});

		return userCount;
	}

	async updateUserGame(userGameId, userGame) {
		const updatedUserGame = await UserGame.findByIdAndUpdate(
			userGameId,
			userGame,
			{
				new: true,
			}
		);
		if (!updatedUserGame) {
			throw new CustomError(404, "UserGame not found");
		}
		return updatedUserGame;
	}

	async deleteUserGame(userGameId) {
		const userGame = await UserGame.findByIdAndDelete(userGameId);
		if (!userGame) {
			throw new CustomError(404, "UserGame not found");
		}
		return userGame;
	}

	async decreaseUserGameTurn(userGameId) {
		const userGame = await UserGame.findById(userGameId);
		if (!userGame) {
			throw new CustomError(404, "UserGame not found");
		}

		userGame.remainingTurns -= 1;
		await userGame.save();
		return userGame;
	}

	async increaseUserGameTurn(userGameId) {
		const userGame = await UserGame.findById(userGameId);
		if (!userGame) {
			throw new CustomError(404, "UserGame not found");
		}

		userGame.remainingTurns += 1;
		userGame.earnedTurns += 1;
		await userGame.save();

		return userGame;
	}
}

module.exports = new UserGameService();
