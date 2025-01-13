const axios = require("axios");

const FPT_AI_URL = process.env.FPT_AI_URL;
const FPT_AI_KEY_VALUE = process.env.FPT_AI_KEY_VALUE;
const GAME_API_SERVICE_URL = process.env.GAME_API_SERVICE_URL;

class FPTClient {
	static async getSpeech(quizQuestionId, text) {
		try {
			// add headers to axios request
			const headers = {
				api_key: FPT_AI_KEY_VALUE,
				voice: "banmai",
				speed: -1,
				format: "mp3",
				callback_url:
					GAME_API_SERVICE_URL +
					"/quizQuestions/" +
					quizQuestionId +
					"/speech/callback",
			};

			// make a post request to the FPT AI service (body is just a string)
			const response = await axios.post(FPT_AI_URL, text, {
				headers,
			});

			// return the response
			return response.data;
		} catch (error) {
			console.error(error);
		}
	}
}
module.exports = FPTClient;
