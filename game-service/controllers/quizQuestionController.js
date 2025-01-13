const CustomError = require("../exceptions/CustomError");
const QuizQuestionService = require("../services/quizQuestionService");
const FPTClient = require("../clients/fptClient");
const axios = require("axios");
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
const getQuizQuestionsBygameId = async (req, res) => {
	const gameId = req.params.id;
	const quizQuestions = await QuizQuestionService.getQuizQuestionsBygameId(
		gameId
	);
	res.ok(quizQuestions);
};

const getSpeech = async (req, res) => {
	// Pipe the speech file to the response
	const quizQuestionId = req.params.id;
	const quizQuestion = await QuizQuestionService.getQuizQuestionById(
		quizQuestionId
	);
	if (!quizQuestion) {
		throw new CustomError(404, "Quiz question not found");
	}

	const speechUrl = quizQuestion.speechUrl;
	if (!speechUrl) {
		throw new CustomError(404, "Speech not found");
	}

	const response = await axios.get(speechUrl, {
		responseType: "stream",
	});

	if (response.status === 200) {
		response.data.pipe(res);
	} else {
		res.status(response.status).send(response.statusText);
	}
};

const createSpeech = async (req, res) => {
	const quizQuestionId = req.params.id;
	const quizQuestion = await QuizQuestionService.getQuizQuestionById(
		quizQuestionId
	);
	if (!quizQuestion) {
		throw new CustomError(404, "Quiz question not found");
	}
	if (quizQuestion.speechUrl) {
		throw new CustomError(400, "Speech already exists");
	}

	let speechText = quizQuestion.question;

	// Merge the quiz questions and answers into a single string; answers will have prefix like "A: ", "B: ", etc.
	for (let i = 0; i < quizQuestion.answers.length; i++) {
		speechText += `${String.fromCharCode(65 + i)}: ${
			quizQuestion.answers[i].text
		}. `;
	}

	const response = await FPTClient.getSpeech(quizQuestionId, speechText);
	if (response) {
		console.log("response", response);
	}

	if (response.error == 0) {
		// success
		const updatedQuizQuestion = await QuizQuestionService.updateSpeechUrl(
			quizQuestionId,
			response.async
		);
		res.ok(updatedQuizQuestion);
	} else {
		res.badRequest(response);
	}
};

const createSpeechCallback = async (req, res) => {
	console.log("createSpeechCallback");
	const { message, success } = req.body;
	const quizQuestionId = req.params.id;
	if (success) {
		// Message is the URL to the speech file
		await QuizQuestion.findByIdAndUpdate(quizQuestionId, {
			speechUrl: message,
		});
	}
	res.ok();
};

module.exports = {
	getAllQuizQuestions,
	createQuizQuestion,
	getQuizQuestionById,
	updateQuizQuestion,
	deleteQuizQuestion,
	getQuizQuestionsBygameId,
	getSpeech,
	createSpeech,
	createSpeechCallback,
};
