const Game = require("../models/Game");
const Promotion = require("../models/Promotion");
const UserGame = require("../models/UserGame");
const Item = require("../models/Item");
const ItemUser = require("../models/ItemUser");
const ItemTransaction = require("../models/ItemTransaction");
const QuizQuestion = require("../models/QuizQuestion");

// read data from json file
const fs = require("fs");
const path = require("path");

// Convert all createdAt and updatedAt fields to Date type
const convertStringToDate = (data) => {
	data.forEach((item) => {
		item.createdAt = new Date(item.createdAt);
		item.updatedAt = new Date(item.updatedAt);
	});

	return data;
};

const games = convertStringToDate(
	JSON.parse(
		fs.readFileSync(
			path.join(
				__dirname,
				"..",
				"..",
				"data-scripts",
				"data-in-jsons",
				"Game.json"
			),
			"utf-8"
		)
	)
);

const promotions = convertStringToDate(
	JSON.parse(
		fs.readFileSync(
			path.join(
				__dirname,
				"..",
				"..",
				"data-scripts",
				"data-in-jsons",
				"Promotion.json"
			),
			"utf-8"
		)
	)
);

const userGames = convertStringToDate(
	JSON.parse(
		fs.readFileSync(
			path.join(
				__dirname,
				"..",
				"..",
				"data-scripts",
				"data-in-jsons",
				"UserGame.json"
			),
			"utf-8"
		)
	)
);

const items = convertStringToDate(
	JSON.parse(
		fs.readFileSync(
			path.join(
				__dirname,
				"..",
				"..",
				"data-scripts",
				"data-in-jsons",
				"Item.json"
			),
			"utf-8"
		)
	)
);

const itemUsers = convertStringToDate(
	JSON.parse(
		fs.readFileSync(
			path.join(
				__dirname,
				"..",
				"..",
				"data-scripts",
				"data-in-jsons",
				"ItemUser.json"
			),
			"utf-8"
		)
	)
);

const itemTransactions = convertStringToDate(
	JSON.parse(
		fs.readFileSync(
			path.join(
				__dirname,
				"..",
				"..",
				"data-scripts",
				"data-in-jsons",
				"ItemTransaction.json"
			),
			"utf-8"
		)
	)
);

const quizQuestions = JSON.parse(
	fs.readFileSync(
		path.join(
			__dirname,
			"..",
			"..",
			"data-scripts",
			"data-in-jsons",
			"QuizQuestion.json"
		),
		"utf-8"
	)
);

const insertData = async () => {
	try {
		// await Game.insertMany(games);
		// await Promotion.insertMany(promotions);
		// await UserGame.insertMany(userGames);
		// await Item.insertMany(items);
		// await ItemUser.insertMany(itemUsers);
		// await ItemTransaction.insertMany(itemTransactions);
		await QuizQuestion.insertMany(quizQuestions);

		console.log("Data inserted successfully");
	} catch (error) {
		console.error("Error while inserting data", error);
	}
};

insertData();
// Run this script by running the following command in the terminal:
// node game-service/db/insertdata.js
