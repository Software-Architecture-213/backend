const express = require("express");
const QuizQuestionController = require("../controllers/quizQuestionController");
const adminVerifyHandler = require("../middlewares/adminVerifyHandler");

const router = express.Router();

router.get("/", QuizQuestionController.getAllQuizQuestions);
router.post("/", QuizQuestionController.createQuizQuestion);
router.get("/:id", QuizQuestionController.getQuizQuestionById);
router.put("/:id", QuizQuestionController.updateQuizQuestion);
router.delete("/:id", QuizQuestionController.deleteQuizQuestion);
router.get("/games/:id", QuizQuestionController.getQuizQuestionsBygameId);

module.exports = router;
