const PromotionService = require("../services/promotionService");

const getAllPromotions = async (req, res) => {
	try {
		const promotions = await PromotionService.getPromotions();
		res.json(promotions);
	} catch (error) {
		next(error);
	}
};

const createPromotion = async (req, res) => {
	try {
		const promotion = req.body;
		const newPromotion = await PromotionService.createPromotion(promotion);
		res.created(newPromotion);
	} catch (error) {
		next(error);
	}
};

const getPromotionById = async (req, res) => {
	try {
		const promotionId = req.params.id;
		const promotion = await PromotionService.getPromotionById(promotionId);
		res.json(promotion);
	} catch (error) {
		next(error);
	}
};

const getPromotionsByBrandId = async (req, res) => {
	try {
		const brandId = req.params.id;
		const promotions = await PromotionService.getPromotionsByBrandId(brandId);
		res.json(promotions);
	} catch (error) {
		next(error);
	}
};

const updatePromotion = async (req, res) => {
	try {
		const promotionId = req.params.id;
		const promotion = req.body;
		const updatedPromotion = await PromotionService.updatePromotion(
			promotionId,
			promotion
		);
		res.json(updatedPromotion);
	} catch (error) {
		next(error);
	}
};

const deletePromotion = async (req, res) => {
	try {
		const promotionId = req.params.id;
		const promotion = await PromotionService.deletePromotion(promotionId);
		res.json(promotion);
	} catch (error) {
		next(error);
	}
};

module.exports = {
	getAllPromotions,
	createPromotion,
	getPromotionById,
	getPromotionsByBrandId,
	updatePromotion,
	deletePromotion,
};
