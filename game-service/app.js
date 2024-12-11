const express = require("express");
const bodyParser = require("body-parser");
const connectMongoDB = require("./db/mongoose") 
require("dotenv").config();

const gameRoutes = require("./routes/gameRoutes");
const errorHandler = require("./middlewares/errorHandler");

// Initialize the Express app
const app = express();

// Middleware
app.use(bodyParser.json());

// Routes
app.use("/games", gameRoutes);

app.use("/", (req, res) => {
  res.json({message :"Welcome to Game Service"})
})

// Error-handling middleware
app.use(errorHandler);

// Connect to MongoDB and start the server
const startServer = async () => {
  try {
    // await connectMongoDB();

    app.listen(process.env.PORT || 3000, () => {
      console.log(`Server running on port ${process.env.PORT || 3000}`);
    });
  } catch (error) {
    console.error("Failed to connect to MongoDB", error);
    process.exit(1);
  }
};

startServer();
