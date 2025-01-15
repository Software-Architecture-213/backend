const GameService = require("../services/gameService");
const PromotionService = require("../services/promotionService");
const QuizQuestionService = require("../services/quizQuestionService");
const ItemService = require("../services/itemService");
const {
	convertTimeQueryParamToFilter,
} = require("../utils/convertQueryParamToFilter");

const getAllGames = async (req, res) => {
	const filter = convertTimeQueryParamToFilter(req.query);
	const games = await GameService.getGames(filter);
	res.ok(games);
};

const createGame = async (req, res) => {
	const game = req.body;
	const newGame = await GameService.createGame(game);
	res.created(newGame);
};

const getGameById = async (req, res) => {
	const gameId = req.params.id;
	const game = await GameService.getGameById(gameId);
	res.ok(game);
};

const getGamesByBrandId = async (req, res) => {
	const brandId = req.params.id;
	const games = await GameService.getGamesByBrandId(brandId);
	res.ok(games);
};

const updateGame = async (req, res) => {
	const gameId = req.params.id;
	const game = req.body;
	const updatedGame = await GameService.updateGame(gameId, game);
	res.ok(updatedGame);
};

const deleteGame = async (req, res) => {
	const gameId = req.params.id;
	const game = await GameService.deleteGame(gameId);
	res.ok(game);
};
const getGamesByPromotionID = async (req, res) => {
	const promotionId = req.params.id;
	const games = await GameService.getGamesByPromotionId(promotionId);
	res.ok(games);
};

const bulkCreateGames = async (req, res) => {
	const { game, quizquestions, items } = req.body;
	// Get promotionId from the request body
	const promotionId = game.promotionId;
	console.log("promotion id: " + promotionId);
	// const promotion = await PromotionService.getPromotionById(promotionId);
	// game.imageUrl = promotion.imageUrl;
	const newGame = await GameService.createGame(game);
	var newQuizQuestions = null;
	const gameId = newGame._id;
	// Create quiz questions
	if (quizquestions != null) {
		quizquestions.forEach((quizquestion) => {
			quizquestion.gameId = gameId;
		});
		newQuizQuestions = await QuizQuestionService.createManyQuizQuestions(
			quizquestions
		);
	}

	// Create items
	const promotionItems = items.map((item) => ({
		...item,
		gameId,
		promotionId,
	}));
	const newItems = await ItemService.createManyItems(promotionItems);

	res.created({
		game: newGame,
		quizquestions: newQuizQuestions,
		items: newItems,
	});
};

module.exports = {
	getAllGames,
	createGame,
	getGameById,
	getGamesByBrandId,
	updateGame,
	deleteGame,
	getGamesByPromotionID,
	bulkCreateGames,
};
