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
};

module.exports = StatisticController;
