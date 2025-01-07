const express = require("express");
const PromotionController = require("../controllers/promotionController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/", PromotionController.getAllPromotions);
router.post("/", PromotionController.createPromotion);
router.get("/:id", PromotionController.getPromotionById);
router.get("/brands/:id", PromotionController.getPromotionsByBrandId);
router.put("/:id", PromotionController.updatePromotion);
router.delete("/:id", PromotionController.deletePromotion);

module.exports = router;
