const express = require("express");
const ItemController = require("../controllers/itemController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/", ItemController.getAllItems);
router.post("/", ItemController.createItem);
router.get("/:id", ItemController.getItemById);
router.put("/:id", ItemController.updateItem);
router.delete("/:id", ItemController.deleteItem);

module.exports = router;
