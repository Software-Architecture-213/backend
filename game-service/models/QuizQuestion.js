const mongoose = require("mongoose");
const { v4: uuidv4 } = require("uuid");

const QuizQuestionSchema = new mongoose.Schema(
	{
		_id: { type: String, default: uuidv4 }, // UUID
		gameId: {
			type: String,
			ref: "Game",
			required: true,
		}, // Reference to the game
		question: { type: String, required: true }, // The question text
		answers: [
			{
				text: { type: String, required: true }, // Answer text
				isCorrect: { type: Boolean, required: true }, // Is this answer correct?
			},
		], // List of possible answers
		difficulty: {
			type: String,
			enum: ["easy", "medium", "hard"],
			default: "medium",
		}, // Difficulty level of the question
		points: { type: Number, default: 10 }, // Points awarded for a correct answer
		speechUrl: { type: String }, // URL to the speech file
	},
	{ timestamps: true }
);

QuizQuestionSchema.set("toJSON", {
	virtuals: true,
});

module.exports = mongoose.model("QuizQuestion", QuizQuestionSchema);
