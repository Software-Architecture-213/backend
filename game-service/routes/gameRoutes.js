const express = require("express");
const GameController = require("../controllers/gameController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/", GameController.getAllGames);
router.post("/", GameController.createGame);
router.get("/:id", GameController.getGameById);
router.get("/brands/:id", GameController.getGamesByBrandId);
router.put("/:id", GameController.updateGame);
router.delete("/:id", GameController.deleteGame);

module.exports = router;