const express = require("express");
const StatisticController = require("../controllers/statisticController");

const router = express.Router();

router.get("/brands/:id", StatisticController.getGeneralBrandStatistic);

module.exports = router;
