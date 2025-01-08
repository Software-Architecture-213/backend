const QuizQuestionService = require("../services/quizQuestionService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const getAllQuizQuestions = async (req, res) => {
	const filter = convertTimeQueryParamToFilter(req.query);
	const quizQuestions = await QuizQuestionService.getQuizQuestions(filter);
	res.ok(quizQuestions);
};

const createQuizQuestion = async (req, res) => {
	const quizQuestion = req.body;
	const newQuizQuestion = await QuizQuestionService.createQuizQuestion(
		quizQuestion
	);
	res.created(newQuizQuestion);
};

const getQuizQuestionById = async (req, res) => {
	const quizQuestionId = req.params.id;
	const quizQuestion = await QuizQuestionService.getQuizQuestionById(
		quizQuestionId
	);
	res.ok(quizQuestion);
};

const updateQuizQuestion = async (req, res) => {
	const quizQuestionId = req.params.id;
	const quizQuestion = req.body;
	const updatedQuizQuestion = await QuizQuestionService.updateQuizQuestion(
		quizQuestionId,
		quizQuestion
	);
	res.ok(updatedQuizQuestion);
};

const deleteQuizQuestion = async (req, res) => {
	const quizQuestionId = req.params.id;
	const quizQuestion = await QuizQuestionService.deleteQuizQuestion(
		quizQuestionId
	);
	res.ok(quizQuestion);
};

module.exports = {
	getAllQuizQuestions,
	createQuizQuestion,
	getQuizQuestionById,
	updateQuizQuestion,
	deleteQuizQuestion,
};
