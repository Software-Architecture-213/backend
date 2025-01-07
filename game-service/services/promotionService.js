const Promotion = require("../models/Promotion");
const CustomError = require("../exceptions/CustomError");

class PromotionService {
	async createPromotion(promotion) {
		const newPromotion = new Promotion(promotion);
		return await newPromotion.save();
	}

	async getPromotions() {
		return await Promotion.find({});
	}

	async getPromotionById(promotionId) {
		const promotion = await Promotion.findById(promotionId);
		if (!promotion) {
			throw new CustomError("Promotion not found", 404);
		}
		return promotion;
	}

	async getPromotionsByBrandId(brandId, filter) {
		const promotions = await Promotion.find({ brandId });
		return promotions;
	}

	async getPromotionCountByBrandId(brandId, filter) {
		const count = await Promotion.countDocuments({ brandId, ...filter });
		return count;
	}

	async updatePromotion(promotionId, promotion) {
		const updatedPromotion = await Promotion.findByIdAndUpdate(
			promotionId,
			promotion,
			{
				new: true,
			}
		);
		if (!updatedPromotion) {
			throw new CustomError("Promotion not found", 404);
		}
		return updatedPromotion;
	}

	async deletePromotion(promotionId) {
		const promotion = await Promotion.findByIdAndDelete(promotionId);
		if (!promotion) {
			throw new CustomError("Promotion not found", 404);
		}
		return promotion;
	}
}

module.exports = new PromotionService();
