const express = require("express");
const UserGameController = require("../controllers/userGameController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/", UserGameController.getAllUserGames);
router.post("/", UserGameController.createUserGame);
router.get("/:id", UserGameController.getUserGameById);
router.put("/:id", adminVerifyHandler, UserGameController.updateUserGame);
router.delete("/:id", UserGameController.deleteUserGame);

module.exports = router;
