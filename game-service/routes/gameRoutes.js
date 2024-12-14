const express = require("express");
const GameService = require("../services/gameService");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler")

const router = express.Router();

router.get("/collection", async (req, res, next) => {
    try {
      const games = await GameService.getGames();
      res.json(games);
    } catch (error) {
      next(error);
    }
});

router.post("", adminVerifyHandler, async (req,res,next) => {
  // CRUD games
  res.json({message : "Game adjusted"})
})

module.exports = router