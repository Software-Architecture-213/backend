const PromotionService = require("../services/promotionService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const getAllPromotions = async (req, res) => {
	const filter = convertTimeQueryParamToFilter(req.query);
	const promotions = await PromotionService.getPromotions(filter);
	res.ok(promotions);
};

const createPromotion = async (req, res) => {
	const promotion = req.body;
	const newPromotion = await PromotionService.createPromotion(promotion);
	console.log(newPromotion);
	res.created(newPromotion);
};

const getPromotionById = async (req, res) => {
	const promotionId = req.params.id;
	const promotion = await PromotionService.getPromotionById(promotionId);
	res.ok(promotion);
};

const getPromotionsByBrandId = async (req, res) => {
	const brandId = req.params.id;
	const promotions = await PromotionService.getPromotionsByBrandId(brandId);
	res.ok(promotions);
};

const updatePromotion = async (req, res) => {
	const promotionId = req.params.id;
	const promotion = req.body;
	const updatedPromotion = await PromotionService.updatePromotion(
		promotionId,
		promotion
	);
	res.ok(updatedPromotion);
};

const deletePromotion = async (req, res) => {
	const promotionId = req.params.id;
	const promotion = await PromotionService.deletePromotion(promotionId);
	res.ok(promotion);
};

module.exports = {
	getAllPromotions,
	createPromotion,
	getPromotionById,
	getPromotionsByBrandId,
	updatePromotion,
	deletePromotion,
};
