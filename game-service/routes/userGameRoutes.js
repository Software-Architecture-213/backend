const express = require("express");
const UserGameController = require("../controllers/userGameController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/:userId/:gameId", UserGameController.getUserGameByUserIdAndGameId);

router.get("/", UserGameController.getAllUserGames);
router.post("/", UserGameController.createUserGame);

router.patch("/:id/decrease-turn", UserGameController.decreaseUserGameTurn);
router.patch("/:id/increase-turn", UserGameController.increaseUserGameTurn);

router.get("/:id", UserGameController.getUserGameById);
router.put("/:id", adminVerifyHandler, UserGameController.updateUserGame);
router.patch("/:id", adminVerifyHandler, UserGameController.updateUserGame);
router.delete("/:id", UserGameController.deleteUserGame);

module.exports = router;
