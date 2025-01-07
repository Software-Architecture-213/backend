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
		const statistic = await Game.aggregate([
			{
				$match: {
					promotionId,
				},
			},
			{
				$group: {
					_id: "$gameId",
					count: { $sum: 1 },
				},
			},
		]);
	}
}

module.exports = new StatisticService();
