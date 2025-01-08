const gameService = require("./gameService");
const promotionService = require("./promotionService");
const userGameService = require("./userGameService");

const Game = require("../models/Game");
const UserGame = require("../models/UserGame");

class StatisticService {
	async getGeneralBrandStatistic(brandId, filter) {
		const gameCount = await gameService.getGameCountByBrandId(brandId, filter);
		const promotionCount = await promotionService.getPromotionCountByBrandId(
			brandId,
			filter
		);
		const userCount = await userGameService.getUserCountByBrandId(
			brandId,
			filter
		);

		return {
			gameCount,
			promotionCount,
			userCount,
		};
	}

	async getGamesStatisticByPromotion(promotionId, filter) {
		// Used to get total number of users who played each game in the promotion
		// The response is an array of objects { _id: gameId, name, playCount }
		const statistic = await Game.aggregate([
			// Match games by promotionId
			{
				$match: { promotionId },
			},
			// Lookup user games associated with the game
			{
				$lookup: {
					from: "usergames", // Collection name for user-game relations
					localField: "_id", // Field in the Game collection
					foreignField: "gameId", // Field in the UserGame collection
					as: "userGames", // Output array field
					pipeline: [
						{
							$match: filter,
						},
					],
				},
			},
			// Group by gameId and calculate play count
			{
				$group: {
					_id: "$_id",
					name: { $first: "$name" },
					playCount: { $sum: { $size: "$userGames" } }, // Calculate play count
				},
			},
			// Sort by play count in descending order
			{
				$sort: { playCount: -1 },
			},
		]);

		return statistic;
	}
}

module.exports = new StatisticService();
