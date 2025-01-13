const express = require("express");
const ItemUserController = require("../controllers/itemUserController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/", ItemUserController.getAllItemUsers);
router.post("/", ItemUserController.createItemUser);
router.get("/:id", ItemUserController.getItemUserById);
router.put("/:id", ItemUserController.updateItemUser);
router.delete("/:id", ItemUserController.deleteItemUser);
router.get("/user/:id", ItemUserController.getItemUserByUserId);
router.get("/user/:userId/promotion/:promotionId", ItemUserController.getItemUserByUserIdAndPromotionId);
router.delete("/user/:userId/items", ItemUserController.deleteItemUsersByUserIdAndItemIds);

module.exports = router;
