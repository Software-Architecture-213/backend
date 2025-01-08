const express = require("express");
const StatisticController = require("../controllers/statisticController");

const router = express.Router();

router.get("/brands/:id", StatisticController.getGeneralBrandStatistic);
router.get(
	"/promotions/:id/games",
	StatisticController.getGamesStatisticByPromotion
);

// router.get("/admin/brands", StatisticController.getGeneralBrandStatisticForAdmin);
router.get("/admin/games", StatisticController.getGamesStatistic);
router.get(
	"/admin/promotions/:id/users",
	StatisticController.getUsersStatisticByPromotion
);

module.exports = router;
