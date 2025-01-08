const express = require("express");
const StatisticController = require("../controllers/statisticController");

const router = express.Router();

router.get("/brands/:id", StatisticController.getGeneralBrandStatistic);
router.get(
	"/promotions/:id/games",
	StatisticController.getGamesStatisticByPromotion
);

module.exports = router;
