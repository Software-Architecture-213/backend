const gameService = require("./gameService");
const promotionService = require("./promotionService");
const userGameService = require("./userGameService");

const Game = require("../models/Game");
const UserGame = require("../models/UserGame");
const Promotion = require("../models/Promotion");

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
			{
				$project: {
					id: "$_id",
					name: "$name",
					playCount: "$playCount",
				},
			},
			// Sort by play count in descending order
			{
				$sort: { playCount: -1 },
			},
		]);

		return statistic;
	}

	async getGamesStatistic(filter) {
		// Used to get total number of users who played each game
		// The response is an array of objects { _id: gameId, name, playCount }
		const statistic = await Game.aggregate([
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
			{
				$project: {
					id: "$_id",
					name: "$name",
					playCount: "$playCount",
				},
			},
			// Sort by play count in descending order
			{
				$sort: { playCount: -1 },
			},
		]);

		return statistic;
	}

	async getUsersStatisticByPromotion(promotionId, filter) {
		// Used to get total number of users who played join each day in the promotion
		// The response is an array of objects { date, userCount }
		// The result must have all days in the promotion period
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
			// Unwind userGames array
			{
				$unwind: "$userGames",
			},
			// Group by date and calculate user count
			{
				$group: {
					_id: {
						$dateToString: { format: "%Y-%m-%d", date: "$userGames.createdAt" },
					},
					userCount: { $sum: 1 },
				},
			},
			{
				$project: {
					date: "$_id",
					userCount: "$userCount",
				},
			},
			// Sort by date in ascending order
			{
				$sort: { _id: 1 },
			},
		]);

		// Fill in missing dates with 0 user count
		const startDate = new Date(filter.createdAt.$gte);
		const endDate = new Date(filter.createdAt.$lte);
		const dateDiff = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24));
		const dateArray = Array.from({ length: dateDiff + 1 }, (_, i) => {
			const date = new Date(startDate);
			date.setDate(date.getDate() + i);
			return date;
		});

		const result = dateArray.map((date) => {
			const dateString = date.toISOString().split("T")[0];
			const found = statistic.find((item) => item._id === dateString);
			return {
				id: dateString,
				userCount: found ? found.userCount : 0,
			};
		});

		return result;
	}

	async getBrandBudgetStatistics(brandId) {
		try {
			const results = await Promotion.aggregate([
				{
					$match: { brandId: brandId }, // Filter by brandId
				},
				{
					$group: {
						_id: null,
						totalBudget: { $sum: "$budget" }, // Sum of all budgets
						remainingBudget: { $sum: "$remainingBudget" }, // Sum of all remaining budgets
						promotions: {
							$push: {
								id: "$_id",
								name: "$name",
								budget: "$budget",
								remainingBudget: "$remainingBudget",
								spentBudget: { $subtract: ["$budget", "$remainingBudget"] },
							},
						},
					},
				},
				{
					$project: {
						_id: 0, // Exclude the group _id
						totalBudget: 1,
						remainingBudget: 1,
						promotions: 1,
					},
				},
			]);

			console.log(brandId);
			const testResult = await Promotion.aggregate([
				{
					$match: { brandId }, // Filter by brandId
				},
			]);
			return results.length > 0 ? results[0] : null;
		} catch (error) {
			console.error("Error fetching brand statistics:", error);
			throw error;
		}
	}
}

module.exports = new StatisticService();
