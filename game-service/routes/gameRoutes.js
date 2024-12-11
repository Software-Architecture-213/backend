const express = require("express");
const GameService = require("../services/gameService");

const router = express.Router();

router.get("", async (req, res, next) => {
    try {
      const games = await GameService.getGames();
      res.json(games);
    } catch (error) {
      next(error);
    }
});

module.exports = router