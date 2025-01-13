const QuizQuestion = require("../models/QuizQuestion");
const CustomError = require("../exceptions/CustomError");

class QuizQuestionService {
	async createQuizQuestion(quizQuestion) {
		const newQuizQuestion = new QuizQuestion(quizQuestion);
		return await newQuizQuestion.save();
	}

	async getQuizQuestions() {
		return await QuizQuestion.find({});
	}

	async getQuizQuestionById(quizQuestionId) {
		const quizQuestion = await QuizQuestion.findById(quizQuestionId);
		if (!quizQuestion) {
			throw new CustomError(404, "QuizQuestion not found");
		}
		return quizQuestion;
	}

	async updateQuizQuestion(quizQuestionId, quizQuestion) {
		const updatedQuizQuestion = await QuizQuestion.findByIdAndUpdate(
			quizQuestionId,
			quizQuestion,
			{
				new: true,
			}
		);
		if (!updatedQuizQuestion) {
			throw new CustomError(404, "QuizQuestion not found");
		}
		return updatedQuizQuestion;
	}

	async deleteQuizQuestion(quizQuestionId) {
		const quizQuestion = await QuizQuestion.findByIdAndDelete(quizQuestionId);
		if (!quizQuestion) {
			throw new CustomError(404, "QuizQuestion not found");
		}
		return quizQuestion;
	}
	async getQuizQuestionsBygameId(gameId) {
		const quizQuestions = await QuizQuestion.find({ gameId: gameId });
		return quizQuestions;
	}

	async updateSpeechUrl(quizQuestionId, speechUrl) {
		const quizQuestion = await QuizQuestion.findByIdAndUpdate(
			quizQuestionId,
			{ speechUrl },
			{ new: true }
		);

		if (!quizQuestion) {
			throw new CustomError(404, "QuizQuestion not found");
		}

		return quizQuestion;
	}
}

module.exports = new QuizQuestionService();
