const express = require("express");
const ItemTransactionController = require("../controllers/itemTransactionController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/", ItemTransactionController.getAllItemTransactions);
router.post("/", ItemTransactionController.createItemTransaction);
router.get("/:id", ItemTransactionController.getItemTransactionById);
router.put("/:id", ItemTransactionController.updateItemTransaction);
router.delete("/:id", ItemTransactionController.deleteItemTransaction);
router.get("/user/:userId", ItemTransactionController.getItemTransactionByUserId);

module.exports = router;
