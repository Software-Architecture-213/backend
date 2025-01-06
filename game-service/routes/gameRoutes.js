const express = require("express");
const GameService = require("../services/gameService");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler")

const router = express.Router();

// Get all games
router.get("/collection", async (req, res, next) => {
  try {
    const games = await GameService.getGames();
    res.json(games);
  } catch (error) {
    next(error);
  }
});

module.exports = router