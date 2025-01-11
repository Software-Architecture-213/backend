const express = require("express");
require("express-async-errors");
const bodyParser = require("body-parser");
const cors = require("cors");
const connectMongoDB = require("./db/mongoose");
require("dotenv").config();

const gameRoutes = require("./routes/gameRoutes");
const promotionRoutes = require("./routes/promotionRoutes");
const itemRoutes = require("./routes/itemRoutes");
const itemTransactionRoutes = require("./routes/itemTransactionRoutes");
const itemUserRoutes = require("./routes/itemUserRoutes");
const quizQuestionRoutes = require("./routes/quizQuestionRoutes");
const userGameRoutes = require("./routes/userGameRoutes");
const statisticRoutes = require("./routes/statisticRoutes");

const errorHandler = require("./middlewares/errorHandler");
const jwtVerifyHandler = require("./middlewares/jwtVerifyHandler");
const responseMethods = require("./configs/responseMethods");
const cookieParser = require("cookie-parser");

// Initialize the Express app
const app = express();

// require("./db/insertdata");

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
// all origins are allowed
// app.use(
// 	cors({
// 		credentials: true,
// 		origin: true,
// 	})
// );
const corsOptions = {
	origin: process.env.FRONTEND_URL, // Replace with your frontend's URL
	methods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS'], // Allowed HTTP methods
	allowedHeaders: ['Content-Type', 'Authorization'], // Allowed headers
	preflightContinue: true,
	credentials: true,
};
console.log(corsOptions)

app.use(cors(corsOptions));
// app.options('*',cors(corsOptions));
// app.use(jwtVerifyHandler);
app.use(responseMethods);

// Routes
app.use("", gameRoutes);
app.use("/promotions", promotionRoutes);
app.use("/statistics", statisticRoutes);
app.use("/items", itemRoutes);
app.use("/itemTransactions", itemTransactionRoutes);
app.use("/itemUsers", itemUserRoutes);
app.use("/quizQuestions", quizQuestionRoutes);
app.use("/userGames", userGameRoutes);

app.use("/health", (req, res) => {
	res.json({ message: "Welcome to Game Service" });
});

// Error-handling middleware
app.use(errorHandler);

// Connect to MongoDB and start the server
const startServer = async () => {
	try {
		await connectMongoDB();

		app.listen(process.env.PORT || 8081, () => {
			console.log(`Server running on port ${process.env.PORT || 3000}`);
		});
	} catch (error) {
		console.error("Failed to connect to MongoDB", error);
		process.exit(1);
	}
};

startServer();
