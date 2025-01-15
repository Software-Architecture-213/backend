const express = require("express");
const ItemController = require("../controllers/itemController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/", ItemController.getAllItems);
router.post("/", ItemController.createItem);
router.get("/:id", ItemController.getItemById);
router.put("/:id", ItemController.updateItem);
router.delete("/:id", ItemController.deleteItem);
router.get("/game/:gameId", ItemController.getItemsByGameId);
router.get("/game/:gameId/random", ItemController.getRandomItemByGameId);
router.get("/promotion/:promotionId", ItemController.getItemByPromotionId)
module.exports = router;
