const statisticService = require("../services/statisticService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const StatisticController = {
	async getGeneralBrandStatistic(req, res, next) {
		const brandId = req.params.id;
		const filter = convertTimeQueryParamToFilter(req.query);
		const statistic = await statisticService.getGeneralBrandStatistic(
			brandId,
			filter
		);
		res.ok(statistic);
	},

	async getGamesStatisticByPromotion(req, res, next) {
		const promotionId = req.params.id;
		const filter = convertTimeQueryParamToFilter(req.query);
		const statistic = await statisticService.getGamesStatisticByPromotion(
			promotionId,
			filter
		);
		res.ok(statistic);
	},

	async getGamesStatistic(req, res, next) {
		const filter = convertTimeQueryParamToFilter(req.query);
		const statistic = await statisticService.getGamesStatistic(filter);
		console.log(statistic);
		res.ok(statistic);
	},

	async getGeneralBrandStatisticForAdmin(req, res, next) {
		const statistic = await statisticService.getGeneralBrandStatisticForAdmin();
		res.ok(statistic);
	},

	async getUsersStatisticByPromotion(req, res, next) {
		const promotionId = req.params.id;
		const filter = convertTimeQueryParamToFilter(req.query);
		console.log(promotionId, filter);
		const statistic = await statisticService.getUsersStatisticByPromotion(
			promotionId,
			filter
		);
		res.ok(statistic);
	},

	async getBrandBudgetStatistics(req, res, next) {
		const brandId = req.params.id;
		const statistic = await statisticService.getBrandBudgetStatistics(brandId);
		res.ok(statistic);
	},
};

module.exports = StatisticController;
